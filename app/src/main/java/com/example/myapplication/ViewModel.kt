package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.util.UUID

class AppViewModel : ViewModel() {

    var isDrawer by mutableStateOf(false)
    var loginState by mutableStateOf(false)
    var loginResponse by mutableStateOf<LoginResponse?>(null)
        private set

    private var userInfo by mutableStateOf<UserResponse?>(null)
        private set

    var responseMessage by mutableStateOf("")

    var loading by mutableStateOf(false)

    var emailInput by mutableStateOf("")
    var passwordInput by mutableStateOf("")
    var childNameInput by mutableStateOf<String?>(null)

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    var toggleRequestInfo by mutableStateOf<ToggleRequest?>(null)

    private val apiService = RetrofitInstance.api

    private val _deleteResult = MutableStateFlow<String?>(null)
    val deleteResult = _deleteResult

    val zoneId = ZoneId.systemDefault()
    val zoneIdString = zoneId.id

    var selectedIndex by mutableStateOf<String?>(null)

    var editChildProfilePopupState by mutableStateOf(false)
    var deleteDevicePopupState by mutableStateOf(false)

    var isonLoading by mutableStateOf(false)
        private set

    fun startLoading() {
        isonLoading = true
    }

    fun stopLoading() {
        isonLoading = false
    }

    private val _monitorStates = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    val monitorStates: StateFlow<Map<Long, Boolean>> = _monitorStates


    fun getMonitorState(childId: Long): Boolean {
        return _monitorStates.value[childId] ?: false
    }


    data class ChildToggleState(
        val gamingBlocked: Boolean = true,
        val socialMediaBlocked: Boolean = true,
        val youTubeBlocked: Boolean = true
    )

    var toggleState by mutableStateOf(ChildToggleState())
        private set

    private val _monitorDurations = MutableStateFlow<Map<Long, Long>>(emptyMap())
    val monitorDurations: StateFlow<Map<Long, Long>> = _monitorDurations

    fun startMonitorDurationTimer() {
        viewModelScope.launch {
            while (true) {
                val currentTime = Instant.now().epochSecond
                val devices = loginResponse?.data?.devices ?: return@launch

                val updatedDurations = loginResponse?.data?.children?.associate { child ->
                    val relevantDevices = devices.filter { it.childId == child.childId && it.monitorOnOff <= currentTime }
                    val latestDevice = relevantDevices.maxByOrNull { it.monitorOnOff }
                    val duration = latestDevice?.let { currentTime - it.monitorOnOff } ?: 0L
                    child.childId to duration
                } ?: emptyMap()

                _monitorDurations.value = updatedDurations
                delay(1000)
            }
        }
    }



    fun updateToggleState(
        gaming: Boolean? = null,
        socialMedia: Boolean? = null,
        youTube: Boolean? = null
    ) {
        toggleState = toggleState.copy(
            gamingBlocked = gaming ?: toggleState.gamingBlocked,
            socialMediaBlocked = socialMedia ?: toggleState.socialMediaBlocked,
            youTubeBlocked = youTube ?: toggleState.youTubeBlocked
        )
    }
    fun calculateAllMonitorDurations() {
        val durations = mutableMapOf<Long, Long>()

        loginResponse?.data?.children?.forEach { child ->
            val duration = getMonitorDurationSeconds(child.childId)
            if (duration != null) {
                durations[child.childId] = duration
            }
        }

        _monitorDurations.value = durations
    }

    fun getMonitorDurationSeconds(childId: Long): Long? {
        val currentTime = Instant.now().epochSecond
        val devices = loginResponse?.data?.devices ?: return null

        // Filter by matching child ID
        val matchingDevices = devices.filter { it.childId == childId }

        // Get the most recent past monitorOnOff time
        val closestDevice = matchingDevices
            .filter { it.monitorOnOff <= currentTime }
            .minByOrNull { currentTime - it.monitorOnOff }

        return closestDevice?.let {
            currentTime - it.monitorOnOff
        }
    }


    fun addChildProfile(context: Context){
        viewModelScope.launch {
            try{
                val userId = loginResponse?.data?.userId?.toInt()
                val accessToken = loginResponse?.data?.accessToken
                val request = childNameInput?.let {
                    AddChildProfileRequest(userId,
                        it,selectedIndex,accessToken,zoneIdString)
                }
                val response = request?.let { apiService.addChildProfile(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        _deleteResult.value = "Child Profile Created Successfully"
                        refreshChildren(context = context)
                    } else {
                        _deleteResult.value = "Failed to create child profile"
                    }
                }
            }catch (e: IOException) {
                deleteResult.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                deleteResult.value = "Server error: ${e.message}"
            } catch (e: Exception) {
                deleteResult.value = "Unknown error: ${e.message}"
            }
        }
    }

    fun changeChildProfileSettings(userId: Long,childId: Long,context: Context, childName : String){
        viewModelScope.launch {
            try{
                val nameInput: String
                nameInput = if(childNameInput == null || childNameInput == "") {
                    childName
                }else{
                    childNameInput as String
                }
                val request = ChangeChildInfoRequest(userId,childId,nameInput,selectedIndex)
                val response = apiService.changeChildProfileSettings(request)
                if (response.isSuccessful) {
                    _deleteResult.value = "Child Profile Changed Successfully"
                    refreshChildren(context = context)
                    editChildProfilePopupState = false
                } else {
                    _deleteResult.value = "Failed to Change child profile"
                }
            }catch (e: IOException) {
                deleteResult.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                deleteResult.value = "Server error: ${e.message}"
            } catch (e: Exception) {
                deleteResult.value = "Unknown error: ${e.message}"
            }
        }
    }


    fun login(context: Context) {
        viewModelScope.launch {
            try {
                val uuid = getOrCreateAppInstanceId(context = context)
                val response = apiService.userLogin(LoginRequest(emailInput, passwordInput, uuid))
                val responseBody = response.body()

                if (response.isSuccessful) {
                    if (responseBody != null) {
                        if (responseBody.status == "success") {
                            loginResponse = responseBody
                            responseMessage = "Login Successful"
                            loginState = true
                            _isLoggedIn.value = true // Update state flow
                            Log.d("AppViewModel", "Login successful")
                            loading = false
                        } else {
                            responseMessage = "Invalid credentials"
                            Log.d("AppViewModel", "Invalid credentials")
                            loading = false
                        }
                    }
                }
            } catch (e: IOException) {
                responseMessage = "Network error: ${e.message}"
                Log.e("AppViewModel", "Network error: ${e.message}", e)
                loading = false
            } catch (e: HttpException) {
                responseMessage = "Server error: ${e.message()}"
                Log.e("AppViewModel", "Server error: ${e.message()}", e)
                loading = false
            } catch (e: Exception) {
                responseMessage = "Unknown error: ${e.message}"
                Log.e("AppViewModel", "Error during login: ${e.message}", e)
                loading = false
            }
        }
    }

    fun toggleChildSettings(childId: Long) {
        viewModelScope.launch {
            startLoading()
            try {
                val userId = loginResponse?.data?.userId

                if (userId != null) {
                    val toggleRequest = ToggleRequest(
                        userId = userId,
                        childId = childId,
                        gamingBlocked = toggleState.gamingBlocked,
                        socialMediaBlocked = toggleState.socialMediaBlocked,
                        youTubeBlocked = toggleState.youTubeBlocked
                    )

                    val response = apiService.toggleChildSettings(toggleRequest)

                    if (response.isSuccessful) {
                        _deleteResult.value = "Settings updated successfully"
                    } else {
                        _deleteResult.value = "Failed to update settings: ${response.message()}"
                    }
                } else {
                    _deleteResult.value = "Invalid user or child ID"
                }
            } catch (e: IOException) {
                _deleteResult.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                _deleteResult.value = "Server error: ${e.message}"
            } catch (e: Exception) {
                _deleteResult.value = "Unknown error: ${e.message}"
            } finally {
                stopLoading()
            }
        }
    }


    fun toggleMonitor(monitorActive: Boolean, childId: Long, context: Context) {
        // Update local UI state for immediate feedback
        val updatedStates = _monitorStates.value.toMutableMap()
        updatedStates[childId] = monitorActive
        _monitorStates.value = updatedStates

        startLoading()

        viewModelScope.launch {
            try {
                val request = loginResponse?.data?.userId?.let {
                    ToggleMonitor(
                        userId = it,
                        childId = childId,
                        monitorActive = monitorActive,
                        monitorOnOff = Instant.now().epochSecond
                    )
                }
                val response = request?.let { apiService.toggleMonitor(it) }

                if (response != null && response.isSuccessful) {
                    // ✅ Now refresh the user data to get updated monitorOnOff time
                    refreshChildren(context)

                    // ✅ Optional: recalculate durations immediately
                    calculateAllMonitorDurations()
                } else {
                    responseMessage = "Failed to toggle monitor: ${response?.message() ?: "No response"}"
                }
            } catch (e: IOException) {
                responseMessage = "Network error: ${e.message}"
            } catch (e: HttpException) {
                responseMessage = "Server error: ${e.message}"
            } catch (e: Exception) {
                responseMessage = "Unknown error: ${e.message}"
            } finally {
                stopLoading()
            }
        }
    }



    fun deleteChild(userId: Long, childId: Long, context: Context) {
        viewModelScope.launch {
            try {
                val request = DeleteChildRequest(userId = userId, childId = childId)
                val response = apiService.deleteChild(request)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.status == "success") {
                    _deleteResult.value = "Child deleted successfully"
                    refreshChildren(context)
                } else {
                    _deleteResult.value = "Failed to delete child"
                }
            } catch (e: Exception) {
                _deleteResult.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteDevice(userId: Long?, uuid: String, context: Context) {
        viewModelScope.launch {
            try {
                val request = DeleteDeviceRequest(userId = userId, uuid = uuid)
                val response = apiService.deleteDevice(request)

                if (response.isSuccessful) {
                    _deleteResult.value = "Child Profile Deleted Successfully"
                    refreshChildren(context = context)
                    deleteDevicePopupState = false
                } else {
                    _deleteResult.value = "Failed to delete child profile"
                }
            }catch (e: IOException) {
                deleteResult.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                deleteResult.value = "Server error: ${e.message}"
            } catch (e: Exception) {
                deleteResult.value = "Unknown error: ${e.message}"
            }}
    }

    fun refreshChildren(context: Context) {
        viewModelScope.launch {
            try {
                val uuid = getOrCreateAppInstanceId(context)
                val response = apiService.userLogin(LoginRequest(emailInput, passwordInput, uuid))
                val refreshedLogin = response.body()
                if (response.isSuccessful && refreshedLogin?.status == "success") {
                    loginResponse = refreshedLogin

                    // ✅ Reflect API monitorActive state
                    val monitorMap: Map<Long, Boolean> = refreshedLogin.data?.devices
                        ?.associate { it.childId to it.monitorActive } ?: emptyMap()
                    _monitorStates.value = monitorMap

                } else {
                    _deleteResult.value = "Failed to refresh children list"
                }
            } catch (e: Exception) {
                _deleteResult.value = "Error refreshing: ${e.message}"
            }
        }
    }

    fun clearDeleteResult() {
        _deleteResult.value = null
    }

    private fun getOrCreateAppInstanceId(context: Context): String {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        var id = prefs.getString("app_instance_id", null)
        if (id == null) {
            id = UUID.randomUUID().toString()
            prefs.edit().putString("app_instance_id", id).apply()
        }
        return id
    }
}



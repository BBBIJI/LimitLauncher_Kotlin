    package com.example.myapplication
    
    import android.content.Context
    import android.util.Log
    import android.widget.Toast
    import androidx.compose.runtime.*
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.google.firebase.installations.FirebaseInstallations
    import kotlinx.coroutines.delay
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.update
    import kotlinx.coroutines.launch
    import retrofit2.HttpException
    import java.io.IOException
    import java.time.Instant
    import java.time.ZoneId
    import java.util.*
    import android.os.Build
    
    class AppViewModel : ViewModel() {
    
        // region --- UI States ---
        var firstNameInput by mutableStateOf("")
        var lastNameInput by mutableStateOf("")
        var confirmPasswordInput by mutableStateOf("")
        var emailInput by mutableStateOf("")
        var passwordInput by mutableStateOf("")
        var childNameInput by mutableStateOf<String?>(null)
    
        private val _drawerShouldBeOpen = MutableStateFlow(false)
        val drawerShouldBeOpen: StateFlow<Boolean> = _drawerShouldBeOpen.asStateFlow()
    
        fun openDrawer() {
            _drawerShouldBeOpen.update { true }
        }
    
        fun closeDrawer() {
            _drawerShouldBeOpen.update { false }
        }
    
        fun onDrawerPhysicalStateChanged(isOpen: Boolean) {
            Log.d("AppViewModel", "Drawer physical state changed: ${if (isOpen) "Open" else "Closed"}")
            _drawerShouldBeOpen.update { isOpen }
            // If the physical state change should also update the desired state:
            // This ensures that if the user swipes the drawer, the ViewModel's desired state is also updated.
            if (_drawerShouldBeOpen.value != isOpen) {
                _drawerShouldBeOpen.update { isOpen }
            }
        }
    
    
        var selectedIndex by mutableStateOf<String?>(null)
        var isDrawer by mutableStateOf(false)
        var loginState by mutableStateOf(false)
        var loading by mutableStateOf(false)
        var isonLoading by mutableStateOf(false)
        var responseMessage by mutableStateOf("")
        var editChildProfilePopupState by mutableStateOf(false)
        var deleteDevicePopupState by mutableStateOf(false)
        // endregion

        var isMandatoryScreenTimeEnabled by mutableStateOf(true)
        var isCumulativeScreentimeEnabled by mutableStateOf(true)
        var isNoScreenPeriodOn by mutableStateOf(true)



        // region --- Auth State ---
        private var userInfo by mutableStateOf<UserResponse?>(null)
        var loginResponse by mutableStateOf<LoginResponse?>(null)
            private set
    
        private val _isLoggedIn = MutableStateFlow(false)
        val isLoggedIn: StateFlow<Boolean> = _isLoggedIn
        // endregion
    
        // region --- Monitor State ---
        private val _monitorStates = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
        val monitorStates: StateFlow<Map<Long, Boolean>> = _monitorStates
    
        private val _monitorDurations = MutableStateFlow<Map<Long, Long>>(emptyMap())
        val monitorDurations: StateFlow<Map<Long, Long>> = _monitorDurations
    
        fun getMonitorState(childId: Long): Boolean = _monitorStates.value[childId] ?: false
        // endregion
    
        // region --- Toggle State ---
        data class ChildToggleState(
            val gamingBlocked: Boolean = true,
            val socialMediaBlocked: Boolean = true,
            val youTubeBlocked: Boolean = true
        )
    
        var toggleState by mutableStateOf(ChildToggleState())
            private set
    
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
        // endregion
    
        // region --- Shared ---
        private val apiService = RetrofitInstance.api
        val zoneIdString = ZoneId.systemDefault().id
    
        private val _deleteResult = MutableStateFlow<String?>(null)
        val deleteResult = _deleteResult
    
        fun clearDeleteResult() {
            _deleteResult.value = null
        }
    
        fun startLoading() {
            isonLoading = true
        }
    
        fun stopLoading() {
            isonLoading = false
        }
        // endregion
    
        // region --- Auth Functions ---
        fun login(context: Context) {
            val uuid = UUID.randomUUID().toString()

            viewModelScope.launch {
                try {
                    loading = true
                    Log.d("AppViewModel", "Trying LoginRequestNew...")

                    // First attempt using new login structure (includes system + role)
                    val newLoginResponse = apiService.userLoginNew(
                        LoginRequestNew(
                            username = emailInput,
                            password = passwordInput,
                            uuid = uuid,
                            deviceName = getDeviceName()
                        )
                    )

                    if (newLoginResponse.isSuccessful && newLoginResponse.body()?.status == "success") {
                        // Success with new login
                        val responseBody = newLoginResponse.body()
                        loginResponse = responseBody
                        responseMessage = "Login Successful"
                        loginState = true
                        _isLoggedIn.value = true
                        Log.d("AppViewModel", "Login successful using LoginRequestNew.")
                    } else {
                        // If new login fails, try fallback
                        Log.d("AppViewModel", "LoginRequestNew failed, trying LoginRequestExisting...")

                        val fallbackResponse = apiService.userLoginExisting(
                            LoginRequestExisting(
                                username = emailInput,
                                password = passwordInput,
                                uuid = uuid
                            )
                        )

                        if (fallbackResponse.isSuccessful && fallbackResponse.body()?.status == "success") {
                            val responseBody = fallbackResponse.body()
                            loginResponse = responseBody
                            responseMessage = "Login Successful (Fallback)"
                            loginState = true
                            _isLoggedIn.value = true
                            Log.d("AppViewModel", "Login successful using LoginRequestExisting.")
                        } else {
                            responseMessage = "Login failed. Invalid credentials or account issue."
                            Log.d("AppViewModel", "Both login attempts failed.")
                        }
                    }
                } catch (e: IOException) {
                    responseMessage = "Network error: ${e.message}"
                    Log.e("AppViewModel", "IOException", e)
                } catch (e: HttpException) {
                    responseMessage = "Server error: ${e.message()}"
                    Log.e("AppViewModel", "HttpException", e)
                } catch (e: Exception) {
                    responseMessage = "Unknown error: ${e.message}"
                    Log.e("AppViewModel", "Exception", e)
                } finally {
                    loading = false
                }
            }
        }


        fun logout(context: Context) {
            _isLoggedIn.value = false
            isDrawer = false
            loginState = false
            emailInput = ""
            passwordInput = ""
            responseMessage = ""
            closeDrawer()
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
        }
    
        fun registerUser(context: Context, onSuccess: () -> Unit) {
            getOrCreateAppInstanceId(context) { uuid ->
                viewModelScope.launch {
                    try {
                        loading = true
                        val request = UserRegistrationRequest(
                                firstname = firstNameInput,
                                lastname = lastNameInput,
                                email = emailInput,
                                password = passwordInput
                            )
    
                        val response = apiService.registerUser(request)
    
                        if (response.code() == 403) {
                            responseMessage = "Registered (403, but treated as success)"
                            onSuccess()
                        } else {
                            responseMessage = "Registration failed: ${response.code()}"
                        }
                    } catch (e: Exception) {
                        responseMessage = "Error: ${e.message}"
                    } finally {
                        loading = false
                    }
                }
            }
        }
        // endregion
    
        // region --- Child Functions ---
        fun refreshChildren(context: Context) {
            val uuid = UUID.randomUUID().toString() // OR use a static value if needed
                viewModelScope.launch {
                    try {
                        if (emailInput.isBlank() || passwordInput.isBlank()) {
                            _deleteResult.value = "Missing credentials"
                            return@launch
                        }

                        val response = apiService.userLoginExisting(LoginRequestExisting(emailInput, passwordInput, uuid))
                        val refreshedLogin = response.body()

                        if (response.isSuccessful && refreshedLogin?.status == "success") {
                            loginResponse = refreshedLogin
                            val devices = refreshedLogin.data?.devices.orEmpty()
                            _monitorStates.value = devices.associate { it.childId to it.monitorActive }
                        } else {
                            _deleteResult.value = "Failed to refresh children list: ${response.message()}"
                        }
                    } catch (e: Exception) {
                        Log.e("AppViewModel", "Exception in refreshChildren", e)
                        _deleteResult.value = "Error refreshing: ${e.localizedMessage}"
                    }
                }
        }


        fun refreshChildById(context: Context, childId: Long, onRefreshed: (Child?) -> Unit) {
            getOrCreateAppInstanceId(context) { uuid ->
                viewModelScope.launch {
                    try {
                        val response = apiService.userLoginExisting(LoginRequestExisting(emailInput, passwordInput, uuid))
                        val refreshedLogin = response.body()
                        if (response.isSuccessful && refreshedLogin?.status == "success") {
                            loginResponse = refreshedLogin
                            val child = refreshedLogin.data?.children?.find { it.childId == childId }
                            onRefreshed(child)
                        } else onRefreshed(null)
                    } catch (e: Exception) {
                        onRefreshed(null)
                    }
                }
            }
        }
    
        fun addChildProfile(context: Context) {
            viewModelScope.launch {
                try {
                    val userId = loginResponse?.data?.userId?.toInt()
                    val accessToken = loginResponse?.data?.accessToken
                    val request = childNameInput?.let {
                        AddChildProfileRequest(userId, it, selectedIndex, accessToken, zoneIdString)
                    }
                    val response = request?.let { apiService.addChildProfile(it) }
                    if (response != null && response.isSuccessful) {
                        _deleteResult.value = "Child Profile Created Successfully"
                        refreshChildren(context)
                    } else {
                        _deleteResult.value = "Failed to create child profile"
                    }
                } catch (e: IOException) {
                    deleteResult.value = "Network error: ${e.message}"
                } catch (e: HttpException) {
                    deleteResult.value = "Server error: ${e.message}"
                } catch (e: Exception) {
                    deleteResult.value = "Unknown error: ${e.message}"
                }
            }
        }
    
        fun changeChildProfileSettings(userId: Long, childId: Long, context: Context, childName: String) {
            viewModelScope.launch {
                try {
                    val nameInput = if (childNameInput.isNullOrEmpty()) childName else childNameInput!!
                    val request = ChangeChildInfoRequest(userId, childId, nameInput, selectedIndex)
                    val response = apiService.changeChildProfileSettings(request)
                    if (response.isSuccessful) {
                        _deleteResult.value = "Child Profile Changed Successfully"
                        refreshChildren(context)
                        editChildProfilePopupState = false
                    } else {
                        _deleteResult.value = "Failed to Change child profile"
                    }
                } catch (e: IOException) {
                    deleteResult.value = "Network error: ${e.message}"
                } catch (e: HttpException) {
                    deleteResult.value = "Server error: ${e.message}"
                } catch (e: Exception) {
                    deleteResult.value = "Unknown error: ${e.message}"
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
        // endregion
    
        // region --- Device Functions ---
        fun deleteDevice(userId: Long?, uuid: String, context: Context) {
            viewModelScope.launch {
                try {
                    val request = DeleteDeviceRequest(userId = userId, uuid = uuid)
                    val response = apiService.deleteDevice(request)
    
                    if (response.isSuccessful) {
                        _deleteResult.value = "Child Profile Deleted Successfully"
                        refreshChildren(context)
                        deleteDevicePopupState = false
                    } else {
                        _deleteResult.value = "Failed to delete child profile"
                    }
                } catch (e: IOException) {
                    deleteResult.value = "Network error: ${e.message}"
                } catch (e: HttpException) {
                    deleteResult.value = "Server error: ${e.message}"
                } catch (e: Exception) {
                    deleteResult.value = "Unknown error: ${e.message}"
                }
            }
        }
        // endregion
    
        // region --- Monitor Timer ---
        fun startMonitorDurationTimer() {
            viewModelScope.launch {
                while (true) {
                    val currentTime = Instant.now().epochSecond
                    val devices = loginResponse?.data?.devices ?: return@launch
    
                    val updatedDurations = loginResponse?.data?.children?.associate { child ->
                        val relevantDevices = devices.filter {
                            it.childId == child.childId && it.monitorOnOff <= currentTime
                        }
                        val latestDevice = relevantDevices.maxByOrNull { it.monitorOnOff }
                        val duration = latestDevice?.let { currentTime - it.monitorOnOff } ?: 0L
                        child.childId to duration
                    } ?: emptyMap()
    
                    _monitorDurations.value = updatedDurations
                    delay(1000)
                }
            }
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
            val matchingDevices = devices.filter { it.childId == childId }
            val closestDevice = matchingDevices
                .filter { it.monitorOnOff <= currentTime }
                .minByOrNull { currentTime - it.monitorOnOff }
            return closestDevice?.let { currentTime - it.monitorOnOff }
        }
    
        fun toggleMonitor(monitorActive: Boolean, childId: Long, context: Context) {
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
                        refreshChildren(context)
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
        // endregion

        fun updateMainSwitchTime(childId: Long, onTimeMinutes: Int, offTimeMinutes: Int, cumulativeTime: Int, context: Context) {
            viewModelScope.launch {
                try {
                    val request = ChildScheduleRequest(
                        childId = childId,
                        mandatoryBreakAfter = onTimeMinutes,
                        mandatoryBreakDuration = offTimeMinutes,
                        userId = loginResponse?.data?.userId ?: 0L,
                        cumulativeScreenTimeTotalAllowed =  cumulativeTime
                    )

                    val response = apiService.updateChildScreenTimeSettings(request)
                    if (response.isSuccessful && response.body()?.status == "success") {
                        Toast.makeText(context, "Schedule updated successfully", Toast.LENGTH_SHORT).show()
                        refreshChildren(context) // If you want to re-fetch child data
                    } else {
                        Toast.makeText(context, "Failed: ${response.body()}", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    
        // region --- Settings Functions ---
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
                        _deleteResult.value = if (response.isSuccessful) {
                            "Settings updated successfully"
                        } else {
                            "Failed to update settings: ${response.message()}"
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
        // endregion

        fun getDeviceName(): String {
            val manufacturer = Build.MANUFACTURER.capitalize()
            val model = Build.MODEL
            return if (model.startsWith(manufacturer, ignoreCase = true)) {
                model
            } else {
                "$manufacturer $model"
            }
        }


        var isEditMode by mutableStateOf(false)
            private set
    
        fun toggleEditMode() {
            isEditMode = !isEditMode
        }
    
    
        // region --- Utility ---
        private fun getOrCreateAppInstanceId(context: Context, onResult: (String) -> Unit) {
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val existingId = prefs.getString("app_instance_id", null)
    
            if (existingId != null) {
                onResult(existingId)
            } else {
                FirebaseInstallations.getInstance().id
                    .addOnSuccessListener { fid ->
                        prefs.edit().putString("app_instance_id", fid).apply()
                        Log.d("FID", "New Firebase Installation ID stored: $fid")
                        onResult(fid)
                    }
                    .addOnFailureListener { e ->
                        Log.e("FID", "Failed to get Firebase Installation ID", e)
                        onResult(UUID.randomUUID().toString())
                    }
            }
        }
        // endregion
    
        // Devices selected for deletion
        var selectedDeviceUuids by mutableStateOf(setOf<String>())
            private set
    
        fun toggleDeviceSelection(uuid: String) {
            selectedDeviceUuids = if (uuid in selectedDeviceUuids) {
                selectedDeviceUuids - uuid
            } else {
                selectedDeviceUuids + uuid
            }
        }
    
        fun clearSelectedDevices() {
            selectedDeviceUuids = emptySet()
        }
    
    
    }


    fun timeStringToMinutes(time: String): Int {
        val parts = time.split(":")
        val hours = parts[0].toIntOrNull() ?: 0
        val minutes = parts[1].toIntOrNull() ?: 0
        return hours * 60 + minutes
    }

    
    

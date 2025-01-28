package com.example.myapplication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class AppViewModel: ViewModel() {

    var loginResponse by mutableStateOf<LoginResponse?>(null)
    var userInfo by mutableStateOf<UserResponse?>(null)
    var errorMessage by mutableStateOf("")
    var emailInput by mutableStateOf("")
    var passwordInput by mutableStateOf("")
    var isLoggedIn by mutableStateOf(false)
    var ToggleRequestInfo by mutableStateOf<ToggleRequest?>(null)

    fun Login() {
        val loginData = LoginRequest(emailInput, passwordInput)
        RetrofitInstance.api.UserLogin(loginData).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Assuming the response body contains the login data
                    loginResponse = response.body()
                    isLoggedIn = true
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle network failure or other unexpected errors
                errorMessage = "Failure: ${t.message ?: "Unknown error"}"
                println(errorMessage)
            }
        })
    }

    fun ToggleChildSettings(){
        val request = ToggleRequestInfo
        RetrofitInstance.api.ToggleChildSettings(request).enqueue(object : Callback<ToggleResponse> {
            override fun onResponse(p0: Call<ToggleResponse>, p1: Response<ToggleResponse>) {}

            override fun onFailure(p0: Call<ToggleResponse>, p1: Throwable) {
                errorMessage = "Failure: ${p1.message}"
            }
        })
    }

    fun toggleMonitor(
        monitorActive: Boolean
    ) {
        val request = ToggleMonitor(monitorActive = monitorActive)
        RetrofitInstance.api.ToggleMonitor(request).enqueue(object : Callback<ToggleResponse> {
            override fun onResponse(call: Call<ToggleResponse>, response: Response<ToggleResponse>) {
            }

            override fun onFailure(call: Call<ToggleResponse>, t: Throwable) {
                errorMessage = "Failure: ${t.message}"
            }
        })
    }
}







package com.example.myapplication

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface UserApiService {
    @POST("wp-json/lldb/getInfo")
    fun UserLogin(@Body request: LoginRequest): Call<LoginResponse>

    @POST("wp-json/lldb/getUserWpInfo")
    fun getUserWpInfo(@Body request: UserRequest): Call<ApiResponse>

    @POST("wp-content/plugins/lldb/toggleMonitor.php")
    fun ToggleMonitor(@Body request: ToggleMonitor): Call<ToggleResponse>

    @POST("wp-content/plugins/lldb/updateChildInfo.php")
    fun ToggleChildSettings(@Body request: ToggleRequest?): Call<ToggleResponse>

}

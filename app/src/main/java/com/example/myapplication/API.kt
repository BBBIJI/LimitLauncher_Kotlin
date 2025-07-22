package com.example.myapplication

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface UserApiService {
    @POST("wp-json/lldb/getInfo")
    suspend fun userLogin(@Body request: LoginRequest): Response<LoginResponse>

    @POST("wp-json/custom-register/register")
    suspend fun registerUser(@Body request: UserRegistrationRequest): Response<RegistrationResponse>

    @POST("wp-content/plugins/lldb/updateChildInfo.php")
    suspend fun changeChildProfileSettings(@Body request: ChangeChildInfoRequest?): Response<CallResponse>

    @POST("wp-content/plugins/lldb/updateChildInfo.php")
    suspend fun updateChildScreenTimeSettings(@Body request: ChildScheduleRequest?): Response<CallResponse>

    @POST("wp-content/plugins/lldb/deleteChild.php")
    suspend fun deleteChild(@Body request: DeleteChildRequest): Response<CallResponse>

    @POST("wp-content/plugins/lldb/deleteDevice.php")
    suspend fun deleteDevice(@Body request: DeleteDeviceRequest): Response<CallResponse>

    @POST("wp-json/lldb/getUserWpInfo")
    suspend fun getUserWpInfo(@Body request: UserRequest): Response<ApiResponse>

    @POST("wp-content/plugins/lldb/toggleMonitor.php")
    suspend fun toggleMonitor(@Body request: ToggleMonitor): Response<CallResponse>

    @POST("wp-content/plugins/lldb/updateChildInfo.php")
    suspend fun toggleChildSettings(@Body request: ToggleRequest?): Response<CallResponse>

    @POST("wp-content/plugins/lldb/updateDeviceInfo.php")
    suspend fun addChildProfile(@Body request: AddChildProfileRequest): Response<CallResponse>
}

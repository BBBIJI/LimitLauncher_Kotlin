package com.example.myapplication

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val status: String,
    val data: Data,
)

data class Data(
    val userId: Long,
    val userFirstname: String,
    val userLastname: String,
    val userEmail: String,
    val accessToken: Any?,
    val subscriptionName: String,
    val subscription: String,
    val sku: String,
    val purchaseToken: Any?,
    val autoRenew: Boolean,
    val paymentMethod: String,
    val children: List<Children>,
    val devices: List<Device>,
    val parentDevices: List<Any?>,
    val childRequests: ChildRequests,
    val serverTime: Long,
)

data class Children(
    val childId: Long,
    val childName: String,
    val childIcon: Any?,
    val timezone: String,
    val gamingBlocked: Boolean,
    val socialMediaBlocked: Boolean,
    val youTubeBlocked: Boolean,
    val timerDuration: Long,
    val mainSwitchAutoOffTime: Long,
    val mainSwitchAutoOnTime: Long,
    val mandatoryBreakAfter: Long,
    val mandatoryBreakDuration: Long,
    val cumulativeScreenTimeTotalAllowed: Long,
    val screenOffBegin: Long,
    val screenOffEnd: Long,
    val schoolHourBegin: Long,
    val schoolHourEnd: Long,
    val extensionAutoResponse: Long,
)

data class Device(
    val id: Long,
    val deviceUuid: String,
    val deviceName: String,
    val deviceActive: Boolean,
    val childId: Long,
    val childName: String,
    val childIcon: Any?,
    val deviceSystem: String,
    val deviceRole: String,
    val isSchoolDevice: Boolean,
    val monitorActive: Boolean,
    val monitorOnOff: Long,
    val deviceLastAck: Long,
    val permissionStatus: Long,
    val iosMdmSettingComplete: Boolean,
)

data class ChildRequests(
    val extensionRequests: List<Any?>,
    val loginRequests: List<Any?>,
)

data class UserRequest(
    val userId: Int
)

data class ToggleMonitor(
    val userId:Int = 1983,
    val childId:Int = 1219,
    val monitorActive:Boolean? = null
)

data class ToggleResponse(
    val status: String,
)

data class ApiResponse(
    val status: String,
    val data: UserResponse
)

// Data class for the API response
data class UserResponse(
    val status: String,
    val userId: Int,
    val userFirstname: String,
    val userLastname: String,
    val userEmail: String,
    val roles: List<String>,
    val subscriptionName: String,
    val subscription: String,
    val sku: String,
    val purchaseToken: String?,
    val autoRenew: Boolean,
    val paymentMethod: String
)

//Gaming, Youtube, &Social Media Toggle
data class ToggleRequest(
    val userId: Int = 1983,
    val childId: Int = 1219,
    var gamingBlocked: Boolean? = null,
    var socialMediaBlocked: Boolean? = null,
    var youTubeBlocked: Boolean? = null
)
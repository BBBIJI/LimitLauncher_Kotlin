package com.example.myapplication

import com.google.gson.annotations.SerializedName

// ✅ Secure login request model
data class LoginRequest(
    @SerializedName("username") val username: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("uuid") val uuid: String
)

data class LoginResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: UserData
)

// ✅ Holds user-specific details
data class UserData(
    @SerializedName("userId") val userId: Long,
    @SerializedName("userFirstname") val firstName: String,
    @SerializedName("userLastname") val lastName: String,
    @SerializedName("userEmail") val email: String,
    @SerializedName("accessToken") val accessToken: String?, // ✅ Changed from Any? to String?
    @SerializedName("subscriptionName") val subscriptionName: String,
    @SerializedName("subscription") val subscription: String,
    @SerializedName("sku") val sku: String,
    @SerializedName("purchaseToken") val purchaseToken: String?, // ✅ Changed from Any?
    @SerializedName("autoRenew") val autoRenew: Boolean,
    @SerializedName("paymentMethod") val paymentMethod: String,
    @SerializedName("children") val children: List<Child>,
    @SerializedName("devices") val devices: List<Device>,
    @SerializedName("parentDevices") val parentDevices: List<ParentDevice>,
    @SerializedName("childRequests") val childRequests: ChildRequests,
    @SerializedName("serverTime") val serverTime: Long,
)

// ✅ Holds child-specific data
data class Child(
    @SerializedName("childId") val childId: Long,
    @SerializedName("childName") val childName: String,
    @SerializedName("childIcon") val childIcon: String?, // ✅ Changed from Any?
    @SerializedName("timezone") val timezone: String,
    @SerializedName("gamingBlocked") val gamingBlocked: Boolean,
    @SerializedName("socialMediaBlocked") val socialMediaBlocked: Boolean,
    @SerializedName("youTubeBlocked") val youTubeBlocked: Boolean,
    @SerializedName("timerDuration") val timerDuration: Long,
    @SerializedName("mainSwitchAutoOffTime") val mainSwitchAutoOffTime: Long,
    @SerializedName("mainSwitchAutoOnTime") val mainSwitchAutoOnTime: Long,
    @SerializedName("mandatoryBreakAfter") val mandatoryBreakAfter: Long,
    @SerializedName("mandatoryBreakDuration") val mandatoryBreakDuration: Long,
    @SerializedName("cumulativeScreenTimeTotalAllowed") val cumulativeScreenTimeTotalAllowed: Long,
    @SerializedName("screenOffBegin") val screenOffBegin: Long,
    @SerializedName("screenOffEnd") val screenOffEnd: Long,
    @SerializedName("schoolHourBegin") val schoolHourBegin: Long,
    @SerializedName("schoolHourEnd") val schoolHourEnd: Long,
    @SerializedName("extensionAutoResponse") val extensionAutoResponse: Long,
)

// ✅ Holds device-specific data
data class Device(
    @SerializedName("id") val id: Long,
    @SerializedName("deviceUuid") val deviceUuid: String,
    @SerializedName("deviceName") val deviceName: String,
    @SerializedName("deviceActive") val deviceActive: Boolean,
    @SerializedName("childId") val childId: Long,
    @SerializedName("childName") val childName: String,
    @SerializedName("childIcon") val childIcon: String?, // ✅ Changed from Any?
    @SerializedName("deviceSystem") val deviceSystem: String,
    @SerializedName("deviceRole") val deviceRole: String,
    @SerializedName("isSchoolDevice") val isSchoolDevice: Boolean,
    @SerializedName("monitorActive") val monitorActive: Boolean,
    @SerializedName("monitorOnOff") val monitorOnOff: Long,
    @SerializedName("deviceLastAck") val deviceLastAck: Long,
    @SerializedName("permissionStatus") val permissionStatus: Long,
    @SerializedName("iosMdmSettingComplete") val iosMdmSettingComplete: Boolean,
)

data class ParentDevice(
    val id: Int,
    val deviceUuid: String,
    val deviceName: String?,   // Nullable since it can be null
    val deviceActive: Boolean
)


data class ChildRequests(
    @SerializedName("extensionRequests") val extensionRequests: List<Any?>,
    @SerializedName("loginRequests") val loginRequests: List<Any?>,
)

data class UserRequest(
    @SerializedName("userId") val userId: Int
)

// ✅ Used for toggling monitor settings
data class ToggleMonitor(
    @SerializedName("userId") val userId: Long,
    @SerializedName("childId") val childId: Long,
    @SerializedName("monitorActive") val monitorActive: Boolean? = null,
    @SerializedName("monitorOnOff") val monitorOnOff: Long
)

data class CallResponse(
    @SerializedName("status") val status: String
)

data class ApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: UserResponse
)

// ✅ User profile data
data class UserResponse(
    @SerializedName("status") val status: String,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userFirstname") val firstName: String,
    @SerializedName("userLastname") val lastName: String,
    @SerializedName("userEmail") val email: String,
    @SerializedName("roles") val roles: List<String>,
    @SerializedName("subscriptionName") val subscriptionName: String,
    @SerializedName("subscription") val subscription: String,
    @SerializedName("sku") val sku: String,
    @SerializedName("purchaseToken") val purchaseToken: String?,
    @SerializedName("autoRenew") val autoRenew: Boolean,
    @SerializedName("paymentMethod") val paymentMethod: String
)

data class ChildScheduleRequest(
    val userId: Long,
    val childId: Long,
    val mandatoryBreakAfter: Int,   // in minutes from midnight (e.g. 14:00 -> 840)
    val mandatoryBreakDuration: Int,   // in minutes from midnight (e.g. 17:00 -> 1020)
    val cumulativeScreenTimeTotalAllowed: Int,   // in minutes from midnight (e.g. 17:00 -> 1020)
)


// ✅ Gaming, YouTube, & Social Media toggle request
data class ToggleRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("childId") val childId: Long,
    @SerializedName("gamingBlocked") var gamingBlocked: Boolean,
    @SerializedName("socialMediaBlocked") var socialMediaBlocked: Boolean,
    @SerializedName("youTubeBlocked") var youTubeBlocked: Boolean
)

data class UserRegistrationRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val role: String = "P",
    val system: String = "Android",
    val deviceName:String,
)

data class RegistrationResponse(
    val status: String,
    val errorCode: Int,
    val errorMessage: String
)



data class DeleteChildRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("childId") val childId: Long
)

// Add Child Profile
data class AddChildProfileRequest(
    val userId: Int?,
    val childName: String,
    val childIcon: String?,
    val accessToken: String?,
    val timezone: String
)

// Change child profile settings
data class ChangeChildInfoRequest(
    val userId: Long,
    val childId: Long,
    val childName: String?,
    val childIcon: String?
)

data class DeleteDeviceRequest(
    @SerializedName("userId") val userId: Long?,
    @SerializedName("uuid") val uuid: String
)



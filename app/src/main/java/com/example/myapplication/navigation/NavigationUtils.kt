package com.example.myapplication.navigation

import com.example.myapplication.Child
import kotlinx.serialization.Serializable


sealed class Screens{
    @Serializable
    data object Home: Screens()

    @Serializable
    data class ChildSettingsMenu(
        val childId : Long,
        val childIcon : String?,
        val childName : String?
    ): Screens()

    @Serializable
    data class ChildSettings(
        val childId : Long
    ): Screens()

    @Serializable
    data object About: Screens()

    @Serializable
    data object Personal: Screens()

    @Serializable
    data object Password: Screens()

    @Serializable
    data object Subscription: Screens()

    @Serializable
    data object SubscriptionLearnMore: Screens()

    @Serializable
    data object CustomerSupport: Screens()

    @Serializable
    data object Language: Screens()

    @Serializable
    data object Devices: Screens()

    @Serializable
    data object SignIn: Screens()

    @Serializable
    data object Register: Screens()

    @Serializable
    data object Login: Screens()

    @Serializable
    data object AddChildDevice: Screens()

    @Serializable
    data object ScreenTimeSettings : Screens()

    @Serializable
    data object AddChildProfile : Screens()
}

sealed class Graphs{
    @Serializable
    data object AuthGraph: Graphs()

    @Serializable
    data object MainGraph: Graphs()
}
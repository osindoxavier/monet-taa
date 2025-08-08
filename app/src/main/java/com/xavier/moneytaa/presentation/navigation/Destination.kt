package com.xavier.moneytaa.presentation.navigation

import kotlinx.serialization.Serializable

// -------- Auth Screens --------
@Serializable
sealed class AuthDestination {
    @Serializable
    object SignIn : AuthDestination()
    @Serializable
    object SignUp : AuthDestination()
    @Serializable
    object MainScreen : AuthDestination()
}

// -------- Main Screens --------
@Serializable
sealed class MainDestination {
    @Serializable
    object Home : MainDestination()
    @Serializable
    object Transaction : MainDestination()
    @Serializable
    object Accounts : MainDestination()
    @Serializable
    object Settings : MainDestination()

    companion object {
        fun fromRoute(route: String): MainDestination? {
            return MainDestination::class.sealedSubclasses.firstOrNull {
                route.contains(it.qualifiedName.toString())
            }?.objectInstance
        }
    }
}

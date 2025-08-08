package com.xavier.moneytaa.presentation.event

sealed class AuthEvent {

    data class EmailChanged(val email: String) : AuthEvent()
    data class PasswordChanged(val password: String) : AuthEvent()
    data class RepeatPasswordChanged(val password: String) : AuthEvent()
    data object SignIn : AuthEvent()
    data object CreateAccount : AuthEvent()
    object LoginWithGoogle : AuthEvent()
    object Logout : AuthEvent()
}
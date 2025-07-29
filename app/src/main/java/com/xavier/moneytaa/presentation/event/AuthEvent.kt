package com.xavier.moneytaa.presentation.event

sealed class AuthEvent {

    data class EmailChanged(val email: String) : AuthEvent()
    data class PasswordChanged(val password: String) : AuthEvent()
    data object LoginWithEmail : AuthEvent()
    object LoginWithGoogle : AuthEvent()
    object Logout : AuthEvent()
}
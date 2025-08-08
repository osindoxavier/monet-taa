package com.xavier.moneytaa.presentation.uiState

data class AuthStateForm(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatPassword: String = "",
    val repeatPasswordError: String? = null
)

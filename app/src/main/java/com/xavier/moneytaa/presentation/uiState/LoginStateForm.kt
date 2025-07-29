package com.xavier.moneytaa.presentation.uiState

data class LoginStateForm(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)

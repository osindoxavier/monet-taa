package com.xavier.moneytaa.domain.model.authentication

data class User(
    val uid: String,
    val email: String,
    val displayName: String?
)
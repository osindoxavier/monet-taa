package com.xavier.moneytaa.domain.model.authentication

data class ValidationResult(
    val successful:Boolean,
    val errorMessage:String?=null
)

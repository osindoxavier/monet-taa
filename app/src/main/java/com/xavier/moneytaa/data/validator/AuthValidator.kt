package com.xavier.moneytaa.data.validator

import com.xavier.moneytaa.domain.model.authentication.ValidationResult

interface AuthValidator {
    fun executeEmail(email: String): ValidationResult
    fun executePassword(password: String): ValidationResult
}
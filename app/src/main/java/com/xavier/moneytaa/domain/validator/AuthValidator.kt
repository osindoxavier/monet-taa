package com.xavier.moneytaa.domain.validator

import com.xavier.moneytaa.domain.model.authentication.ValidationResult

interface AuthValidator {
    fun executeEmail(email: String): ValidationResult
    fun executePassword(password: String): ValidationResult
    fun executeRepeatPassword(password: String, repeatPassword: String): ValidationResult
}
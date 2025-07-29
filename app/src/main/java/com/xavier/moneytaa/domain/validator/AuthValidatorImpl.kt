package com.xavier.moneytaa.domain.validator

import android.util.Patterns
import com.xavier.moneytaa.data.validator.AuthValidator
import com.xavier.moneytaa.domain.model.authentication.ValidationResult

class AuthValidatorImpl : AuthValidator {
    override fun executeEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid email address!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun executePassword(password: String): ValidationResult {
        if (password.isBlank()) {

            return ValidationResult(
                successful = false,
                errorMessage = "Password cannot be empty!"
            )


        }

        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid Password!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
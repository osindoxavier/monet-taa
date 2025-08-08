package com.xavier.moneytaa.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier.moneytaa.domain.validator.AuthValidator
import com.xavier.moneytaa.domain.model.authentication.AuthResponse
import com.xavier.moneytaa.domain.repository.AuthRepository
import com.xavier.moneytaa.presentation.event.AuthEvent
import com.xavier.moneytaa.presentation.uiState.AuthStateForm
import com.xavier.moneytaa.presentation.uiState.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authValidator: AuthValidator
) : ViewModel() {

    var state by mutableStateOf(AuthStateForm())

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Idle)
    val uiState: StateFlow<UIState<String>> = _uiState.asStateFlow()

    fun updateUiState(newState: UIState<String>) {
        _uiState.value = newState
    }

    init {
        checkAuthentication()
    }


    private fun checkAuthentication() {
        viewModelScope.launch {
            updateUiState(UIState.Loading)
            val user = authRepository.getCurrentUser()
            if (user != null) {
                Log.d(TAG, "checkAuthentication: ✅ ${user.email} signed in")
                updateUiState(UIState.Success(data = user.email.toString()))
            } else {
                Log.e(TAG, "checkAuthentication: ❌ signed out")
                updateUiState(UIState.Idle)
            }
        }
    }


    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email, emailError = null
                )
            }

            is AuthEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password, passwordError = null
                )
            }

            is AuthEvent.RepeatPasswordChanged -> {
                state = state.copy(
                    repeatPassword = event.password, repeatPasswordError = null
                )
            }


            is AuthEvent.SignIn -> validateUserInput()
            is AuthEvent.LoginWithGoogle -> loginWithGoogle()
            is AuthEvent.Logout -> logout()
            AuthEvent.CreateAccount -> validateCreateAccountInputs()
        }
    }


    private fun validateUserInput() {
        val emailResult = authValidator.executeEmail(state.email)
        val passwordResult = authValidator.executePassword(state.password)

        val hasError = listOf(
            emailResult, passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage, passwordError = passwordResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            loginWithEmail(email = state.email, password = state.password)
        }
    }

    private fun loginWithEmail(email: String, password: String) {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            authRepository.loginWithEmail(email, password).collect { response ->
                when (response) {
                    is AuthResponse.Success -> {
                        Log.d(TAG, "✅loginWithEmail: ${response.user.email}")
                        updateUiState(UIState.Success(data = response.user.email ?: ""))
                    }

                    is AuthResponse.Error -> {
                        Log.e(TAG, "❌loginWithEmail: ${response.message}")
                        updateUiState(UIState.Error(error = response.message))
                    }
                }
            }
        }
    }

    private fun validateCreateAccountInputs() {
        val emailResult = authValidator.executeEmail(state.email)
        val passwordResult = authValidator.executePassword(state.password)
        val repeatPasswordResult = authValidator.executeRepeatPassword(
            password = state.password,
            repeatPassword = state.repeatPassword
        )

        val hasError = listOf(
            emailResult, passwordResult, repeatPasswordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatPasswordError = repeatPasswordResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            createAccount(email = state.email, password = state.password)
        }
    }

    private fun createAccount(email: String, password: String) {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            authRepository.createAccountWithEmail(email, password).collect { response ->
                when (response) {
                    is AuthResponse.Success -> {
                        Log.d(TAG, "✅createAccount: ${response.user.email}")
                        updateUiState(UIState.Success(data = response.user.email ?: ""))
                    }

                    is AuthResponse.Error -> {
                        Log.e(TAG, "❌createAccount: ${response.message}")
                        updateUiState(UIState.Error(error = response.message))
                    }
                }
            }
        }

    }


    private fun loginWithGoogle() {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            authRepository.sigInWithGoogle().collect { response ->
                when (response) {
                    is AuthResponse.Success -> {
                        Log.d(TAG, "✅loginWithGoogle: ${response.user.email}")
                        updateUiState(UIState.Success(data = response.user.email ?: ""))
                    }

                    is AuthResponse.Error -> {
                        Log.e(TAG, "❌loginWithGoogle: ${response.message}")
                        updateUiState(UIState.Error(error = response.message))
                    }
                }
            }
        }
    }


    fun resetState() {
        updateUiState(UIState.Idle)
    }

    private fun logout() {
        viewModelScope.launch {
            updateUiState(UIState.Loading)
            authRepository.signOut()
            resetState()
        }
    }


    companion object {
        private const val TAG = "AuthenticationViewModel"
    }


}
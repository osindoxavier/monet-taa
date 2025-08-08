package com.xavier.moneytaa

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier.moneytaa.domain.repository.AuthRepository
import com.xavier.moneytaa.presentation.navigation.AuthDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf<AuthDestination?>(null)
        private set

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        viewModelScope.launch {
//            updateUiState(UIState.Loading)
            val user = authRepository.getCurrentUser()
            if (user != null) {
                Log.d(TAG, "checkAuthentication: ✅ ${user.email} signed in")
                startDestination = AuthDestination.MainScreen
                splashCondition = false
//                updateUiState(UIState.Success(data = user.email.toString()))
            } else {
                Log.e(TAG, "checkAuthentication: ❌ signed out")
                startDestination = AuthDestination.SignIn
                splashCondition = false
//                updateUiState(UIState.Idle)
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
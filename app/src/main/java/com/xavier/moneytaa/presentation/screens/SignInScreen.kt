package com.xavier.moneytaa.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xavier.moneytaa.presentation.event.AuthEvent
import com.xavier.moneytaa.presentation.screens.composables.AuthTextField
import com.xavier.moneytaa.presentation.screens.composables.ErrorDialog
import com.xavier.moneytaa.presentation.screens.composables.LoadingDialog
import com.xavier.moneytaa.presentation.uiState.UIState
import com.xavier.moneytaa.presentation.viewmodel.AuthenticationViewModel

@Composable
fun SignInScreen(modifier: Modifier = Modifier, onLoginSuccess: () -> Unit) {
    val viewModel: AuthenticationViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val state = viewModel.state
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is UIState.Error -> {

                ErrorDialog(
                    showDialog = true,
                    title = "Login Error",
                    message = uiState.error,
                    dismissButtonText = "Cancel",
                    onDismissRequest = {
                        viewModel.resetState()
                    },
                    confirmButtonText = "Ok",
                    onConfirm = {
                        viewModel.resetState()
                    }
                )

            }

            UIState.Idle -> {
                AuthTextField(
                    value = state.email,
                    onValueChange = {
                        viewModel.onEvent(AuthEvent.EmailChanged(it))
                    },
                    label = "Email",
                    isPassword = false,
                    hasError = state.emailError != null
                )

                state.emailError?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        textAlign = TextAlign.Right,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                AuthTextField(
                    value = state.password,
                    onValueChange = {
                        viewModel.onEvent(AuthEvent.PasswordChanged(it))
                    },
                    label = "Password",
                    isPassword = false,
                    hasError = state.passwordError != null
                )

                state.passwordError?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        textAlign = TextAlign.Right,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(100.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onEvent(AuthEvent.LoginWithEmail) }) {
                    Text("Sign in")
                }

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onEvent(AuthEvent.LoginWithGoogle) }) {
                    Text("Google Sign in")
                }

            }

            UIState.Loading -> {
                LoadingDialog()
            }

            is UIState.Success<String> -> {
//                Toast.makeText(context, "Signed in as ${uiState.data} signed in", Toast.LENGTH_SHORT).show()
                onLoginSuccess()

            }
        }

    }
}
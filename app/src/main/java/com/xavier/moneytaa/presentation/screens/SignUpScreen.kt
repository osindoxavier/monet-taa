package com.xavier.moneytaa.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xavier.moneytaa.presentation.event.AuthEvent
import com.xavier.moneytaa.presentation.screens.composables.AuthButton
import com.xavier.moneytaa.presentation.screens.composables.AuthTextField
import com.xavier.moneytaa.presentation.screens.composables.ErrorDialog
import com.xavier.moneytaa.presentation.screens.composables.GoogleSignInButton
import com.xavier.moneytaa.presentation.screens.composables.LoadingDialog
import com.xavier.moneytaa.presentation.uiState.AuthStateForm
import com.xavier.moneytaa.presentation.uiState.UIState

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {},
    uiState: UIState<String>,
    state: AuthStateForm,
    onResetState: () -> Unit = {},
    onEvent: (AuthEvent) -> Unit,
) {
    val context = LocalContext.current
    var passwordVisibility by remember {
        mutableStateOf(true)
    }

    val focusManager = LocalFocusManager.current


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
                    title = "Sign Up Error",
                    message = uiState.error,
                    dismissButtonText = "Cancel",
                    onDismissRequest = {
                        onResetState()
                    },
                    confirmButtonText = "Ok",
                    onConfirm = {
                        onResetState()
                    })

            }

            UIState.Idle -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Sign Up",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 10.dp,
                        color = MaterialTheme.colorScheme.onBackground

                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                AuthTextField(
                    value = state.email,
                    onValueChange = {
                        onEvent(AuthEvent.EmailChanged(it))
                    },
                    label = "Email",
                    isPassword = false,
                    hasError = state.emailError != null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )

                state.emailError?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        textAlign = TextAlign.Right,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                AuthTextField(
                    value = state.password,
                    onValueChange = {
                        onEvent(AuthEvent.PasswordChanged(it))
                    },
                    label = "Password",
                    isPassword = passwordVisibility,
                    hasError = state.passwordError != null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )

                state.passwordError?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        textAlign = TextAlign.Right,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                AuthTextField(
                    value = state.repeatPassword,
                    onValueChange = {
                        onEvent(AuthEvent.RepeatPasswordChanged(it))
                    },
                    label = "Re-Enter Password",
                    isPassword = passwordVisibility,
                    hasError = state.repeatPasswordError != null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )

                state.repeatPasswordError?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        textAlign = TextAlign.Right,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                //Sign Up Button
                AuthButton(
                    text = "Sign Up", onClick = onSignUpClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Divider
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = DividerDefaults.Thickness,
                        color = DividerDefaults.color
                    )
                    Text(
                        " Or ",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = DividerDefaults.Thickness,
                        color = DividerDefaults.color
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Google Button
                GoogleSignInButton(onClick = onGoogleClick)
                Spacer(modifier = Modifier.height(16.dp))
                // Sign Up
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onSignInClick) {
                        Text(
                            "Already have an account? ",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                        )
                        Text(
                            "Sign In",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

            }

            UIState.Loading -> {
                LoadingDialog()
            }

            is UIState.Success<String> -> {
                Toast.makeText(
                    context, "Signed in as ${uiState.data} signed in", Toast.LENGTH_SHORT
                ).show()
                onSignUpSuccess()

            }
        }
    }
}
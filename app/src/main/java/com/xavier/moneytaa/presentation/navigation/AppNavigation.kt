package com.xavier.moneytaa.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xavier.moneytaa.presentation.event.AuthEvent
import com.xavier.moneytaa.presentation.screens.MainAppScreen
import com.xavier.moneytaa.presentation.screens.SignInScreen
import com.xavier.moneytaa.presentation.screens.SignUpScreen
import com.xavier.moneytaa.presentation.viewmodel.AuthenticationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: AuthDestination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<AuthDestination.SignIn> {
            val viewModel: AuthenticationViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            val state = viewModel.state

            SignInScreen(
                onSignInClick = {
                    viewModel.onEvent(AuthEvent.SignIn)
                },
                onGoogleClick = {
                    viewModel.onEvent(AuthEvent.LoginWithGoogle)
                },
                onSignUpClick = {
                    navController.navigate(AuthDestination.SignUp)
                },
                onForgotPasswordClick = {
//                    navController.navigate(AuthDestination.SignUp)
                },
                onSignInSuccess = {
                    navController.navigate(AuthDestination.MainScreen)
                },
                uiState = uiState,
                state = state,
                onEvent = viewModel::onEvent,
                onResetState = {
                    viewModel.resetState()
                }
            )
        }

        composable<AuthDestination.SignUp> {
            val viewModel: AuthenticationViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            val state = viewModel.state

            SignUpScreen(
                onSignInClick = {
                    navController.navigate(AuthDestination.SignIn)
                },
                onGoogleClick = {
                    viewModel.onEvent(AuthEvent.LoginWithGoogle)
                },
                onSignUpClick = {
                    viewModel.onEvent(AuthEvent.CreateAccount)
                },
                onSignUpSuccess = {
                    navController.navigate(AuthDestination.MainScreen)
                },
                uiState = uiState,
                state = state,
                onResetState = {
                    viewModel.resetState()
                },
                onEvent = viewModel::onEvent
            )
        }

        composable<AuthDestination.MainScreen> {
            MainAppScreen(rootNavController = navController)
        }

    }
}
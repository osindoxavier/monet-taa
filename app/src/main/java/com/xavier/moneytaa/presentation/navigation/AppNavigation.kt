package com.xavier.moneytaa.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xavier.moneytaa.presentation.screens.HomeScreen
import com.xavier.moneytaa.presentation.screens.MainAppScreen
import com.xavier.moneytaa.presentation.screens.SignInScreen
import com.xavier.moneytaa.presentation.screens.SignUpScreen

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
            SignInScreen(
                onLoginSuccess = {
                    navController.navigate(MainDestination.Home)
                }
            )
        }

        composable<AuthDestination.SignUp> {
            SignUpScreen()
        }

        composable<AuthDestination.MainScreen> {
            MainAppScreen(rootNavController = navController)
        }

    }
}
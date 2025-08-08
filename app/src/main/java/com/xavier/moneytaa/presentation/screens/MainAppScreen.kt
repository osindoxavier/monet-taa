package com.xavier.moneytaa.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.xavier.moneytaa.presentation.navigation.AppBottomBar
import com.xavier.moneytaa.presentation.navigation.MainDestination
import com.xavier.moneytaa.presentation.screens.composables.AccountTopApp
import com.xavier.moneytaa.presentation.screens.composables.HomeTopApp
import com.xavier.moneytaa.presentation.screens.composables.ProfileTopApp
import com.xavier.moneytaa.presentation.screens.composables.TransactionsTopApp

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(rootNavController: NavController) {
    Column {
        Text(text = "Home")
    }
    val navController = rememberNavController()
//
//
//    // Observe back stack safely
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = remember(backStackEntry) {
        MainDestination.fromRoute(backStackEntry?.destination?.route ?: "")
    }

    val showBottomBar = backStackEntry?.destination?.route in listOf(
        MainDestination.Home::class.qualifiedName,
        MainDestination.Transaction::class.qualifiedName,
        MainDestination.Accounts::class.qualifiedName,
        MainDestination.Settings::class.qualifiedName
    )


    Scaffold(
        topBar = {
            when (currentRoute) {
                MainDestination.Home  -> HomeTopApp()
                MainDestination.Transaction ->  TransactionsTopApp()
                MainDestination.Accounts  -> AccountTopApp()
                MainDestination.Settings -> ProfileTopApp()
                null -> {

                }
            }
        },
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainDestination.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MainDestination.Home> { HomeScreen() }
            composable<MainDestination.Transaction> { TransactionsScreen() }
            composable<MainDestination.Accounts> { AccountsScreen() }
            composable<MainDestination.Settings> { SettingsScreen() }
        }
    }

}
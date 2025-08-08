package com.xavier.moneytaa.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomBar(modifier: Modifier = Modifier, navController: NavHostController) {

    val bottomNavItems = listOf(
        BottomNavItem(
            destination = MainDestination.Home,
            label = "Home",
            icon = Icons.Default.Dashboard
        ),
        BottomNavItem(
            destination = MainDestination.Transaction,
            label = "Transactions",
            icon = Icons.Filled.InsertChart
        ),
        BottomNavItem(
            destination = MainDestination.Accounts,
            label = "Accounts",
            icon = Icons.Default.AccountBalance
        ),
        BottomNavItem(
            destination = MainDestination.Settings,
            label = "Settings",
            icon = Icons.Default.PermIdentity
        )
    )

    NavigationBar(modifier = modifier) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = remember(backStackEntry) {
            MainDestination.fromRoute(backStackEntry?.destination?.route ?: "")
        }

        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen,
                onClick = {
                    if (currentRoute != screen.destination) {
                        navController.navigate(screen.destination) {
                            popUpTo(MainDestination.Home) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.label)
                },
                label = { }
            )
        }
    }

}

data class BottomNavItem(
    val destination: MainDestination,
    val label: String,
    val icon: ImageVector
)
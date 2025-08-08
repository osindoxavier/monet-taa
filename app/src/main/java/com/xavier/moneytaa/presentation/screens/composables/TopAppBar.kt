package com.xavier.moneytaa.presentation.screens.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopApp(
    modifier: Modifier = Modifier,
    onSyncClicked: () -> Unit,
    onNotificationClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "Home", style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            IconWithBadge(
                onClick = {
                    onSyncClicked()
                },
                imageVector = Icons.Default.Sync,
                description = "Sync Icon"
            )

            Spacer(modifier = Modifier.width(6.dp))

            IconWithBadge(
                onClick = {
                    onNotificationClicked()
                },
                showBadge = true,
                imageVector = Icons.Default.Notifications,
                description = "Notification Icon"
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsTopApp(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier, title = {
        Text(text = "Transaction", style = MaterialTheme.typography.titleLarge)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTopApp(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier, title = {
        Text(text = "Account", style = MaterialTheme.typography.titleLarge)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopApp(modifier: Modifier = Modifier) {
    TopAppBar(modifier = modifier, title = {
        Text(text = "Settings", style = MaterialTheme.typography.titleLarge)
    })
}

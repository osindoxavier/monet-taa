package com.xavier.moneytaa.presentation.screens.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog(
    isLoading: Boolean = true,
    message: String = "Loading, please wait...",
) {
    if (isLoading) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(120.dp),
                        trackColor = Color.Blue
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorDialog(
    showDialog: Boolean,
    icon: ImageVector = Icons.Default.Info,
    title: String,
    message: String,
    dismissButtonText: String = "Dismiss",
    onDismissRequest: () -> Unit,
    confirmButtonText: String = "Accept",
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest, // Callback for outside taps or back press
            icon = {
                // Display the provided icon
                Icon(
                    imageVector = icon,
                    contentDescription = "Error Icon",
                    tint = MaterialTheme.colorScheme.error // Use error color for the icon
                )
            },
            title = {
                // Display the title with appropriate styling
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth() // Center the title
                )
            },
            text = {
                // Display the error message
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth() // Center the message
                )
            },
            confirmButton = {
                // The confirm button, placed on the right by default in AlertDialog's button row
                TextButton(onClick = onConfirm) {
                    Text(confirmButtonText)
                }
            },
            dismissButton = {
                // The dismiss button, placed on the left by default in AlertDialog's button row
                TextButton(onClick = onDismissRequest) {
                    Text(dismissButtonText)
                }
            },
            // The AlertDialog automatically arranges confirmButton and dismissButton in a row.
            // If you needed more custom button arrangements, you'd use the buttons slot directly.
            // For this requirement, the default behavior is perfect.
        )
    }
}
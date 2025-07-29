package com.xavier.moneytaa.presentation.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    isLoading: Boolean = true,
    message: String = "Loading, please wait...",
    onDismissRequest: () -> Unit = {}
) {
    if (isLoading) {
        Dialog(
            onDismissRequest = onDismissRequest, properties = DialogProperties(
                dismissOnBackPress = false, // Prevents dismissal by back button
                dismissOnClickOutside = false, // Prevents dismissal by tapping outside
                usePlatformDefaultWidth = false // Allows the dialog to fill width if needed
            )
        ) {
            // Box to apply the translucent black background across the whole dialog area
            Box(
                modifier = Modifier
                    .fillMaxSize() // Make the box fill the entire dialog window
                    .background(Color.Black.copy(alpha = 0.6f)), // Black with 60% opacity
                contentAlignment = Alignment.Center // Center the content within this box
            ) {
                // Surface is used for the dialog's background and elevation
                Surface(
                    shape = RoundedCornerShape(16.dp), // Rounded corners for the dialog box
                    color = MaterialTheme.colorScheme.surface, // Use MaterialTheme's surface color
                    shadowElevation = 8.dp, // Add a shadow for depth
                    modifier = Modifier.padding(horizontal = 32.dp) // Add horizontal padding for smaller screens
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp), // Padding around the content inside the dialog
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Circular progress indicator (spinner)
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp), // Size of the spinner
                            color = MaterialTheme.colorScheme.primary // Use primary color for the spinner
                        )
                        // Spacer for some vertical spacing
                        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = 16.dp))
                        // Loading message text
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge, // Use MaterialTheme's typography
                            color = MaterialTheme.colorScheme.onSurface, // Text color
                            textAlign = TextAlign.Center // Center align text
                        )
                    }
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
package com.xavier.moneytaa.presentation.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconWithBadge(
    showBadge: Boolean = false,
    onClick: () -> Unit,
    imageVector: ImageVector,
    description: String = ""
) {
    IconButton(onClick = onClick) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            // Notification icon
            Icon(
                imageVector = imageVector,
                contentDescription = description,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(6.dp)
                    .size(24.dp)
            )

            // Red badge on top-end
            if (showBadge) {
                Box(
                    modifier = Modifier
                        .size(6.dp) // badge size
                        .align(Alignment.TopEnd)
                        .offset(x = (-10).dp, y = (10).dp) // adjust position
                        .clip(CircleShape)
                        .background(Color.Red)
                )
            }
        }
    }
}

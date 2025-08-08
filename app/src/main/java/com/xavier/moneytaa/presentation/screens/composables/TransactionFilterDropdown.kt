package com.xavier.moneytaa.presentation.screens.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xavier.moneytaa.domain.model.transactions.TransactionTimeFilter

@Composable
fun TransactionFilterDropdown(
    modifier:Modifier = Modifier,
    selectedFilter: TransactionTimeFilter,
    onFilterSelected: (TransactionTimeFilter) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = modifier
                .clickable{
                    expanded = true
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Total Balance(${selectedFilter.label})",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 15.sp
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Dropdown",
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(20.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TransactionTimeFilter.entries.forEach { filter ->
                DropdownMenuItem(
                    onClick = {
                        onFilterSelected(filter)
                        expanded = false
                    },
                    text = {
                        Text(text = filter.label)
                    }
                )
            }
        }
    }
}

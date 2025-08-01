package com.xavier.moneytaa.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.xavier.moneytaa.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                viewModel.parseAndSaveSmsMessages()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.parseAndSaveSmsMessages()
        } else {
            permissionLauncher.launch(Manifest.permission.READ_SMS)
        }
    }

    val transactions = viewModel.pagedTransactions.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Last SMS:")
        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(transactions.itemCount) { index ->
                val transaction = transactions[index]
                if (transaction != null) {
                    Text(
                        text = "KES ${transaction.amount} - ${transaction.type}",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            when (transactions.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text("Error loading more items")
                    }
                }

                else -> Unit
            }
        }
    }
}

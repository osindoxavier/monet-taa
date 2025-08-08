package com.xavier.moneytaa.presentation.uiState

import com.xavier.moneytaa.domain.model.transactions.TransactionTimeFilter

data class TransactionUiState(
    val selectedFilter: TransactionTimeFilter = TransactionTimeFilter.LAST_30_DAYS,
    val totalCredit: Double = 0.0,
    val totalDebit: Double = 0.0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
){
    val totalBalance: Double
        get() = totalCredit - totalDebit
}

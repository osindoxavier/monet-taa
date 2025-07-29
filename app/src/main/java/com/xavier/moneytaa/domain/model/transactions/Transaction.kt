package com.xavier.moneytaa.domain.model.transactions

data class Transaction(
    val id: String,
    val userId: String,
    val type: TransactionType,
    val amount: Double,
    val description: String,
    val timestamp: Long
)

enum class TransactionType {
    INCOME, EXPENSE
}

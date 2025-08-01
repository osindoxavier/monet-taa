package com.xavier.moneytaa.domain.model.transactions

import java.util.UUID

data class SmsTransaction(
    val id: Int = 0,
    val userId: String,
    val transactionType: SmsTransactionType,
    val amount: Double,
    val source: String?,
    val message: String,
    val timestamp: Long
)

enum class SmsTransactionType {
    CREDIT, DEBIT, UNKNOWN
}

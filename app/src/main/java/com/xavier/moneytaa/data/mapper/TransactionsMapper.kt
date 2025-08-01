package com.xavier.moneytaa.data.mapper

import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction


fun SmsTransaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        userId = userId,
        type = transactionType,
        amount = amount,
        message = message,
        timestamp = this.timestamp

    )
}

fun TransactionEntity.toSmsTransaction(): SmsTransaction {
    return SmsTransaction(
        id = id,
        userId = userId,
        transactionType = type,
        amount = amount,
        source = null,
        message = message,
        timestamp = timestamp
    )
}
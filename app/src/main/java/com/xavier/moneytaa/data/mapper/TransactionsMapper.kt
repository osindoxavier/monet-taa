package com.xavier.moneytaa.data.mapper

import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction


fun SmsTransaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        userId = userId,
        type = transactionType,
        amount = amount,
        message = message,
        timestamp = this.timestamp,
        source = this.source

    )
}

fun TransactionEntity.toSmsTransaction(): SmsTransaction {
    return SmsTransaction(
        id = id,
        userId = userId,
        transactionType = type,
        amount = amount,
        source = source,
        message = message,
        timestamp = timestamp
    )
}
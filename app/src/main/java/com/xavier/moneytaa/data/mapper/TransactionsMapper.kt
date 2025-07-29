package com.xavier.moneytaa.data.mapper

import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.domain.model.transactions.Transaction
import com.xavier.moneytaa.domain.model.transactions.TransactionType

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id, userId, TransactionType.valueOf(type), amount, description, timestamp
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id, userId, type.name, amount, description, timestamp
)
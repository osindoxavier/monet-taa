package com.xavier.moneytaa.domain.repository

import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.domain.model.transactions.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(txn: Transaction)
}
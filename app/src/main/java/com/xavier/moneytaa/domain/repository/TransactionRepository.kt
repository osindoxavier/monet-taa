package com.xavier.moneytaa.domain.repository

import androidx.paging.Pager
import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getPagedTransactions(userId: String): Pager<Int, TransactionEntity>
    suspend fun addTransaction(smsTransaction: SmsTransaction)
}
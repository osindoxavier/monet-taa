package com.xavier.moneytaa.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction
import com.xavier.moneytaa.domain.model.transactions.TransactionTimeFilter
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getPagedTransactions(userId: String): Pager<Int, TransactionEntity>
    suspend fun addTransaction(smsTransaction: SmsTransaction)
    fun getTransactionsFiltered(
        filter: TransactionTimeFilter,
        userId: String
    ): Flow<PagingData<TransactionEntity>>

}
package com.xavier.moneytaa.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xavier.moneytaa.data.local.dao.TransactionDao
import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.data.mapper.toEntity
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction
import com.xavier.moneytaa.domain.repository.TransactionRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val transactionDao: TransactionDao,
) : TransactionRepository {

    override fun getPagedTransactions(userId: String): Pager<Int, TransactionEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { transactionDao.getTransactionsByUser(userId = userId) }
        )
    }

    override suspend fun addTransaction(smsTransaction: SmsTransaction) {
        val entity = smsTransaction.toEntity()
        transactionDao.insertTransaction(entity)
        syncTransactionsToFirebase(smsTransaction.userId, localTransactions = listOf(entity))
    }

    private suspend fun syncTransactionsToFirebase(
        userId: String,
        localTransactions: List<TransactionEntity>
    ) {
        val batch = firestore.batch()
        val userRef = firestore.collection("users").document(userId)

        localTransactions.forEach { smsTransactions ->
            val txnRef = userRef.collection("transactions").document(smsTransactions.id.toString())
            batch.set(txnRef, smsTransactions)
        }

        batch.commit().await()
    }
}
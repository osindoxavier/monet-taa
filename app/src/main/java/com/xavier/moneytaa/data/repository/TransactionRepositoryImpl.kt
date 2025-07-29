package com.xavier.moneytaa.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xavier.moneytaa.data.local.dao.TransactionDao
import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.data.mapper.toDomain
import com.xavier.moneytaa.data.mapper.toEntity
import com.xavier.moneytaa.domain.model.transactions.Transaction
import com.xavier.moneytaa.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override fun getTransactions(): Flow<List<Transaction>> {
        val uid = auth.currentUser?.uid ?: return flowOf(emptyList())
        return transactionDao.getTransactionsByUser(uid)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addTransaction(txn: Transaction) {
        val entity = txn.toEntity()
        transactionDao.insertTransaction(entity)
        syncTransactionsToFirebase(txn.userId, localTransactions = listOf(entity))
    }

    private suspend fun syncTransactionsToFirebase(
        userId: String,
        localTransactions: List<TransactionEntity>
    ) {
        val batch = firestore.batch()
        val userRef = firestore.collection("users").document(userId)

        localTransactions.forEach { txn ->
            val txnRef = userRef.collection("transactions").document(txn.id)
            batch.set(txnRef, txn)
        }

        batch.commit().await()
    }
}
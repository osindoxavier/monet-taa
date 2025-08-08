package com.xavier.moneytaa.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xavier.moneytaa.data.local.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTransactions(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM sms_transactions WHERE userId = :userId ORDER BY timestamp DESC")
    fun getTransactionsByUser(userId: String): PagingSource<Int, TransactionEntity>

    @Query("SELECT * FROM sms_transactions WHERE timestamp >= :fromTime AND userId = :userId  ORDER BY timestamp DESC")
    fun getTransactionsFrom(userId: String, fromTime: Long): PagingSource<Int, TransactionEntity>

    @Query("""SELECT SUM(amount) FROM sms_transactions WHERE userId = :userId AND timestamp >= :fromTime AND type = 'CREDIT'""")
    suspend fun getTotalCredit(userId: String, fromTime: Long): Double?

    @Query(
        """
        SELECT SUM(amount) FROM sms_transactions
        WHERE userId = :userId AND timestamp >= :fromTime AND type = 'DEBIT'
    """
    )
    suspend fun getTotalDebit(userId: String, fromTime: Long): Double?


}
package com.xavier.moneytaa.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.xavier.moneytaa.domain.model.transactions.SmsTransactionType

@Entity(
    tableName = "sms_transactions",
    indices = [Index(value = ["message"], unique = true)]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val type: SmsTransactionType,
    val amount: Double,
    val timestamp: Long,
    val message: String,
    val source: String
)

package com.xavier.moneytaa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xavier.moneytaa.data.local.dao.TransactionDao
import com.xavier.moneytaa.data.local.dao.UserDao
import com.xavier.moneytaa.data.local.entity.TransactionEntity
import com.xavier.moneytaa.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, TransactionEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
}
package com.xavier.moneytaa.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xavier.moneytaa.data.local.AppDatabase
import com.xavier.moneytaa.data.local.dao.TransactionDao
import com.xavier.moneytaa.data.local.dao.UserDao
import com.xavier.moneytaa.data.repository.TransactionRepositoryImpl
import com.xavier.moneytaa.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {



    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "moneytaa_db"
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()

    @Provides
    @Singleton
    fun provideTransactionRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth,
        transactionDao: TransactionDao
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            firestore = firestore,
            auth = auth,
            transactionDao = transactionDao
        )
    }


}
package com.xavier.moneytaa.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.xavier.moneytaa.data.local.dao.UserDao
import com.xavier.moneytaa.data.repository.AuthRepositoryImpl
import com.xavier.moneytaa.domain.validator.AuthValidator
import com.xavier.moneytaa.domain.repository.AuthRepository
import com.xavier.moneytaa.data.validator.AuthValidatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        @ApplicationContext context: Context,
        usrDao: UserDao
    ): AuthRepository =
        AuthRepositoryImpl(firebaseAuth = firebaseAuth, context = context, userDao = usrDao)

    @Provides
    @Singleton
    fun provideAuthValidator(): AuthValidator = AuthValidatorImpl()
}
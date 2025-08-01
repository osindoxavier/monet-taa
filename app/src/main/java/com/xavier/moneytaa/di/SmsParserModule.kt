package com.xavier.moneytaa.di

import com.xavier.moneytaa.data.repository.SmsParserRepositoryImpl
import com.xavier.moneytaa.domain.repository.SmsParserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SmsParserModule {

    @Binds
    abstract fun bindSmsParserRepository(
        impl: SmsParserRepositoryImpl
    ): SmsParserRepository
}
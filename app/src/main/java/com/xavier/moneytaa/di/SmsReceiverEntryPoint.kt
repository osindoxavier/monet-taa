package com.xavier.moneytaa.di

import com.xavier.moneytaa.domain.repository.SmsParserRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SmsReceiverEntryPoint {
    fun smsParserRepository(): SmsParserRepository
}
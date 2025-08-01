package com.xavier.moneytaa.domain.repository

import androidx.paging.Pager
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction

interface SmsParserRepository {
    //    suspend fun parseSms(message: String): SmsTransaction?
    suspend fun parseAndSaveSmsMessages(userId: String)
}
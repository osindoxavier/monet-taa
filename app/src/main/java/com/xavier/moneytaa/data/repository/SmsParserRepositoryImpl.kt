package com.xavier.moneytaa.data.repository

import android.content.Context
import android.provider.Telephony
import android.util.Log
import com.xavier.moneytaa.data.local.dao.TransactionDao
import com.xavier.moneytaa.data.mapper.toEntity
import com.xavier.moneytaa.domain.model.transactions.SmsMessageData
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction
import com.xavier.moneytaa.domain.model.transactions.SmsTransactionType
import com.xavier.moneytaa.domain.repository.SmsParserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SmsParserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val transactionDao: TransactionDao
) : SmsParserRepository {
    private val transactionKeywords = listOf(
        "received", "sent", "paid", "withdrawn", "withdrew",
        "payment", "buy airtime", "transferred", "deposited",
        "credited", "debited", "was credited", "was debited",
        "has been credited", "has been debited",
        "credit has been made", "debit has been made",
        "a credit has been made", "a debit has been made", "give ksh"
    )


    override suspend fun parseAndSaveSmsMessages(userId: String) {
        val sequence = readSmsFromInboxSequence()
            .filter { sms ->
                transactionKeywords.any { keyword ->
                    sms.body.contains(keyword, ignoreCase = true)
                }
            }

        val batch = mutableListOf<SmsTransaction>()

        for (sms in sequence) {
            val type = detectTransactionType(sms.body)
            val amount = extractAmount(sms.body)

            if (type != SmsTransactionType.UNKNOWN && amount != null) {
                batch += SmsTransaction(
                    userId = userId,
                    transactionType = type,
                    amount = amount,
                    source = sms.address,
                    timestamp = sms.timestamp,
                    message = sms.body
                )
            }

            if (batch.size >= 20) {
                Log.d(TAG, "parseAndSaveSmsMessages: Saving batch of ${batch.size} transactions")
                transactionDao.insertAllTransactions(batch.map { it.toEntity() })
                batch.clear()
            }
        }

        // ✅ Make sure this part always runs, even if batch < 20
        if (batch.isNotEmpty()) {
            Log.d(TAG, "parseAndSaveSmsMessages: Saving remaining ${batch.size} transactions")
            transactionDao.insertAllTransactions(batch.map { it.toEntity() })
        }
    }

    private fun readSmsFromInboxSequence(): Sequence<SmsMessageData> = sequence {
        val uri = Telephony.Sms.Inbox.CONTENT_URI
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.DATE),
            null, null,
            "${Telephony.Sms.DATE} DESC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val address = it.getString(0)
                val body = it.getString(1)
                val timestamp = it.getLong(2)
                yield(SmsMessageData(address, body, timestamp))
            }
        }
    }


    private fun detectTransactionType(body: String): SmsTransactionType {
        val lowercaseBody = body.lowercase()

        val creditIndicators = listOf(
            "credited",
            "has been credited",
            "was credited",
            "a credit has been made",
            "credit has been made",
            "you have received",
            "payment received",
            "received from",
            "deposit",
            "deposited",
            "give ksh"
        )

        val debitIndicators = listOf(
            "debited",
            "has been debited",
            "was debited",
            "a debit has been made",
            "debit has been made",
            "you have sent",
            "payment made",
            "paid to",
            "withdrawn",
            "withdrew",
            "buy airtime",
            "purchase at"
        )

        return when {
            creditIndicators.any { it in lowercaseBody } -> SmsTransactionType.CREDIT
            debitIndicators.any { it in lowercaseBody } -> SmsTransactionType.DEBIT
            else -> SmsTransactionType.UNKNOWN
        }
    }


    private fun extractAmount(body: String): Double? {
        val currencyRegex = Regex(
            pattern = """(?:KES|Ksh|KES\.|Ksh\.?)\s?[\.:]?\s?([\d,]+(?:\.\d{1,2})?)""",
            option = RegexOption.IGNORE_CASE
        )

        val match = currencyRegex.find(body)
        return match?.groups?.get(1)?.value
            ?.replace(",", "")  // Remove thousands separator
            ?.toDoubleOrNull()
    }


    companion object {
        private const val TAG = "SmsParserRepositoryImpl"
    }
}
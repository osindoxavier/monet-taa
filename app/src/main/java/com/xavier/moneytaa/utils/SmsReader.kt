package com.xavier.moneytaa.utils

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import androidx.core.net.toUri
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction
import com.xavier.moneytaa.domain.model.transactions.SmsTransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object SmsReader {
//    private val transactionKeywords = listOf(
//        "received", "sent", "paid", "withdrawn", "withdrew",
//        "payment", "buy airtime", "transferred", "deposited", "credited", "debited"
//    )
//
//    fun readTransactionMessages(context: Context, userId: String): List<SmsTransaction> {
//        val smsList = mutableListOf<SmsTransaction>()
//
//        val cursor = context.contentResolver.query(
//            "content://sms/inbox".toUri(),
//            arrayOf("_id", "address", "body", "date"),
//            null,
//            null,
//            "date DESC"
//        )
//
//        cursor?.use {
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
//
//            while (it.moveToNext()) {
//                val body = it.getString(it.getColumnIndexOrThrow("body"))?.lowercase() ?: continue
//
//                // ✅ Filter out non-transactional messages
//                if (!transactionKeywords.any { keyword -> keyword in body }) continue
//
//                val address = it.getString(it.getColumnIndexOrThrow("address"))
//                val timestamp = it.getLong(it.getColumnIndexOrThrow("date"))
//
//                val dateObj = Date(timestamp)
//                val formattedDate = dateFormat.format(dateObj)
//                val formattedTime = timeFormat.format(dateObj)
//
//                val transaction = SmsTransaction(
//                    userId = userId,
//                    transactionType = detectTransactionType(body),
//                    amount = extractAmount(body),
//                    source = address,
//                    date = formattedDate,
//                    time = formattedTime,
//                    message = body
//                )
//
//                smsList.add(transaction)
//            }
//        }
//
//        return smsList
//    }
//
//    fun detectTransactionType(message: String): SmsTransactionType {
//        return when {
//            message.contains("received", true) || message.contains("credited", true) || message.contains("deposited", true) -> SmsTransactionType.CREDIT
//            message.contains("sent", true) || message.contains("paid", true) || message.contains("withdraw", true) || message.contains("debited", true) -> SmsTransactionType.DEBIT
//            else -> SmsTransactionType.UNKNOWN
//        }
//    }
//
//    fun extractAmount(message: String): Double {
//        val regex = Regex("""(?:Ksh|KES|\bSh\b)?\s?([\d,]+(\.\d{1,2})?)""", RegexOption.IGNORE_CASE)
//        val match = regex.find(message)
//        return match?.groups?.get(1)?.value?.replace(",", "")?.toDoubleOrNull() ?: 0.0
//    }
}
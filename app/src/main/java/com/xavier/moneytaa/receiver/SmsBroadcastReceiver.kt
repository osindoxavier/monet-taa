package com.xavier.moneytaa.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction
import com.xavier.moneytaa.domain.model.transactions.SmsTransactionType
import com.xavier.moneytaa.domain.repository.AuthRepository
import com.xavier.moneytaa.domain.repository.SmsParserRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var smsParserRepository: SmsParserRepository

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(p1)
            for (sms in smsMessages) {
                val address = sms.displayOriginatingAddress
                val body = sms.displayMessageBody
                val timestamp = sms.timestampMillis

                val lowerBody = body.lowercase()

                // Check for transaction keyword
                if (containsTransactionKeyword(lowerBody)) {
                    val transactionType = detectTransactionType(lowerBody)
                    val amount = extractAmount(lowerBody)

                    if (transactionType != SmsTransactionType.UNKNOWN && amount != null) {
                        // Get userId from preferences or fallback
                        CoroutineScope(Dispatchers.IO).launch {
                            val userId = authRepository.getCurrentUser()?.uid ?: return@launch
                            val transaction = SmsTransaction(
                                userId = userId,
                                transactionType = transactionType,
                                amount = amount,
                                source = address,
                                timestamp = timestamp,
                                message = body
                            )
                            Log.d(TAG, "onReceive: $transaction")
//                            smsParserRepository.saveTransaction(transaction)
                        }
                    }
                }
            }
        }
    }

    private fun containsTransactionKeyword(message: String): Boolean {
        val keywords = listOf(
            "received", "sent", "paid", "withdrawn", "withdrew",
            "payment", "buy airtime", "transferred", "deposited", "credited", "debited"
        )
        return keywords.any { message.contains(it, ignoreCase = true) }
    }

    private fun detectTransactionType(message: String): SmsTransactionType {
        return when {
            message.contains("received") || message.contains("credited") || message.contains("deposited") -> SmsTransactionType.CREDIT
            message.contains("sent") || message.contains("paid") || message.contains("debited") || message.contains(
                "withdrawn"
            ) || message.contains("withdrew") -> SmsTransactionType.DEBIT

            else -> SmsTransactionType.UNKNOWN
        }
    }

    private fun extractAmount(message: String): Double? {
        val regex = Regex("""(?:KSh|KES|USD)?[\s]*([0-9,]+(?:\.\d{1,2})?)""")
        return regex.find(message)?.groupValues?.get(1)?.replace(",", "")?.toDoubleOrNull()
    }

    companion object {
        private const val TAG = "SmsBroadcastReceiver"
    }
}
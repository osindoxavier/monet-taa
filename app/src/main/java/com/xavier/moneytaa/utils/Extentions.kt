package com.xavier.moneytaa.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.xavier.moneytaa.data.local.entity.TransactionEntity
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Double.truncateToTwoDecimalPlaces(): Double {
    return (this * 100).toInt() / 100.0
}

@RequiresApi(Build.VERSION_CODES.O)
fun TransactionEntity.groupTitle(): String {
    val now = ZonedDateTime.now()
    val transactionDate = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(timestamp),
        ZoneId.systemDefault()
    ).toLocalDate()

    val today = now.toLocalDate()
    val yesterday = today.minusDays(1)

    return when (transactionDate) {
        today -> "Today"
        yesterday -> "Yesterday"
        else -> DateTimeFormatter.ofPattern("dd-MM-yyyy").format(transactionDate)
    }
}

fun Long.toAmPmFormat(): String {
    val date = Date(this)
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(date).uppercase()
}
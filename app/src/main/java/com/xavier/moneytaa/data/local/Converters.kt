package com.xavier.moneytaa.data.local

import androidx.room.TypeConverter
import com.xavier.moneytaa.domain.model.transactions.SmsTransactionType

class Converters {
    @TypeConverter
    fun fromSmsTransactionType(value: SmsTransactionType): String = value.name

    @TypeConverter
    fun toSmsTransactionType(value: String): SmsTransactionType =
        SmsTransactionType.valueOf(value)
}
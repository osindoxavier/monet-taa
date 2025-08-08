package com.xavier.moneytaa.domain.model.transactions

enum class TransactionTimeFilter(val label: String) {
    ALL_TIME("All Time"),
    LAST_7_DAYS("7 Days"),
    LAST_30_DAYS("30 Days")
}
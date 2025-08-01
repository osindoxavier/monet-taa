package com.xavier.moneytaa.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.xavier.moneytaa.domain.model.transactions.SmsTransaction
import com.xavier.moneytaa.domain.repository.AuthRepository
import com.xavier.moneytaa.domain.repository.SmsParserRepository
import com.xavier.moneytaa.domain.repository.TransactionRepository
import com.xavier.moneytaa.utils.SmsReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val smsParserRepository: SmsParserRepository,
    private val authRepository: AuthRepository,
    private val repository: TransactionRepository
) : ViewModel() {

    val userId = authRepository.getCurrentUser()?.uid ?: ""


    val pagedTransactions =
        repository.getPagedTransactions(userId = userId).flow.cachedIn(viewModelScope)

    fun parseAndSaveSmsMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            smsParserRepository.parseAndSaveSmsMessages(userId = userId)
        }

    }
}
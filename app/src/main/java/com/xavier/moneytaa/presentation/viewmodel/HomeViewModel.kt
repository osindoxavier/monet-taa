package com.xavier.moneytaa.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.xavier.moneytaa.data.local.dao.TransactionDao
import com.xavier.moneytaa.domain.model.transactions.TransactionTimeFilter
import com.xavier.moneytaa.domain.repository.AuthRepository
import com.xavier.moneytaa.domain.repository.SmsParserRepository
import com.xavier.moneytaa.domain.repository.TransactionRepository
import com.xavier.moneytaa.presentation.uiState.TransactionUiState
import com.xavier.moneytaa.presentation.uiState.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val smsParserRepository: SmsParserRepository,
    private val authRepository: AuthRepository,
    private val repository: TransactionRepository,
    private val transactionDao: TransactionDao
) : ViewModel() {

    val userId = authRepository.getCurrentUser()?.uid ?: ""

    fun parseAndSaveSmsMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            smsParserRepository.parseAndSaveSmsMessages(userId = userId)
            fetchTotals()
        }

    }

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    fun onFilterChanged(newFilter: TransactionTimeFilter) {
        _uiState.update { it.copy(selectedFilter = newFilter) }
        fetchTotals(newFilter)
    }

    private fun fetchTotals(filter: TransactionTimeFilter = TransactionTimeFilter.LAST_30_DAYS) {
        viewModelScope.launch {
            val fromTime = getFromTimeMillis(filter)
            val userId = userId // get from auth/session

            val credit = transactionDao.getTotalCredit(userId, fromTime) ?: 0.0
            val debit = transactionDao.getTotalDebit(userId, fromTime) ?: 0.0

            _uiState.update {
                it.copy(
                    totalCredit = credit,
                    totalDebit = debit
                )
            }
        }
    }

    private fun getFromTimeMillis(filter: TransactionTimeFilter): Long {
        val now = System.currentTimeMillis()
        val oneDayMillis = 24L * 60 * 60 * 1000

        return when (filter) {
            TransactionTimeFilter.ALL_TIME -> 0L
            TransactionTimeFilter.LAST_7_DAYS -> now - (7L * oneDayMillis)
            TransactionTimeFilter.LAST_30_DAYS -> now - (30L * oneDayMillis)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val transactions = _uiState
        .map { it.selectedFilter }
        .distinctUntilChanged()
        .flatMapLatest { filter ->
            repository.getTransactionsFiltered(filter, userId = userId)
        }
        .cachedIn(viewModelScope)


    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun logout() {
        viewModelScope.launch {
//            updateUiState(UIState.Loading)
            authRepository.signOut()
//            resetState()
        }
    }


}
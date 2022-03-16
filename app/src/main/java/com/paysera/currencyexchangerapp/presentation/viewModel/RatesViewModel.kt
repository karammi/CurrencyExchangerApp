package com.paysera.currencyexchangerapp.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paysera.currencyexchangerapp.di.qualifier.IoDispatcher
import com.paysera.currencyexchangerapp.domain.entity.Rates
import com.paysera.currencyexchangerapp.domain.usecase.FetchRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val fetchRatesUseCase: FetchRatesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _rates = MutableStateFlow<Rates?>(null)
    val currencies = mutableStateOf(_rates.value?.rates)

    private val _myBalances = MutableStateFlow<LinkedHashMap<String, Double>?>(null)
    val myBalancesSell: StateFlow<Map<String, Double>?> = _myBalances

    val rates = MutableStateFlow<List<String>?>(null)

    val sellSelectedRate = MutableStateFlow(Pair<String?, String>(null, ""))
    val receiveSelectedRate = MutableStateFlow(Pair<String?, String>(null, ""))

    var isShowSellSheet = mutableStateOf(false)
        private set
    var isShowReceiveSheet = mutableStateOf(false)
        private set

    var transactionCount = mutableStateOf(0)
        private set

    private val commissionFee: Double = 0.07

    private val job = Job()

    init {
        viewModelScope.launch(ioDispatcher) {
            while (job.isActive) {
                ensureActive()
                val response = fetchRatesUseCase.invoke()
                if (response.isSuccess) {
                    _rates.emit(response.getOrNull())
                    currencies.value = _rates.value?.rates
                }
                delay(15000)
            }
        }

        viewModelScope.launch(ioDispatcher) {
            _rates.collect { currentCurrency ->
                _myBalances.value = currentCurrency?.rates?.mapValues {
                    if (it.key == _rates.value?.base)
                        1000.00
                    else
                        0.00
                } as LinkedHashMap<String, Double>?
            }
        }
        viewModelScope.launch(ioDispatcher) {
            combineTransform(sellSelectedRate, receiveSelectedRate, _rates) { sell, buy, rates ->

                if (rates == null || sell.first.isNullOrEmpty() || buy.first.isNullOrEmpty() || sell.second.isNullOrEmpty()) {
                    emit((-1).toDouble())
                } else {
                    val rate: Double = rates.rates[buy.first]!!
                    emit(kotlin.math.round((sell.second.toDouble() * rate) * 100) / 100)
                }
            }.filterNotNull()
                .filter {
                    it != (-1).toDouble()
                }.collectLatest {
                    receiveSelectedRate.emit(receiveSelectedRate.value.copy(second = it.toString()))
                }
        }
    }

    fun submitTransaction() {
        viewModelScope.launch(ioDispatcher) {
            combineTransform(
                sellSelectedRate,
                receiveSelectedRate,
                _rates,
            ) { sell, receive, rates ->
                emit(Transaction.Loading)
                if (rates == null || sell.first.isNullOrEmpty() ||
                    sell.second.isNullOrEmpty() || receive.first.isNullOrEmpty() ||
                    receive.second.isNullOrEmpty() || sell.second.toDouble() <= 0.0
                ) {
                    emit(Transaction.TransactionError.UnknownError)
                } else if (_myBalances.value!![sell.first]!! <= 0.0) {
                    emit(Transaction.TransactionError.SourceBalanceError)
                } else {
                    val srcBalanceValue = _myBalances.value!![sell.first]
                    val desBalanceValue = _myBalances.value!![receive.first]
                    val srcValue: Double = sell.second.toDouble()
                    val result = if (transactionCount.value > 5)
                        srcBalanceValue!! - srcValue - (((commissionFee * srcBalanceValue) * 100) / 100)
                    else
                        srcBalanceValue!! - srcValue

                    val desValue: Double =
                        receive.second.toDouble() + kotlin.math.round((desBalanceValue!! * 100) / 100)
                    emit(
                        Transaction.Success(
                            sell = sell.first!! to result,
                            receive = receive.first!! to (kotlin.math.round(desValue * 100) / 100),
                            0.0
                        )
                    )
                }
            }.collectLatest { currentTransaction ->
                when (currentTransaction) {
                    Transaction.TransactionError.UnknownError,
                    Transaction.Loading,
                    -> {
                        // show error message based on error type on ui screen
                    }
                    is Transaction.Success -> {
                        // reset values
                        sellSelectedRate.update {
                            it.copy(first = null, second = "")
                        }
                        receiveSelectedRate.update {
                            it.copy(first = null, second = "")
                        }
                        // increment transaction count
                        transactionCount.value = transactionCount.value + 1

                        // update source and destination balances value
                        _myBalances.emit(
                            _myBalances.value?.mapValues {
                                when (it.key) {
                                    currentTransaction.sell.first -> currentTransaction.sell.second
                                    currentTransaction.receive.first -> currentTransaction.receive.second
                                    else -> it.value
                                }
                            } as LinkedHashMap<String, Double>?
                        )
                    }
                }
            }
        }
    }

    fun toggleSellSheet() {
        isShowSellSheet.value = !isShowSellSheet.value
    }

    fun toggleReceiveSheet() {
        isShowReceiveSheet.value = !isShowReceiveSheet.value
    }

    fun setSellSelectedRate(selectedRate: String) {
        viewModelScope.launch(ioDispatcher) {
            sellSelectedRate.update { it.copy(first = selectedRate) }
        }
    }

    fun setReceiveSelectedRate(selectedRate: String) {
        viewModelScope.launch(ioDispatcher) {
            receiveSelectedRate.update { it.copy(first = selectedRate) }
        }
    }

    fun setSellValue(amount: String) {
        viewModelScope.launch(ioDispatcher) {
            sellSelectedRate.update { it.copy(second = amount) }
        }
    }

    fun setReceiveValue(amount: String) {
        viewModelScope.launch(ioDispatcher) {
            receiveSelectedRate.update { it.copy(second = amount) }
        }
    }
}

sealed class Transaction {
    object Loading : Transaction()
    data class Success(
        val sell: Pair<String, Double>,
        val receive: Pair<String, Double>,
        val commissionFee: Double,
    ) : Transaction()

    sealed class TransactionError : Transaction() {
        object UnknownError : TransactionError()
        object SourceBalanceError : TransactionError()
        object DestinationBalanceError : TransactionError()
        object CommissionFeeError : TransactionError()
        object TransferError : TransactionError()
    }
}

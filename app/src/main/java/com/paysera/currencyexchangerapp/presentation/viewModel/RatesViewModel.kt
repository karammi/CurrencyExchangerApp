package com.paysera.currencyexchangerapp.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paysera.currencyexchangerapp.di.qualifier.IoDispatcher
import com.paysera.currencyexchangerapp.domain.entity.Rates
import com.paysera.currencyexchangerapp.domain.usecase.FetchRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    var showSellSheet = mutableStateOf(false)
        private set
    var showBuySheet = mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(ioDispatcher) {
            val response = fetchRatesUseCase.invoke()
            if (response.isSuccess) {
                _rates.emit(response.getOrNull())
                currencies.value = _rates.value?.rates
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

                if (_myBalances.value?.entries?.size == 1) {
                    setSellSelectedRate(_myBalances.value?.keys?.first().toString())
                }
            }
        }
        viewModelScope.launch(ioDispatcher) {
            combineTransform(sellSelectedRate, receiveSelectedRate, _rates) { sell, buy, rates ->

                if (rates == null || sell.first.isNullOrEmpty() || buy.first.isNullOrEmpty() || sell.second.isNullOrEmpty()) {
                    emit((-1).toDouble())
                } else {
                    val rate: Double = rates.rates[buy.first]!!
                    emit(sell.second.toDouble() * rate)
//                    emit(currencies.value?.get(sell.first)?.times(buy.second) ?: (-1).toDouble())
                }
            }.filterNotNull()
                .filter {
                    it != (-1).toDouble()
                }.collectLatest {
                    receiveSelectedRate.emit(receiveSelectedRate.value.copy(second = it.toString()))
                }
        }
    }

    fun toggleSellSheet() {
        showSellSheet.value = !showSellSheet.value
    }

    fun toggleReceiveSheet() {
        showBuySheet.value = !showBuySheet.value
    }

    fun setSellSelectedRate(selectedRate: String) {
        viewModelScope.launch(ioDispatcher) {
            sellSelectedRate.update {
                it.copy(first = selectedRate)
            }
        }
    }

    fun setReceiveSelectedRate(selectedRate: String) {
        viewModelScope.launch(ioDispatcher) {
            receiveSelectedRate.update {
                it.copy(first = selectedRate)
            }
        }
    }

    fun setSellValue(amount: String) {
        viewModelScope.launch {
//            sellSelectedRate.emit(sellSelectedRate.value.copy(second = amount))
            sellSelectedRate.update {
                it.copy(second = amount)
            }
        }
    }

    fun setReceiveValue(amount: String) {
        viewModelScope.launch {
            receiveSelectedRate.update {
                it.copy(second = amount)
            }
        }
    }
}

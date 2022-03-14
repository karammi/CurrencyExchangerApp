package com.paysera.currencyexchangerapp.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paysera.currencyexchangerapp.di.qualifier.IoDispatcher
import com.paysera.currencyexchangerapp.domain.entity.Rates
import com.paysera.currencyexchangerapp.domain.usecase.FetchRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
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

    val sellSelectedRate = MutableStateFlow(Pair("Unit", 0.0))
    val buySelectedRate = MutableStateFlow(Pair("Unit", 0.0))

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
                _myBalances.value =
                    currentCurrency?.rates?.toMutableMap() as LinkedHashMap<String, Double>?

                _myBalances.value?.entries?.forEach {
                    if (it.key == "EUR")
                        it.setValue(1000.00)
                    else
                        it.setValue(0.00)
                }
                _myBalances.value?.entries?.apply {
                    this.removeIf {
                        it.value == 0.00
                    }
                }
                if (_myBalances.value?.entries?.size == 1) {
                    setSellSelectedRate(_myBalances.value?.keys?.first().toString())
                }
            }
        }

        viewModelScope.launch(ioDispatcher) {
            sellSelectedRate.zip(
                buySelectedRate
            ) { sell: Pair<String, Double>, buy: Pair<String, Double> ->
                if (sell.second != 0.0)
                    buySelectedRate.emit(
                        buySelectedRate.value.copy(
                            second = currencies.value?.get(buy.first)!! * sell.second
                        )
                    )
            }
        }
    }

    // "USD":1.095662,

    fun toggleSellSheet() {
        showSellSheet.value = !showSellSheet.value
    }

    fun toggleBuySheet() {
        showBuySheet.value = !showBuySheet.value
    }

    fun setSellSelectedRate(selectedRate: String) {
        viewModelScope.launch {
            sellSelectedRate.update {
                it.copy(first = selectedRate)
            }
        }
    }

    fun setBuySelectedRate(selectedRate: String) {
        viewModelScope.launch {
            buySelectedRate.update {
                it.copy(first = selectedRate)
            }
        }
    }

    fun setSellValue(amount: Double) {
        viewModelScope.launch {
            sellSelectedRate.update {
                it.copy(second = amount)
            }
        }
    }

    fun setBuyValue(amount: Double) {
        viewModelScope.launch {
            buySelectedRate.update {
                it.copy(second = amount)
            }
        }
    }
}

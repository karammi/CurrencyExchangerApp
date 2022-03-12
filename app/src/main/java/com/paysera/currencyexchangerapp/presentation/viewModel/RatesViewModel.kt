package com.paysera.currencyexchangerapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paysera.currencyexchangerapp.di.qualifier.IoDispatcher
import com.paysera.currencyexchangerapp.domain.entity.Rates
import com.paysera.currencyexchangerapp.domain.usecase.FetchRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val fetchRatesUseCase: FetchRatesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _rates = MutableStateFlow<Rates?>(null)
    private val rate: StateFlow<Rates?> = _rates

    init {
        viewModelScope.launch(ioDispatcher) {
            println("######################################################")
            val response = fetchRatesUseCase.invoke()
            println(response)
            println("######################################################")
            if (response.isSuccess)
                _rates.emit(response.getOrNull())
        }
    }
}

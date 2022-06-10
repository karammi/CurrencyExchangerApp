package com.paysera.currencyexchangerapp.domain.usecase

import com.paysera.currencyexchangerapp.domain.entity.Rates
import com.paysera.currencyexchangerapp.domain.repository.RateRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * This use case used to fetched data from repository layer
 * */
@ViewModelScoped
class FetchRatesUseCase @Inject constructor(
    private val rateRepository: RateRepository,
) {
    suspend operator fun invoke(): Result<Rates> = rateRepository.fetchRates()
}

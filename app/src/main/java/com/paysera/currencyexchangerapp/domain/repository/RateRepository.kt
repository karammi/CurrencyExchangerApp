package com.paysera.currencyexchangerapp.domain.repository

import com.paysera.currencyexchangerapp.domain.entity.Rates

interface RateRepository {
    suspend fun fetchRates(): Result<Rates>
}

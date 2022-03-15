package com.paysera.currencyexchangerapp.data.dataSource.local

import kotlinx.coroutines.flow.Flow

interface RatesDao {
    suspend fun fetchCurrentRates(): Flow<RateEntity>
}

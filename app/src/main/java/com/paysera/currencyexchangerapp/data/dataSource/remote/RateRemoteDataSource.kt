package com.paysera.currencyexchangerapp.data.dataSource.remote

interface RateRemoteDataSource {

    suspend fun fetchRates(): Result<RateModel>
}

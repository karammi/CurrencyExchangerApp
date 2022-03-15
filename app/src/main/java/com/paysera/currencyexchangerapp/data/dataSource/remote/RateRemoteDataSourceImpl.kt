package com.paysera.currencyexchangerapp.data.dataSource.remote

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RateRemoteDataSourceImpl @Inject constructor(
    private val rateApi: RateApi,
) : RateRemoteDataSource {
    override suspend fun fetchRates(): Result<RateModel> {
        return try {
            val response = rateApi.fetchRates()
            if (response.isSuccessful)
                Result.success(response.body()!!)
            else
                Result.failure(Exception(response.errorBody().toString()))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}

package com.paysera.currencyexchangerapp.data.dataSource.remote

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * this is the network layer, actually it's responsibility is fetching data from server api,
 * i used retrofit response object to handle response for dealing with api error or success
 * result
 * params:
 * [rateApi] retrofit api service
 * */

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

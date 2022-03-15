package com.paysera.currencyexchangerapp.data.dataSource.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RateApi {

    @GET("latest")
    suspend fun fetchRates(
        @Query("access_key") accessKey: String = "a6ba1d2168f74d9af93c008f858c0f20",
    ): Response<RateModel>
}

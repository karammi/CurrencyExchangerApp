package com.paysera.currencyexchangerapp.data.dataSource.remote

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RateRemoteDataSourceImplTest {

    @MockK
    lateinit var rateApi: RateApi
    var rateRemoteDataSourceImpl: RateRemoteDataSourceImpl? = null

    @Before
    fun setup() {
        rateRemoteDataSourceImpl = RateRemoteDataSourceImpl(rateApi = rateApi)
    }

    @Test
    fun `fetch rates call should return dataResult success`() = runBlocking {
        val fakeResponse = RateModel(
            success = true,
            timeStamp = 13123123,
            base = "EUR",
            date = "2022:02:02T09:56:12Z",
            rates = mutableMapOf()
        )

        coEvery { rateApi.fetchRates("") } returns Response.success(fakeResponse)
        val response = rateRemoteDataSourceImpl?.fetchRates()

        coVerify { rateApi.fetchRates("") }
    }
}

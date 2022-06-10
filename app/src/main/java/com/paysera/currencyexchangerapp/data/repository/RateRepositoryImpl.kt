package com.paysera.currencyexchangerapp.data.repository

import com.paysera.currencyexchangerapp.data.dataSource.remote.RateRemoteDataSource
import com.paysera.currencyexchangerapp.data.dataSource.util.RateResponseMapper
import com.paysera.currencyexchangerapp.domain.entity.Rates
import com.paysera.currencyexchangerapp.domain.repository.RateRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * This implementation of class is used to provide data to upper layers,
 * that is domain layer(optional layer)
 * params
 *  first params is [rateRemoteDataSource] for fetching data from server api
 *  second params is [rateResponseMapper] for mapping server result to domain layer entities
 * */

@ViewModelScoped
class RateRepositoryImpl @Inject constructor(
    private val rateRemoteDataSource: RateRemoteDataSource,
    private val rateResponseMapper: RateResponseMapper,
) : RateRepository {

    override suspend fun fetchRates(): Result<Rates> {
        return rateRemoteDataSource
            .fetchRates()
            .map {
                rateResponseMapper.mapFromModel(it)
            }
    }
}

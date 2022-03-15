package com.paysera.currencyexchangerapp.di.module

import com.paysera.currencyexchangerapp.data.dataSource.remote.RateRemoteDataSource
import com.paysera.currencyexchangerapp.data.dataSource.remote.RateRemoteDataSourceImpl
import com.paysera.currencyexchangerapp.data.repository.RateRepositoryImpl
import com.paysera.currencyexchangerapp.domain.repository.RateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RatesBinderModule {

    @Binds
    abstract fun bindRatesRemoteDataSource(remote: RateRemoteDataSourceImpl): RateRemoteDataSource

    @Binds
    abstract fun bindRatesRepository(repo: RateRepositoryImpl): RateRepository
}

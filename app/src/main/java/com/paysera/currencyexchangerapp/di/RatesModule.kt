package com.paysera.currencyexchangerapp.di

import com.paysera.currencyexchangerapp.data.dataSource.remote.RateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object RatesModule {

    @Provides
    fun provideRateApi(retrofit: Retrofit): RateApi = retrofit.create(RateApi::class.java)
}
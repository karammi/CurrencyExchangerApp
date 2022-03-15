package com.paysera.currencyexchangerapp.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class MainDispatcher

@Qualifier
@Retention(value = AnnotationRetention.BINARY)
annotation class ImmediateDispatcher


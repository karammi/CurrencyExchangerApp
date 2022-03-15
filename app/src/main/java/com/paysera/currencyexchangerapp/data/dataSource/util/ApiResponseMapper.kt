package com.paysera.currencyexchangerapp.data.dataSource.util

interface ApiResponseMapper<M, E> {

    fun mapFromModel(model: M): E

    fun mapToModel(entity: E): M
}

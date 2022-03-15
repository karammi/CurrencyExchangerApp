package com.paysera.currencyexchangerapp.data.dataSource.util

interface DbEntityMapper<E, T> {

    fun mapFromEntity(entity: E): T

    fun mapToEntity(model: T): E
}

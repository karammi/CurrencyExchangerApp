package com.paysera.currencyexchangerapp.domain.entity

data class Rates(
    val success: Boolean,
    val timeStamp: Int,
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
)

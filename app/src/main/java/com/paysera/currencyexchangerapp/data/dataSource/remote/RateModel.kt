package com.paysera.currencyexchangerapp.data.dataSource.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RateModel(
    val success: Boolean,
    @Json(name = "timestamp")
    val timeStamp: Int,
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
)

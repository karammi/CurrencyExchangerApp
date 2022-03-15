package com.paysera.currencyexchangerapp.data.dataSource.local

import androidx.room.Entity

@Entity(tableName = "rate_tbl")
data class RateEntity(
    val success: Boolean,
    val timeStamp: Int,
    val base: String,
    val date: String,
    val rates: String,
)

package com.paysera.currencyexchangerapp.presentation.util

/**
 * the [Transaction] sealed class is used to emit states of the screen using flow
 * */

sealed class Transaction {
    object Loading : Transaction()
    data class Success(
        val sell: Pair<String, Double>,
        val receive: Pair<String, Double>,
        val commissionFee: Double,
    ) : Transaction()

    sealed class TransactionError : Transaction() {
        object UnknownError : TransactionError()
        object SourceBalanceError : TransactionError()
        object DestinationBalanceError : TransactionError()
        object CommissionFeeError : TransactionError()
        object TransferError : TransactionError()
    }
}

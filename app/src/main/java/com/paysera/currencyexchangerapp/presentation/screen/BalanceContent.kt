package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@Composable
fun MyBalanceContent(viewModel: RatesViewModel) {
//    var rates: Map<String, Double>? = null
//    LaunchedEffect(key1 = viewModel.rate.collectAsState()) {
//        rates = viewModel.currencies.value
//    }

    val balances = viewModel.myBalancesSell.collectAsState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        balances.value?.entries?.sortedByDescending {
            it.value
        }?.forEach { currentMap ->
            item(
                key = currentMap.key
            ) {
                RateItemContent(balance = currentMap.value, priceUnit = currentMap.key)
            }
        }
    }
}

@Composable
fun RateItemContent(balance: Double, priceUnit: String) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(48.dp)
            .padding(horizontal = 4.dp)
    ) {
        Text(text = balance.toString())
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = priceUnit)
    }
}

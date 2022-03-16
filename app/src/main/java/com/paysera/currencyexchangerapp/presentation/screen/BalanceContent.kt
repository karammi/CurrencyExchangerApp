package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@Composable
fun MyBalanceContent(viewModel: RatesViewModel) {
    val balances = viewModel.myBalancesSell.collectAsState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        println("COLLECTED")
        println("%%%%%%%%%%%%%%%%%%%%%COLLECTED%%%%%%%%%%%%%%%%%%%%%%%%%%%")
        balances.value?.entries?.sortedByDescending {
            it.value
        }?.forEach { currentMap ->
            item(key = currentMap.key) {
                RateItemContent(balance = currentMap.value, priceUnit = currentMap.key)
            }
        }
    }
}

@Composable
fun RateItemContent(balance: Double, priceUnit: String) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth()
            .height(48.dp)
            .shadow(1.dp, RoundedCornerShape(corner = CornerSize(8.dp)))
            .background(Color.LightGray)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = balance.toString())
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = priceUnit)
    }
}

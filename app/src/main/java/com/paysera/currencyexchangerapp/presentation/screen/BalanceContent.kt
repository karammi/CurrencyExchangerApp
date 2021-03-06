package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@Composable
fun MyBalanceContent(viewModel: RatesViewModel) {
    val balances by viewModel.myBalancesSell.collectAsState()
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        val result = balances?.entries?.sortedByDescending {
            it.value
        }
        result?.let { listItem ->
            items(result.size) {
                RateItemContent(
                    balance = listItem[it].value,
                    priceUnit = listItem[it].key
                ) { selectedBalance ->
                    if (selectedBalance.second != 0.0) {
                        viewModel.setSellSelectedRate(selectedBalance.first)
                        viewModel.setSellValue(selectedBalance.second.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun RateItemContent(
    balance: Double,
    priceUnit: String,
    onBalanceClick: (Pair<String, Double>) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth()
            .height(48.dp)
            .shadow(1.dp, RoundedCornerShape(corner = CornerSize(8.dp)))
            .background(Color.LightGray)
            .padding(horizontal = 8.dp)
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                onClick = {
                    onBalanceClick(priceUnit to balance)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = balance.toString(), color = Color.Black)
        Text(text = priceUnit, color = Color.Black)
    }
}

package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencyExchangeContentSection(viewModel: RatesViewModel) {

    val sellRateState = viewModel.sellSelectedRate.collectAsState()
    val buySelectedRate = viewModel.buySelectedRate.collectAsState()

    val balancesState = viewModel.myBalancesSell.collectAsState()
    val rates = balancesState.value?.keys?.toList()
    val ratesBuy = viewModel.currencies.value?.keys?.toList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(text = "CURRENCY EXCHANGE")
        SellContentRow(
            unitTitle = sellRateState.value.first,
            onUnitClick = {
                viewModel.toggleSellSheet()
            },
            textInputValue = sellRateState.value.second.toString(),
            onValueChanged = {
                viewModel.setSellValue(it.toDouble())
            }
        )
        Divider(modifier = Modifier.height(1.dp))

        BuyContentRow(
            unitTitle = buySelectedRate.value.first,
            onUnitClick = {
                viewModel.toggleBuySheet()
            },
            textInputValue = buySelectedRate.value.second.toString(),
            onValueChanged = {
                viewModel.setBuyValue(it.toDouble())
            }
        )

        if (viewModel.showSellSheet.value) {
            RateBottomSheet(
                initialValue = rates,
                isVisible = viewModel.showSellSheet.value,
                onOutsidePressed = { viewModel.toggleSellSheet() },
                isLoading = false,
                onItemClick = {
                    viewModel.setSellSelectedRate(it)
                    viewModel.toggleSellSheet()
                }
            )
        }
        if (viewModel.showBuySheet.value) {
            RateBottomSheet(
                initialValue = ratesBuy,
                isVisible = viewModel.showBuySheet.value,
                onOutsidePressed = { viewModel.toggleBuySheet() },
                isLoading = false,
                onItemClick = {
                    viewModel.setBuySelectedRate(it)
                    viewModel.toggleBuySheet()
                }
            )
        }
    }
}

@Composable
fun SellContentRow(
    unitTitle: String,
    onUnitClick: (String) -> Unit,
    textInputValue: String,
    onValueChanged: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(6.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.ArrowDropUp,
                contentDescription = unitTitle,
                modifier = Modifier
                    .shadow(1.dp, CircleShape)
                    .background(Color.Red)
                    .size(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sell")
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = textInputValue,
                onValueChange = {
                    onValueChanged(it)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(2f),
                textStyle = TextStyle(textAlign = TextAlign.End)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .weight(0.7f)
                    .clickable {
                        onUnitClick(unitTitle)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = unitTitle)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "down",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun BuyContentRow(
    unitTitle: String,
    onUnitClick: (String) -> Unit,
    textInputValue: String,
    onValueChanged: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(6.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.ArrowDropUp,
                contentDescription = "sell",
                modifier = Modifier
                    .shadow(1.dp, CircleShape)
                    .background(Color.Green)
                    .size(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sell")
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = textInputValue,
                onValueChange = { onValueChanged(it) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(2f),
                textStyle = TextStyle(textAlign = TextAlign.End)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .weight(0.7f)
                    .clickable {
                        onUnitClick(unitTitle)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = unitTitle)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "down",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

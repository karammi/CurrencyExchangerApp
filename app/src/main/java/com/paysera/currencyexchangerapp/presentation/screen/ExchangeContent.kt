package com.paysera.currencyexchangerapp.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paysera.currencyexchangerapp.core.ui.theme.iBlue
import com.paysera.currencyexchangerapp.core.ui.theme.iGreen
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@Composable
fun CurrencyExchangeContentSection(viewModel: RatesViewModel) {

    val sellRateState = viewModel.sellSelectedRate.collectAsState()
    val receiveRateState = viewModel.receiveSelectedRate.collectAsState()
    val balancesState = viewModel.myBalancesSell.collectAsState()
    val rates = balancesState.value?.keys?.toList()
    val ratesReceive = viewModel.currencies.value?.keys?.toList()

    BackHandler {

        if (viewModel.isShowReceiveSheet.value) {
            viewModel.toggleReceiveSheet()
            return@BackHandler
        }

        if (viewModel.isShowSellSheet.value) {
            viewModel.toggleSellSheet()
            return@BackHandler
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {

        Text(
            text = "CURRENCY EXCHANGE",
            modifier = Modifier.padding(vertical = 8.dp),
            color = iBlue,
        )

        Spacer(modifier = Modifier.height(8.dp))

        SellContentRow(
            unitTitle = sellRateState.value.first ?: "",
            onUnitClick = {
                viewModel.toggleSellSheet()
            },
            textInputValue = sellRateState.value.second,
            onValueChanged = {
                viewModel.setSellValue(it)
            }
        )

        ReceiveContentRow(
            unitTitle = receiveRateState.value.first ?: "",
            onUnitClick = {
                viewModel.toggleReceiveSheet()
            },
            textInputValue = receiveRateState.value.second,
            onValueChanged = {
                viewModel.setReceiveValue(it)
            }
        )
    }

    if (viewModel.isShowSellSheet.value) {
        RateBottomSheet(
            initialValue = rates,
            isVisible = viewModel.isShowSellSheet.value,
            onOutsidePressed = { viewModel.toggleSellSheet() },
            onItemClick = {
                viewModel.setSellSelectedRate(it)
                viewModel.toggleSellSheet()
            }
        )
    }
    if (viewModel.isShowReceiveSheet.value) {
        RateBottomSheet(
            initialValue = ratesReceive,
            isVisible = viewModel.isShowReceiveSheet.value,
            onOutsidePressed = { viewModel.toggleReceiveSheet() },
            onItemClick = {
                viewModel.setReceiveSelectedRate(it)
                viewModel.toggleReceiveSheet()
            }
        )
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
            .padding(vertical = 16.dp)
            .shadow(8.dp, RoundedCornerShape(6.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.ArrowUpward,
                contentDescription = unitTitle,
                modifier = Modifier
                    .shadow(1.dp, CircleShape)
                    .background(Color.Red)
                    .padding(2.dp)
                    .size(24.dp)
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
                    .clickable(
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        onClick = {
                            onUnitClick(unitTitle)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = unitTitle)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Sell",
                    modifier = Modifier.size(40.dp),
                    tint = iBlue
                )
            }
        }
    }
}

@Composable
fun ReceiveContentRow(
    unitTitle: String,
    onUnitClick: (String) -> Unit,
    textInputValue: String,
    onValueChanged: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .shadow(8.dp, RoundedCornerShape(6.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.ArrowDownward,
                contentDescription = "Receive",
                modifier = Modifier
                    .shadow(1.dp, CircleShape)
                    .background(iGreen)
                    .padding(2.dp)
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Receive")
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(2f),
                textStyle = TextStyle(
                    textAlign = TextAlign.End,
                    color = if (textInputValue.isNotEmpty() && textInputValue.toDouble() > 0.0) Color.Green else Color.Red
                ),
                singleLine = true,
                readOnly = true,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .weight(0.7f)
                    .clickable(
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        onClick = {
                            onUnitClick(unitTitle)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = unitTitle)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Receive",
                    modifier = Modifier.size(40.dp),
                    tint = iBlue
                )
            }
        }
    }
}

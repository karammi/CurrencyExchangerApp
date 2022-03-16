package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatesScreen(viewModel: RatesViewModel = hiltViewModel()) {
    Scaffold(
        bottomBar = {
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {
            Text(
                text = "My Balances",
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            MyBalanceContent(viewModel = viewModel)

            CurrencyExchangeContentSection(viewModel = viewModel)

            Button(
                onClick = {
                    viewModel.submitTransaction()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .shadow(0.dp, RoundedCornerShape(10f)),
                enabled = true

            ) {
                Text(text = "Submit")
            }
        }
    }
}

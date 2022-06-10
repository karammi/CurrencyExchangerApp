package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@Composable
fun RatesScreen(viewModel: RatesViewModel = hiltViewModel()) {
    Scaffold {
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

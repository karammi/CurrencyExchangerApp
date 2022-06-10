package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.paysera.currencyexchangerapp.core.ui.theme.iBlue
import com.paysera.currencyexchangerapp.core.ui.theme.iGreen
import com.paysera.currencyexchangerapp.core.ui.theme.iWhite
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@Composable
fun RatesScreen(viewModel: RatesViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Paysera ",
                            color = iWhite,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            textAlign = TextAlign.Left
                        )
                    },
                    backgroundColor = iBlue,
                    elevation = 0.dp,
                    modifier = Modifier.statusBarsPadding(),
                )
            }
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
                    .shadow(0.dp, RoundedCornerShape(10f))
                    .align(alignment = Alignment.CenterHorizontally),
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = iBlue,
                    contentColor = iWhite,
                    disabledBackgroundColor = iGreen
                )

            ) {
                Text(text = "Submit")
            }
        }
    }
}

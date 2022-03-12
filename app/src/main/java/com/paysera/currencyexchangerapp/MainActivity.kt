package com.paysera.currencyexchangerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.paysera.currencyexchangerapp.presentation.screen.RatesScreen
import com.paysera.currencyexchangerapp.presentation.ui.theme.CurrencyExchangerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyExchangerAppTheme {
                RatesScreen()
            }
        }
    }
}

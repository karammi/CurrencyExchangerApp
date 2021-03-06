package com.paysera.currencyexchangerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.paysera.currencyexchangerapp.core.ui.theme.CurrencyExchangerAppTheme
import com.paysera.currencyexchangerapp.presentation.screen.RatesScreen
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

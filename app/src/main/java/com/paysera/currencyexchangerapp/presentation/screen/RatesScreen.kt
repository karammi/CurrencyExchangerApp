package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.paysera.currencyexchangerapp.presentation.viewModel.RatesViewModel

@Composable
fun RatesScreen(viewModel: RatesViewModel = hiltViewModel()) {
    Scaffold {
        Text(text = "this is a test")
    }
}

package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paysera.currencyexchangerapp.core.BottomSheetTemplate

@OptIn(ExperimentalFoundationApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun RateBottomSheet(
    initialValue: List<String>?,
    isVisible: Boolean,
    onOutsidePressed: () -> Unit,
    isLoading: Boolean,
    onItemClick: (String) -> Unit,
) {
    BottomSheetTemplate(
        isVisible = isVisible,
        onOutsidePressed = onOutsidePressed
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            initialValue?.let {
                CurrencySheetContent(rates = it) {
                    onItemClick(it)
                }
            }
        }
    }
}

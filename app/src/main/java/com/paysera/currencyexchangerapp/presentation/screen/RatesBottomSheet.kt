package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetCurrencies(
    state: ModalBottomSheetState,
    rates: List<String>,
    onItemClick: (String) -> Unit,
) {

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                CurrencySheetContent(rates = rates) {
                    onItemClick(it)
                }
            }
        },
        modifier = Modifier
            .height(400.dp)
    ) {
    }
}

@Composable
fun CurrencySheetContent(rates: List<String>, onItemClick: (String) -> Unit) {
    val lazyColumnState = rememberLazyListState()
    LazyColumn(state = lazyColumnState) {
        itemsIndexed(items = rates) { _, item ->
            CurrencySheetItem(title = item, onItemClick = onItemClick)
        }
    }
}

@Composable
fun CurrencySheetItem(title: String, onItemClick: (String) -> Unit) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(8.dp)
            .clickable {
                onItemClick(title)
            }
    )
}

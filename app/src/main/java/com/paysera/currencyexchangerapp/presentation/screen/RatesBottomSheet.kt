package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetCurrencies(
    state: ModalBottomSheetState,
    myBalances: State<Map<String, Double>?>,
    rates: List<String>,
    onItemClick: (String) -> Unit,
) {

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                rates.let {
                    CurrencySheetContent(rates = it) {
                        onItemClick(it)
//                        viewModel.setSellSelectedRate(it)
                    }
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

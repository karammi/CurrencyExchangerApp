package com.paysera.currencyexchangerapp.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paysera.currencyexchangerapp.core.ui.BottomSheetTemplate
import com.paysera.currencyexchangerapp.core.ui.theme.iBlueDark
import com.paysera.currencyexchangerapp.core.ui.theme.iGreyLight

@OptIn(ExperimentalFoundationApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun RateBottomSheet(
    initialValue: List<String>?,
    isVisible: Boolean,
    onOutsidePressed: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    val listState = rememberLazyListState()

    Surface(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) {
        LazyColumn(state = listState) {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(iGreyLight)
                        .height(48.dp)
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp
                        ),
                    contentAlignment = Alignment.Center,

                ) {
                    Text(
                        text = "Choose your currency:",
                        modifier = Modifier.fillMaxWidth(),
                        color = iBlueDark,
                        fontWeight = FontWeight.Medium
                    )
                }
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .padding(16.dp),
                    color = iBlueDark,

                )
            }
            item {
                BottomSheetTemplate(
                    isVisible = isVisible,
                    onOutsidePressed = onOutsidePressed
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        initialValue?.let { item ->
                            CurrencySheetContent(rates = item) {
                                onItemClick(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

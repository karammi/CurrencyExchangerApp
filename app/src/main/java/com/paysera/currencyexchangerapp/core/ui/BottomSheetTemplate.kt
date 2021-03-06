package com.paysera.currencyexchangerapp.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.insets.imePadding
import com.paysera.currencyexchangerapp.core.ui.theme.iWhite

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun BottomSheetTemplate(
    isVisible: Boolean,
    height: Dp = 300.dp,
    onOutsidePressed: () -> Unit,
    content: @Composable () -> Unit,
) {

    val density = LocalDensity.current

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .imePadding(),
    ) {

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .zIndex(1f)
                .graphicsLayer()
        ) {
            Surface(
                color = Color.Gray.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onOutsidePressed()
                    }
                    .blur(10.dp)
            ) {}
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) {
                with(density) { height.roundToPx() }
            } + scaleIn(
                initialScale = 0.7f,
                animationSpec = spring(stiffness = Spring.StiffnessLow)
            ),
            exit = slideOutVertically {
                with(density) { height.roundToPx() }
            },
            modifier = Modifier.zIndex(2f)
        ) {
            Surface(
                modifier = Modifier
                    .requiredHeight(height)
                    .fillMaxWidth()
                    .graphicsLayer(),
                color = iWhite,
                shape = RoundedCornerShape(
                    topStart = UiConstant.BorderRadiusMedium,
                    topEnd = UiConstant.BorderRadiusMedium,
                )
            ) {
                content()
            }
        }
    }
}

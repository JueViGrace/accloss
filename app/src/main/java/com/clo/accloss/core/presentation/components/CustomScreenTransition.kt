package com.clo.accloss.core.presentation.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScreenTransition
import cafe.adriel.voyager.transitions.ScreenTransitionContent

@Composable
fun CustomScreenTransition(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    transition: AnimatedContentTransitionScope<Screen>.() -> ContentTransform = {
        val (initialOffset, targetOffset) = when (navigator.lastEvent) {
            StackEvent.Pop -> ({ size: Int -> -size }) to ({ size: Int -> size })
            else -> ({ size: Int -> size }) to ({ size: Int -> -size })
        }

        fadeIn(
            animationSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                dampingRatio = Spring.DampingRatioLowBouncy
            )
        ) + slideInHorizontally(
            animationSpec = tween(
                durationMillis = 500
            ),
            initialOffset
        ) togetherWith
            fadeOut(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            ) +
            slideOutHorizontally(
                animationSpec = tween(
                    durationMillis = 500
                ),
                targetOffset
            )
    },
    content: ScreenTransitionContent = { it.Content() }
) {
    ScreenTransition(
        navigator = navigator,
        modifier = modifier,
        content = content,
        transition = transition
    )
}

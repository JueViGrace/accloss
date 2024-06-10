package com.clo.accloss.core.modules.offers.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clo.accloss.core.modules.offers.domain.model.Image
import com.clo.accloss.core.modules.offers.presentation.viewmodel.OffersViewModel
import com.clo.accloss.core.presentation.components.DisplayComponents
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomClickableCard
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import kotlin.math.absoluteValue

object OffersScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<OffersViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        DefaultLayoutComponent(
            topBar = {
                DefaultTopBar(
                    navigationIcon = {
                        DefaultBackArrow {
                            navigator.pop()
                        }
                    }
                )
            },
            state = state.images
        ) { list ->
            ImagesCarouselComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 20.dp
                    ),
                images = list
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ImagesCarouselComponent(
        modifier: Modifier = Modifier,
        images: List<Image>
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            val pagerState = rememberPagerState(pageCount = { images.size })
            HorizontalPager(
                pageSpacing = 16.dp,
                state = pagerState,
            ) { page ->
                CustomClickableCard(
                    modifier = Modifier.graphicsLayer {
                        val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                    colors = CardDefaults.elevatedCardColors()
                        .copy(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(10),
                    elevation = CardDefaults.cardElevation(0.dp),
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize()
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                            .padding(10.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(images[page].enlace)
                            .crossfade(enable = true)
                            .crossfade(200)
                            .build(),
                        contentDescription = images[page].nombre
                    )
                }
            }
        }
    }
}

package com.clo.accloss.core.modules.dashboard.presentation.navigation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.dashboardOptionsMenu
import com.clo.accloss.core.common.Constants.dashboardStatisticsMenu
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ErrorComponent
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.presentation.components.MenuItem
import com.clo.accloss.core.modules.dashboard.presentation.components.DashboardMenu
import com.clo.accloss.core.modules.dashboard.presentation.viewmodel.DashboardViewModel
import com.clo.accloss.management.presentation.screen.ManagementScreen
import com.clo.accloss.products.presentation.screen.ProductsScreen
import kotlin.math.absoluteValue

class DashboardScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<DashboardViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val list = emptyList<String>()

        val pagerState = rememberPagerState(pageCount = { list.size })

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            ) {
                if (list.isNotEmpty()) {
                    HorizontalPager(
                        contentPadding = PaddingValues(horizontal = 110.dp),
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
                            colors = CardDefaults.cardColors()
                                .copy(containerColor = Color.Transparent),
                            shape = RoundedCornerShape(10),
                            elevation = CardDefaults.cardElevation(0.dp),
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(10.dp),
                                model =
                                ImageRequest.Builder(LocalContext.current)
                                    .data(list[page])
                                    .crossfade(enable = true)
                                    .crossfade(200)
                                    .build(),
                                contentDescription = "",
                            )
                        }
                    }
                } else {
                    CustomClickableCard {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomText(
                                text = stringResource(R.string.no_images)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            ) {
                CustomClickableCard(
                    modifier = Modifier.shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(20),
                    ),
                    shape = RoundedCornerShape(20),
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomText(
                                    text = stringResource(R.string.welcome_back),
                                )
                                state.currentSession.DisplayResult(
                                    onLoading = {
                                        LoadingComponent(
                                            modifier = Modifier.size(30.dp)
                                        )
                                    },
                                    onError = { ErrorComponent() },
                                    onSuccess = { session ->
                                        CustomText(
                                            text = session.nombre,
                                        )
                                    },
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        viewModel.updateRates()
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_update_24px),
                                    contentDescription = "Update"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomText(
                                text = stringResource(R.string.rate_of_today),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                            )
                            state.rates.DisplayResult(
                                onLoading = {
                                    LoadingComponent(
                                        modifier = Modifier.size(30.dp)
                                    )
                                },
                                onError = { ErrorComponent() },
                                onSuccess = { remoteRate ->
                                    CustomText(
                                        text = "Bs. ${remoteRate.tasa.roundFormat()}",
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                    )
                                },
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(100.dp)
                    .padding(horizontal = 10.dp)
            ) {
                CustomClickableCard(
                    shape = RoundedCornerShape(10),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
                    ) {
                        dashboardOptionsMenu.forEach { menu ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                CustomClickableCard(
                                    colors = CardDefaults.cardColors().copy(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = RoundedCornerShape(20),
                                    onClick = handleMenuClick(menu, navigator)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            painter = painterResource(id = menu.icon),
                                            contentDescription = stringResource(menu.name),
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                        CustomText(
                                            text = stringResource(menu.name),
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                CustomClickableCard(
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        columns = GridCells.Adaptive(150.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
                    ) {
                        items(
                            items = dashboardStatisticsMenu,
                            key = { item -> item.name }
                        ) { menu ->
                            MenuItem(
                                name = stringResource(menu.name),
                                icon = painterResource(id = menu.icon),
                                onClick = handleMenuClick(menu, navigator)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleMenuClick(menu: DashboardMenu, navigator: Navigator): () -> Unit {
        return when (menu) {
            is DashboardMenu.Managements -> {
                { navigator.parent?.parent?.push(ManagementScreen) }
            }
            is DashboardMenu.Salesmen -> {
                { }
            }
            is DashboardMenu.Customers -> {
                { }
            }
            is DashboardMenu.Offers -> {
                { }
            }
            DashboardMenu.Catalogue -> {
                { navigator.parent?.parent?.push(ProductsScreen) }
            }
            is DashboardMenu.Orders -> {
                { }
            }
            is DashboardMenu.Bills -> {
                { }
            }
        }
    }
}

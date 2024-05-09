package com.clo.accloss.core.presentation.dashboard.presentation.navigation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.dashboard.presentation.viewmodel.DashboardViewModel
import kotlin.math.absoluteValue

class DashboardScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<DashboardViewModel>()
        val state by viewModel.state.collectAsState()

        val list = listOf(
            "https://cloccidental.com/img/1-0001.jpg",
            "https://cloccidental.com/img/1-0002.jpg",
            "https://cloccidental.com/img/1-0001.jpg",
            "https://cloccidental.com/img/1-0001.jpg"
        )

        val pagerState = rememberPagerState(pageCount = { list.size })

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                ) { page ->
                    if(pagerState.currentPage > 0){
                        Button(onClick = { }) {

                        }
                    }
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
                        colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(10),
                        elevation = CardDefaults.cardElevation(0.dp),
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(100.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
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
                    if(pagerState.pageCount <= list.size){
                        Button(onClick = { }) {

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
                            CustomText(
                                text = "Bienvenido de nuevo, ",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                            )
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        // TODO: CLICK
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
                                text = "Tasa del día",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                            )
                            CustomText(
                                text = "Bs. 40",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
                    .requiredHeight(150.dp)
                    .padding(horizontal = 10.dp)
            ) {
                CustomClickableCard(
                    shape = RoundedCornerShape(10),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            CustomClickableCard(
                                shape = RoundedCornerShape(20),
                                colors = CardDefaults.outlinedCardColors().copy(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_shopping_bag_24px),
                                        contentDescription = ""
                                    )
                                    CustomText(text = "Pedidos")
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            CustomClickableCard(
                                shape = RoundedCornerShape(20),
                                colors = CardDefaults.outlinedCardColors().copy(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_shopping_bag_24px),
                                        contentDescription = ""
                                    )
                                    CustomText(text = "Pedidos")
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            CustomClickableCard(
                                shape = RoundedCornerShape(20),
                                colors = CardDefaults.outlinedCardColors().copy(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_shopping_bag_24px),
                                        contentDescription = ""
                                    )
                                    CustomText(text = "Pedidos")
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
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                    }
                }
            }
        }
    }
}

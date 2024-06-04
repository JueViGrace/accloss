package com.clo.accloss.order.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomClickableCard
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.order.presentation.model.OrderDetails
import com.clo.accloss.order.presentation.viewmodel.OrderDetailsViewModel
import com.clo.accloss.orderlines.domain.model.OrderLines
import org.koin.core.parameter.parametersOf

data class OrderDetailsScreen(
    val orderId: String
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<OrderDetailsViewModel>(parameters = { parametersOf(orderId) })
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
            state = state.order
        ) { orderDetails ->
            OrderDetailsContent(
                modifier = Modifier.fillMaxSize(),
                orderDetails = orderDetails
            )
        }
    }

    @Composable
    private fun OrderDetailsContent(
        modifier: Modifier = Modifier,
        orderDetails: OrderDetails
    ) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    CustomText(
                        text = "Doc: ${orderDetails.order?.ktiNdoc}",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )

                    CustomText(
                        text = "Nº ${orderDetails.order?.ktiNroped}",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            item {
                HorizontalDivider()
            }

            item {
                CustomText(
                    text = stringResource(R.string.order_details),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                )
            }

            item {
                CustomClickableCard(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            CustomText(text = orderDetails.order?.ktiTdoc ?: "")
                        }
                    }
                }
            }

            item {
                CustomText(
                    text = stringResource(R.string.order_products),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                )
            }

            items(
                orderDetails.orderLines,
                key = { item -> item.kmvCodart }
            ) { orderLine ->
                OrderLinesComponent(orderLine = orderLine)
            }
        }
    }

    @Composable
    private fun OrderLinesComponent(
        modifier: Modifier = Modifier,
        orderLine: OrderLines
    ) {
        CustomClickableCard {

        }
    }
}

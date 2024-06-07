package com.clo.accloss.order.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.calculateOrderStatus
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.DisplayComponents.CardLabel
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomClickableCard
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.ListComponents.ListFooter
import com.clo.accloss.order.domain.model.Order
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
                orderDetails.order?.let { order ->
                    OrderDetailsComponent(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(horizontal = 10.dp),
                        order = order
                    )
                }
            }

            item {
                HorizontalDivider()
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
                OrderLinesComponent(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(horizontal = 10.dp),
                    orderLine = orderLine
                )
            }

            item {
                ListFooter()
            }
        }
    }

    @Composable
    private fun OrderDetailsComponent(
        modifier: Modifier = Modifier,
        order: Order
    ) {
        val status = calculateOrderStatus(order.kePedstatus)

        val condicion = when (order.ktiCondicion) {
            "1" -> R.string.fac
            "2" -> R.string.n_e
            else -> R.string.not_specified
        }

        val column1 = mapOf(
            Pair(R.string.customer, order.ktiNombrecli),
            Pair(R.string.id_or_rif, order.ktiCodcli),
            Pair(R.string.id_or_rif, order.ktiCodcli),
            Pair(R.string.price_type, order.ktiTipprec.roundFormat(0)),
            Pair(R.string.condition, stringResource(id = condicion)),
        )

        val column2 = mapOf(
            Pair(R.string.net_amount, "${order.ktiTotneto.roundFormat()} $"),
            Pair(R.string.issue_date, order.ktiFchdoc),
            Pair(R.string.status, stringResource(status)),
            Pair(R.string.doc_type, order.ktiTdoc),
        )

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                column1.forEach { (title, value) ->
                    CardLabel(
                        modifier = Modifier.fillMaxWidth(),
                        title = title,
                        value = value,
                        maxLines = 10,
                        valueFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        valueFontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                column2.forEach { (title, value) ->
                    CardLabel(
                        modifier = Modifier.fillMaxWidth(),
                        title = title,
                        value = value,
                        maxLines = 10,
                        valueFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        valueFontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                    )
                }
            }
        }
    }

    @Composable
    private fun OrderLinesComponent(
        modifier: Modifier = Modifier,
        orderLine: OrderLines
    ) {
        CustomClickableCard(
            modifier = modifier,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    CustomText(
                        modifier = Modifier
                            .requiredWidth(width = maxWidth / 1.2f)
                            .align(Alignment.TopStart),
                        text = orderLine.kmvNombre,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        overflow = TextOverflow.Ellipsis,
                    )
                    CustomText(
                        modifier = Modifier.align(Alignment.TopEnd),
                        text = orderLine.kmvCodart,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = "${stringResource(R.string.amount)}: ${orderLine.kmvArtprec.roundFormat()} $"
                    )
                    CustomText(
                        text = "${stringResource(R.string.quantity)}: ${orderLine.kmvCant.roundFormat(0)}"
                    )
                }
            }
        }
    }
}

package com.clo.accloss.bills.presentation.screens

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
import androidx.compose.material3.CardDefaults
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
import com.clo.accloss.billlines.domain.model.BillLines
import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.bills.presentation.model.BillDetails
import com.clo.accloss.bills.presentation.viewmodel.BillDetailViewModel
import com.clo.accloss.core.common.Constants.calculateDocType
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.DisplayComponents.CardLabel
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomClickableCard
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.ListComponents.ListFooter
import org.koin.core.parameter.parametersOf

data class BillDetailScreen(
    val id: String
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<BillDetailViewModel>(parameters = { parametersOf(id) })
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
            state = state.billDetails
        ) { billDetails ->
            BillDetailsContent(
                modifier = Modifier.fillMaxSize(),
                billDetails = billDetails
            )
        }
    }

    @Composable
    private fun BillDetailsContent(
        modifier: Modifier = Modifier,
        billDetails: BillDetails
    ) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
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
                        text = "Doc: ${billDetails.bill?.documento}",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )

                    billDetails.bill?.codcliente?.let {
                        CustomText(
                            text = it,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            item {
                CustomText(
                    text = stringResource(R.string.bill_details),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                )
            }

            item {
                BillDetailsComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    bill = billDetails.bill
                )
            }

            item {
                CustomText(
                    text = stringResource(R.string.bill_products),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                )
            }

            items(
                billDetails.billLines,
                key = { item -> item.codigo }
            ) { billLines ->
                BillProductsComponent(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    billLines = billLines
                )
            }

            item {
                ListFooter()
            }
        }
    }

    @Composable
    private fun BillDetailsComponent(
        modifier: Modifier = Modifier,
        bill: Bill?
    ) {
        val column1 = mapOf(
            Pair(R.string.customer, bill?.nombrecli),
            Pair(R.string.code, bill?.codcliente),
            Pair(R.string.doc_type, stringResource(calculateDocType(bill?.agencia))),
            Pair(R.string.net_amount, "${bill?.dtotneto?.roundFormat()} $"),
        )

        val column2 = mapOf(
            Pair(R.string.issue_date, bill?.emision),
            Pair(R.string.date_of_receipt, bill?.recepcion),
            Pair(R.string.expiration_date, bill?.vence)
        )
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                column1.forEach { (title, value) ->
                    value?.let { str ->
                        CardLabel(
                            modifier = Modifier.fillMaxSize(),
                            title = title,
                            value = str,
                            valueFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            valueFontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                            maxLines = 20
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                column2.forEach { (title, value) ->
                    value?.let { str ->
                        CardLabel(
                            modifier = Modifier.fillMaxSize(),
                            title = title,
                            value = str,
                            maxLines = 5
                        )
                    }
                }
            }
        }
    }

    // TODO: navigate to product details

    @Composable
    private fun BillProductsComponent(
        modifier: Modifier = Modifier,
        billLines: BillLines
    ) {
        CustomClickableCard(
            modifier = modifier,
            colors = CardDefaults.elevatedCardColors(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    CustomText(
                        modifier = Modifier
                            .requiredWidth(width = maxWidth / 1.2f)
                            .align(Alignment.TopStart),
                        text = billLines.nombre
                    )

                    CustomText(
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        text = billLines.codigo,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(text = "${billLines.dmontototal.roundFormat()} $")
                    CustomText(text = "${stringResource(id = R.string.quantity)}: ${billLines.cantidad.roundFormat(0)}")
                }
            }
        }
    }
}

package com.clo.accloss.customer.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.DisplayComponents.CardLabel
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.ListComponents.ListFooter
import com.clo.accloss.customer.presentation.model.CustomerData
import com.clo.accloss.customer.presentation.viewmodel.CustomerDetailsViewModel
import org.koin.core.parameter.parametersOf

data class CustomerDetailsScreen(
    val id: String
) : Screen {
    override val key: ScreenKey = super.key + uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<CustomerDetailsViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsStateWithLifecycle()

        DefaultLayoutComponent(
            topBar = {
                DefaultTopBar(
                    navigationIcon = {
                        DefaultBackArrow {
                            navigator.pop()
                        }
                    },
                    actions = {
                    }
                )
            },
            state = state.customer
        ) { customerData ->
            CustomerDetailsContent(
                modifier = Modifier.fillMaxSize(),
                customerData = customerData
            )
        }
    }

    @Composable
    private fun CustomerDetailsContent(
        modifier: Modifier = Modifier,
        customerData: CustomerData
    ) {
        val column1 = mapOf(
            Pair(R.string.id_or_rif, customerData.customer.codigo),
            Pair(R.string.sub_code, customerData.customer.subcodigo),
            Pair(R.string.email, customerData.customer.email),
            Pair(R.string.phones, customerData.customer.telefonos),
            Pair(R.string.address, customerData.customer.direccion),
            Pair(R.string.person_of_contact, customerData.customer.perscont),
            Pair(
                R.string.special_contributor,
                if (customerData.customer.contribespecial == 1.0) {
                    stringResource(R.string.yes)
                } else {
                    stringResource(R.string.no)
                }
            ),
            Pair(R.string.created_at, customerData.customer.fchcrea),
        )

        val column2 = mapOf(
            Pair(
                R.string.last_sale,
                customerData.customer.diasultvta.roundFormat(0)
            ),
            Pair(
                R.string.last_sale_date,
                customerData.customer.fchultvta
            ),
            Pair(
                R.string.last_sale_amount,
                customerData.customer.mtoultvta.roundFormat()
            ),
            Pair(
                R.string.average_days_between_sales,
                customerData.customer.promdiasvta.roundFormat(0)
            ),
            Pair(
                R.string.average_amount_of_days_between_payments,
                customerData.customer.promdiasp.roundFormat(0)
            ),
            Pair(
                R.string.credit_risk_in_days,
                customerData.customer.riesgocrd.roundFormat(0)
            ),
            Pair(
                R.string.amount_of_emitted_documents,
                customerData.customer.cantdocs.roundFormat(0)
            ),
            Pair(
                R.string.percentage_of_documents_paid_in_time,
                customerData.customer.prcdpagdia.roundFormat()
            ),
            Pair(
                R.string.average_amount_per_document,
                customerData.customer.prommtodoc.roundFormat()
            ),
            Pair(
                R.string.total_emitted_documents_amount,
                customerData.customer.totmtodocs.roundFormat()
            ),
        )

        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
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
                        modifier = Modifier.weight(0.9f),
                        text = customerData.customer.nombre,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        overflow = TextOverflow.Ellipsis
                    )

                    CustomText(
                        modifier = Modifier.weight(0.3f),
                        text = "${stringResource(id = R.string.salesman)}: ${customerData.customer.vendedor}",
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        column1.forEach { (title, value) ->
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = title,
                                value = value.ifEmpty { stringResource(id = R.string.not_specified) },
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = if (value.isEmpty()) {
                                        MaterialTheme.colorScheme.errorContainer
                                    } else {
                                        MaterialTheme.colorScheme.surfaceContainer
                                    },
                                    contentColor = if (value.isEmpty()) {
                                        MaterialTheme.colorScheme.onErrorContainer
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    },
                                ),
                                valueFontSize = if (value == customerData.customer.direccion) {
                                    MaterialTheme.typography.bodyMedium.fontSize
                                } else {
                                    MaterialTheme.typography.titleMedium.fontSize
                                },
                                maxLines = 20
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        column2.forEach { (title, value) ->
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = title,
                                value = formatText(title, value),
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = calculateContainerColor(title, value, customerData),
                                    contentColor = calculateContentColor(title, value, customerData),
                                ),
                                maxLines = 10
                            )
                        }
                    }
                }
            }
            item {
                ListFooter()
            }
        }
    }

    @Composable
    private fun calculateContainerColor(title: Int, value: String, customerData: CustomerData): Color {
        return when {
            value.isEmpty() -> {
                MaterialTheme.colorScheme.errorContainer
            }

            title == R.string.average_days_between_sales && value.toDouble() > 40 -> {
                MaterialTheme.colorScheme.errorContainer
            }

            title == R.string.percentage_of_documents_paid_in_time && value.toDouble() < 50 -> {
                MaterialTheme.colorScheme.errorContainer
            }

            title == R.string.credit_risk_in_days && value.toDouble() > 10 -> {
                MaterialTheme.colorScheme.errorContainer
            }

            title == R.string.last_sale -> {
                if (customerData.customer.diasultvta >= customerData.customer.promdiasvta) {
                    MaterialTheme.colorScheme.errorContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainer
                }
            }

            else -> {
                MaterialTheme.colorScheme.surfaceContainer
            }
        }
    }

    @Composable
    private fun calculateContentColor(title: Int, value: String, customerData: CustomerData): Color {
        return when {
            value.isEmpty() -> {
                MaterialTheme.colorScheme.onErrorContainer
            }

            title == R.string.average_days_between_sales && value.toDouble() > 40 ||
                title == R.string.credit_risk_in_days && value.toDouble() > 10 ||
                title == R.string.percentage_of_documents_paid_in_time && value.toDouble() < 50
            -> {
                MaterialTheme.colorScheme.onErrorContainer
            }

            title == R.string.last_sale -> {
                if (customerData.customer.diasultvta >= customerData.customer.promdiasvta) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            }

            else -> {
                MaterialTheme.colorScheme.onSurface
            }
        }
    }

    @Composable
    private fun formatText(title: Int, value: String): String {
        return when (title) {
            R.string.average_days_between_sales,
            R.string.average_amount_of_days_between_payments,
            R.string.last_sale -> {
                "$value ${stringResource(id = R.string.days)}"
            }

            R.string.percentage_of_documents_paid_in_time -> {
                "$value %"
            }

            R.string.average_amount_per_document,
            R.string.total_emitted_documents_amount,
            R.string.last_sale_amount -> {
                "$value $"
            }

            else -> value.ifEmpty { stringResource(id = R.string.not_specified) }
        }
    }
}

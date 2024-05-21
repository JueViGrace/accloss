package com.clo.accloss.management.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.management.presentation.model.ManagementsUi

@Composable
fun ManagementDetailsContent(
    modifier: Modifier = Modifier,
    management: ManagementsUi
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .clip(CircleShape)
                    .border(
                        color = MaterialTheme.colorScheme.primary,
                        width = 2.dp,
                        shape = CircleShape
                    )
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomText(text = "${stringResource(id = R.string.goal)}: ${management.meta.roundFormat()} $")
                    CustomText(
                        text = "${stringResource(id = R.string.goal_percentage)}: ${management.prcmeta.roundFormat()} %"
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(id = R.string.debts)
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = "${management.deuda.roundFormat()} $",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }

                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(id = R.string.expired)
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = "${management.vencido.roundFormat()} $",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }

                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(id = R.string.net_billed_amount),
                                softWrap = true,
                                maxLines = 2
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = "${management.mtofactneto.roundFormat()} $",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }

                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(id = R.string.paid)
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = "${management.mtocob.roundFormat()} $",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }

                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(R.string.average_amount_per_document),
                                softWrap = true,
                                maxLines = 2
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = "${management.prommtopordoc.roundFormat()} $",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }

                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(R.string.total_sold_amount),
                                softWrap = true,
                                maxLines = 2
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = "${management.totmtodocs.roundFormat()} $",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(R.string.orders_amount),
                                softWrap = true,
                                maxLines = 2
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = management.cantped.roundFormat(0),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }

                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(R.string.document_amount),
                                softWrap = true,
                                maxLines = 2
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = management.cantdocs.roundFormat(0),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }

                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = stringResource(R.string.average_number_of_days_between_sales),
                                softWrap = true,
                                maxLines = 2
                            )
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = "${management.promdiasvta.roundFormat(0)} ${stringResource(R.string.days)}",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            )
                        }
                    }
                }
            }
        }

        item {
            ListFooter()
        }
    }
}

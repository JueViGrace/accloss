package com.clo.accloss.products.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.HUNDRED_INT
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.products.presentation.model.ProductDetails

@Composable
fun ProductDetailContent(
    modifier: Modifier = Modifier,
    productDetails: ProductDetails
) {
    val discount: Double = productDetails.product.dctotope / HUNDRED_INT
    val discountAmount: Double = productDetails.product.precio1 * discount
    val priceWithDiscount: Double = productDetails.product.precio1 - discountAmount

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(productDetails.product.url)
                    .crossfade(enable = true)
                    .crossfade(200)
                    .build(),
                contentDescription = productDetails.product.nombre
            )
        }

        item {
            CustomClickableCard(
                modifier = Modifier.fillParentMaxSize(),
                shape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp),
                colors = CardDefaults.elevatedCardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        text = productDetails.product.nombre,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        softWrap = true,
                        maxLines = 5,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.Start
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (productDetails.product.dctotope > 0) {
                                CustomText(
                                    text = "${priceWithDiscount.roundFormat()} $",
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                CustomText(
                                    text = "${productDetails.product.precio1.roundFormat()} $",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                                    textDecoration = TextDecoration.LineThrough,
                                )
                                CustomClickableCard(
                                    shape = RoundedCornerShape(20),
                                    colors = CardDefaults.elevatedCardColors().copy(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    CustomText(
                                        modifier = Modifier.padding(5.dp),
                                        text = "${productDetails.product.dctotope.roundFormat()} ${
                                            stringResource(
                                                id = R.string.prc_off
                                            )
                                        }",
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                                    )
                                }
                            } else {
                                CustomText(
                                    text = "$ ${productDetails.product.precio1.roundFormat()}",
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                )
                            }
                        }

                        CustomText(
                            text = "${stringResource(id = R.string.reference)}: ${
                                productDetails.product.referencia.ifEmpty {
                                    stringResource(id = R.string.not_specified)
                                }}",
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            5.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(
                                15.dp,
                                Alignment.Top
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(
                                    5.dp,
                                    Alignment.CenterVertically
                                ),
                            ) {
                                CustomText(
                                    text = stringResource(R.string.brand),
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                )
                                CustomText(
                                    text = productDetails.product.marca.ifEmpty {
                                        stringResource(R.string.not_specified)
                                    },
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                    color = if (productDetails.product.marca.isNotEmpty()) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.tertiary
                                    }
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(
                                    5.dp,
                                    Alignment.CenterVertically
                                ),
                            ) {
                                CustomText(
                                    text = stringResource(R.string.sold_by),
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                )
                                CustomText(
                                    text = productDetails.product.unidad.ifEmpty {
                                        stringResource(R.string.not_specified)
                                    },
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                    color = if (productDetails.product.marca.isNotEmpty()) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.tertiary
                                    }
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(
                                    5.dp,
                                    Alignment.CenterVertically
                                ),
                            ) {
                                CustomText(
                                    text = stringResource(R.string.stock),
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                )
                                CustomText(
                                    text = if (productDetails.product.existencia > 0) {
                                        productDetails.product.existencia.roundFormat(0)
                                    } else {
                                        stringResource(
                                            id = R.string.no_stock_available
                                        )
                                    },
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                    color = if (productDetails.product.marca.isNotEmpty()) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.error
                                    }
                                )
                            }

                            if (productDetails.product.enpreventa.isNotEmpty()) {
                                CustomText(
                                    text = stringResource(R.string.on_pre_sale),
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(
                                10.dp,
                                Alignment.Top
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(
                                    5.dp,
                                    Alignment.CenterVertically
                                ),
                            ) {
                                CustomText(
                                    text = stringResource(R.string.available_with),
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                )
                                CustomText(
                                    text = when {
                                        productDetails.product.vtaSolofac == 1 -> {
                                            "FAC"
                                        }

                                        productDetails.product.vtaSolone == 1 -> {
                                            "N/E"
                                        }

                                        else -> {
                                            "FAC, N/E"
                                        }
                                    },
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                )
                            }

                            if (productDetails.product.vtaMax > 0.0) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    verticalArrangement = Arrangement.spacedBy(
                                        5.dp,
                                        Alignment.CenterVertically
                                    ),
                                ) {
                                    CustomText(
                                        text = stringResource(R.string.max_sale_amount),
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                    )
                                    CustomText(
                                        text = productDetails.product.vtaMax.roundFormat(0),
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                    )
                                }
                            }

                            if (productDetails.product.vtaMin > 0.0 && productDetails.product.vtaMinenx != 1.0) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    verticalArrangement = Arrangement.spacedBy(
                                        5.dp,
                                        Alignment.CenterVertically
                                    ),
                                ) {
                                    CustomText(
                                        text = stringResource(R.string.minimum_sale_amount),
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                    )
                                    CustomText(
                                        text = productDetails.product.vtaMin.roundFormat(0),
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                    )
                                }
                            }

                            if (productDetails.product.vtaMinenx > 0.0) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    verticalArrangement = Arrangement.spacedBy(
                                        5.dp,
                                        Alignment.CenterVertically
                                    ),
                                ) {
                                    CustomText(
                                        text = stringResource(R.string.sold_in_packs_of),
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                    )
                                    CustomText(
                                        text = productDetails.product.vtaMin.roundFormat(0),
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                    )
                                }
                            }
                        }
                    }

                    if (productDetails.utilities) {
                        CustomText(
                            text = stringResource(id = R.string.statistics),
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                        )

                        HorizontalDivider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(
                                    10.dp,
                                    Alignment.CenterVertically
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CustomClickableCard(
                                    colors = CardDefaults.elevatedCardColors().copy()
                                ) {
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
                                            text = "${stringResource(R.string.earning)} 1",
                                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                        )
                                        CustomText(
                                            text = "% ${productDetails.product.util1.roundFormat()}",
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                        )
                                    }
                                }

                                CustomClickableCard(
                                    colors = CardDefaults.elevatedCardColors()
                                ) {
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
                                            text = "${stringResource(R.string.earning)} 2",
                                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                        )
                                        CustomText(
                                            text = "% ${productDetails.product.util2.roundFormat()}",
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                        )
                                    }
                                }

                                CustomClickableCard(
                                    colors = CardDefaults.elevatedCardColors()
                                ) {
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
                                            text = "${stringResource(R.string.earning)} 3",
                                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                        )
                                        CustomText(
                                            text = "% ${productDetails.product.util3.roundFormat()}",
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                        )
                                    }
                                }
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(
                                    10.dp,
                                    Alignment.CenterVertically
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CustomClickableCard(
                                    colors = CardDefaults.elevatedCardColors()
                                ) {
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
                                            text = stringResource(R.string.average_cost),
                                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                        )
                                        CustomText(
                                            text = "${productDetails.product.costoProm.roundFormat()} $",
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                        )
                                    }
                                }

                                CustomClickableCard(
                                    colors = CardDefaults.elevatedCardColors()
                                ) {
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
                                            text = stringResource(R.string.date_of_last_purchase),
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                        )
                                        CustomText(
                                            text = productDetails.product.fchUltComp,
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                        )
                                    }
                                }

                                CustomClickableCard(
                                    colors = CardDefaults.elevatedCardColors()
                                ) {
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
                                            text = stringResource(R.string.amount_of_last_purchase),
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                        )
                                        CustomText(
                                            text = productDetails.product.qtyUltComp.roundFormat(0),
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

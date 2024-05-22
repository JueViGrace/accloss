package com.clo.accloss.products.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clo.accloss.R
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.products.domain.model.Product

@Composable
fun ProductListContent(
    modifier: Modifier = Modifier,
    product: Product,
    onSelect: (String) -> Unit
) {
    val discount by rememberSaveable {
        mutableStateOf(product.dctotope > 0.0)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CustomClickableCard(
            onClick = {
                onSelect(product.codigo)
            },
            shape = RoundedCornerShape(10),
            border = if (discount) {
                BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
            } else {
                null
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .weight(0.5f)
                        .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                        .padding(10.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.url)
                        .crossfade(enable = true)
                        .crossfade(200)
                        .build(),
                    contentDescription = product.nombre
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    CustomText(
                        text = product.nombre,
                        softWrap = true,
                        maxLines = 5,
                        textAlign = TextAlign.Justify
                    )

                    CustomText(
                        text = "${stringResource(id = R.string.code)}: ${product.codigo}",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    )

                    CustomText(
                        text = "${stringResource(id = R.string.reference)}: ${product.referencia}",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    )

                    if (product.existencia <= 0) {
                        CustomText(text = stringResource(R.string.no_stock_available))
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomText(
                                text = "${product.precio1.roundFormat()} $",
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                color = if (discount) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )

                            if (discount) {
                                CustomText(
                                    text = stringResource(R.string.tap_to_see_discount),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.clo.accloss.products.presentation.components

import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.RowComponent
import com.clo.accloss.products.domain.model.Product

@Composable
fun ProductListContent(
    product: Product,
    onSelect: (String) -> Unit
) {
    CustomClickableCard(
        onClick = {
            onSelect(product.codigo)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        RowComponent(
                            field = "Código",
                            value = product.codigo
                        )

                        RowComponent(
                            field = "Referencia",
                            value = product.referencia.ifEmpty { "N/A" }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        RowComponent(
                            field = "Marca",
                            value = product.marca.ifEmpty { "N/A" }
                        )

                        val dctoaplicar: Double = product.dctotope / 100
                        val mtodescuento: Double = product.precio1 * dctoaplicar
                        val preciocondescuento: Double = product.precio1 - mtodescuento
                        if (product.dctotope > 0) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
                            ) {
                                CustomText(text = "Precio:")
                                CustomText(
                                    text = "$ ${product.precio1.roundFormat(1)}",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                    textDecoration = TextDecoration.LineThrough
                                )

                                CustomText(text = "->")
                                CustomText(text = "$ ${preciocondescuento.roundFormat(1)}")
                            }
                        } else {
                            RowComponent(
                                field = "Precio",
                                value = "$ ${product.precio1.roundFormat(1)}"
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        RowComponent(
                            field = "Existencia",
                            value = product.existencia.roundFormat(0)
                        )

                        RowComponent(
                            modifier = Modifier
                                .background(
                                    color = if (product.dctotope > 0) {
                                        Color.Green.copy(alpha = 0.3f)
                                    } else {
                                        MaterialTheme.colorScheme.surfaceContainer
                                    },
                                    shape = RoundedCornerShape(30)
                                )
                                .padding(3.dp),
                            field = "Descuento",
                            value = if (product.dctotope > 0) {
                                "${product.dctotope.roundFormat(0)} %"
                            } else {
                                "N/A"
                            },
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        RowComponent(
                            field = "Disponible en",
                            value = when {
                                product.vtaSolofac == 1 -> {
                                    "FAC"
                                }
                                product.vtaSolone == 1 -> {
                                    "N/E"
                                }
                                else -> {
                                    "FAC, N/E"
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

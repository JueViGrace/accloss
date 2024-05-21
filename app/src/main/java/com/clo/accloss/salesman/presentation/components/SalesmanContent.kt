package com.clo.accloss.salesman.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.salesman.domain.model.Salesman

@Composable
fun SalesmanContent(
    modifier: Modifier = Modifier,
    salesman: Salesman
) {
    Column(
        modifier = modifier
    ) {
        CustomText(text = salesman.nombre)
    }
}

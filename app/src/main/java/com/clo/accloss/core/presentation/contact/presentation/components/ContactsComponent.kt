package com.clo.accloss.core.presentation.contact.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.LetterIcon
import com.clo.accloss.vendedor.domain.model.Vendedor

@Composable
fun ContactsComponent(
    modifier: Modifier = Modifier,
    vendedor: Vendedor,
    onSelect: (String) -> Unit
) {
    val letter: String by remember {
        mutableStateOf(vendedor.nombre.first().uppercase())
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onSelect(vendedor.vendedor)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
    ) {
        LetterIcon(letter = letter)

        Column {
            CustomText(
                text = vendedor.nombre,
                overflow = TextOverflow.Ellipsis,
                softWrap = true
            )

            CustomText(
                text = vendedor.vendedor,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}

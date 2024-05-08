package com.clo.accloss.core.presentation.contact.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.clo.accloss.core.presentation.components.LetterIcon
import com.clo.accloss.vendedor.domain.model.Vendedor

@Composable
fun ContactsComponent(
    vendedor: Vendedor,
    onSelect: (String) -> Unit
) {
    val letter: String by remember {
        mutableStateOf(vendedor.nombre.first().uppercase())
    }

    Row {
        LetterIcon(letter = letter)
    }
}

package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

object DisplayComponents {

    @Composable
    fun CustomText(
        modifier: Modifier = Modifier,
        text: String,
        fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
        fontWeight: FontWeight? = MaterialTheme.typography.bodyLarge.fontWeight,
        textAlign: TextAlign = TextAlign.Start,
        maxLines: Int = 2,
        color: Color = Color.Unspecified,
        textDecoration: TextDecoration = TextDecoration.None,
        softWrap: Boolean = true,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign,
            maxLines = maxLines,
            color = color,
            modifier = modifier,
            textDecoration = textDecoration,
            softWrap = softWrap,
            overflow = overflow
        )
    }

    @Composable
    fun CardLabel(
        modifier: Modifier = Modifier,
        verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
            5.dp,
            Alignment.CenterVertically
        ),
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        title: Int,
        value: String,
        softWrap: Boolean = true,
        maxLines: Int = 2,
        titleFontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
        titleFontWeight: FontWeight? = MaterialTheme.typography.bodyLarge.fontWeight,
        valueFontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
        valueFontWeight: FontWeight? = MaterialTheme.typography.titleLarge.fontWeight
    ) {
        CustomClickableCard {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment
            ) {
                CustomText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    text = stringResource(id = title),
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    softWrap = softWrap,
                    maxLines = maxLines
                )
                CustomText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    text = value,
                    fontSize = valueFontSize,
                    fontWeight = valueFontWeight,
                    softWrap = softWrap,
                    maxLines = maxLines
                )
            }
        }
    }

    @Composable
    fun CustomClickableCard(
        modifier: Modifier = Modifier,
        onClick: (() -> Unit)? = null,
        enabled: Boolean = true,
        shape: Shape = RoundedCornerShape(5),
        colors: CardColors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),
        elevation: CardElevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border: BorderStroke? = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        ),
        content: @Composable (ColumnScope.() -> Unit)
    ) {
        if (onClick != null) {
            Card(
                modifier = modifier,
                onClick = onClick,
                enabled = enabled,
                shape = shape,
                elevation = elevation,
                colors = colors,
                border = border,
                content = content
            )
        } else {
            Card(
                modifier = modifier,
                shape = shape,
                elevation = elevation,
                colors = colors,
                border = border,
                content = content
            )
        }
    }

    @Composable
    fun LetterIcon(
        modifier: Modifier = Modifier,
        letter: String,
        fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
        fontWeight: FontWeight? = MaterialTheme.typography.titleLarge.fontWeight
    ) {
        Box(
            modifier = modifier
                .size(50.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = letter,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

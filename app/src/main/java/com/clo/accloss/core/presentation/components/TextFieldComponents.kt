package com.clo.accloss.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText

object TextFieldComponents {
    @Composable
    fun CustomOutlinedTextField(
        modifier: Modifier = Modifier,
        value: String,
        onValueChanged: (String) -> Unit,
        readOnly: Boolean = false,
        enabled: Boolean = true,
        leadingIcon: @Composable (() -> Unit)? = null,
        singleLine: Boolean = true,
        placeholder: @Composable (() -> Unit)? = null,
        label: @Composable (() -> Unit)? = null,
        supportingText: @Composable (() -> Unit)? = null,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary
        ),
        isError: Boolean = false,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardActions: KeyboardActions = KeyboardActions(),
        trailingIcon: @Composable (() -> Unit)? = null,
        maxLines: Int = 1,
        shape: Shape = OutlinedTextFieldDefaults.shape
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChanged,
            enabled = enabled,
            readOnly = readOnly,
            leadingIcon = leadingIcon,
            singleLine = singleLine,
            placeholder = placeholder,
            label = label,
            supportingText = supportingText,
            keyboardOptions = keyboardOptions,
            colors = colors,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            trailingIcon = trailingIcon,
            maxLines = maxLines,
            shape = shape
        )
    }

    @Composable
    fun SearchBarComponent(
        modifier: Modifier = Modifier,
        query: String,
        onQueryChange: (String) -> Unit,
        onSearch: (String) -> Unit,
        placeholder: @Composable (() -> Unit)? = {
            DisplayComponents.CustomText(text = stringResource(R.string.search))
        },
        leadingIcon: @Composable (() -> Unit)? = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_24px),
                contentDescription = "search"
            )
        },
        trailingIcon: @Composable (() -> Unit)? = {
            AnimatedVisibility(
                visible = query.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = {
                        onQueryChange("")
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel_24px),
                        contentDescription = "Cancel"
                    )
                }
            }
        },
        shape: Shape = RoundedCornerShape(20),
        keyboardActions: KeyboardActions = KeyboardActions(onDone = { onSearch(query) }),
        keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = query,
                onValueChanged = onQueryChange,
                leadingIcon = leadingIcon,
                placeholder = placeholder,
                trailingIcon = trailingIcon,
                shape = shape,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions
            )
        }
    }

    @Composable
    fun TextFieldComponent(
        modifier: Modifier = Modifier,
        value: String,
        onChange: (String) -> Unit,
        label: String = "",
        placeholder: String = "",
        supportingText: String? = null,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        leadingIcon: Int,
        trailingIcon: Int? = null,
        onTrailingIconClick: (() -> Unit)? = null,
        errorStatus: Boolean = false,
        readOnly: Boolean = false,
        enabled: Boolean = true,
        visualTransformation: VisualTransformation = VisualTransformation.None
    ) {
        val focus = LocalFocusManager.current
        CustomOutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChanged = {
                onChange(it)
            },
            label = {
                CustomText(text = label)
            },
            readOnly = readOnly,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            placeholder = {
                CustomText(text = placeholder)
            },
            supportingText = if (supportingText != null) {
                { CustomText(text = supportingText) }
            } else {
                null
            },
            keyboardActions = KeyboardActions(
                onNext = {
                    focus.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    focus.clearFocus()
                }
            ),
            leadingIcon = {
                Icon(painter = painterResource(leadingIcon), contentDescription = null)
            },
            trailingIcon = if (trailingIcon != null) {
                {
                    IconButton(
                        onClick = { onTrailingIconClick?.invoke() },
                        enabled = enabled
                    ) {
                        Icon(painter = painterResource(trailingIcon), contentDescription = null)
                    }
                }
            } else {
                null
            },
            isError = errorStatus,
            visualTransformation = visualTransformation
        )
    }
}

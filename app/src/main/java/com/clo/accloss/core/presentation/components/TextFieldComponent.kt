package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

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
    icon: Int,
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
            }
        ),
        leadingIcon = {
            Icon(painter = painterResource(icon), contentDescription = null)
        },
        isError = errorStatus,
        visualTransformation = visualTransformation
    )
}

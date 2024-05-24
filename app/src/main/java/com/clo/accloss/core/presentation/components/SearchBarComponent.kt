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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.clo.accloss.R

@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = {
        CustomText(text = stringResource(R.string.search))
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

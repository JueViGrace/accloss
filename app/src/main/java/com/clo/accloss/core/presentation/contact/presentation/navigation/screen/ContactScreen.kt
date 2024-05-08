package com.clo.accloss.core.presentation.contact.presentation.navigation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.core.presentation.contact.presentation.components.ContactsContent
import com.clo.accloss.core.presentation.contact.presentation.viewmodel.ContactViewModel

class ContactScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ContactViewModel>()
        val state by viewModel.state.collectAsState()

        state.vendedores.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                ErrorScreen(it)
            },
            onSuccess = { list ->
                ContactsContent(
                    vendedores = list,
                    isRefreshing = state.reload ?: false,
                    onRefresh = {
                        viewModel.onRefresh()
                    },
                    onSelect = {

                    }
                )
            },
        )
    }
}

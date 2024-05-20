package com.clo.accloss.core.modules.contact.presentation.navigation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.core.modules.contact.presentation.components.ContactsContent
import com.clo.accloss.core.modules.contact.presentation.viewmodel.ContactViewModel
import com.clo.accloss.salesman.presentation.screens.SalesmanDetailScreen

class ContactScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ContactViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        state.sellers.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                ErrorScreen(it)
            },
            onSuccess = { sellers ->
                ContactsContent(
                    salesmen = sellers,
                    isRefreshing = state.reload == true,
                    onRefresh = {
                        viewModel.onRefresh()
                    },
                    onSelect = { seller ->
                        navigator.parent?.parent?.push(SalesmanDetailScreen(seller))
                    }
                )
            },
        )
    }
}

package com.clo.accloss.salesman.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.DefaultBackArrow
import com.clo.accloss.core.presentation.components.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.salesman.presentation.components.SalesmanContent
import com.clo.accloss.salesman.presentation.viewmodel.SalesmanViewModel
import com.clo.accloss.statistic.presentation.screen.StatisticDetailsScreen
import org.koin.core.parameter.parametersOf

data class SalesmanScreen(
    val id: String
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SalesmanViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsStateWithLifecycle()
        var code by rememberSaveable {
            mutableStateOf("")
        }

        DefaultLayoutComponent(
            topBar = {
                DefaultTopBar(
                    navigationIcon = {
                        DefaultBackArrow {
                            navigator.pop()
                        }
                    },
                    actions = {
                        Row(
                            modifier = Modifier.clickable {
                                navigator.push(StatisticDetailsScreen(code))
                            },
                            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomText(
                                text = stringResource(id = R.string.statistics),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_right_24px),
                                contentDescription = "Statistics"
                            )
                        }
                    }
                )
            },
            state = state.salesman
        ) { salesman ->
            code = salesman.vendedor
            SalesmanContent(
                salesman = salesman
            )
        }
    }
}

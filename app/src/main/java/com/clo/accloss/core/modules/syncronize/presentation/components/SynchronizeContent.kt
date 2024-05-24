package com.clo.accloss.core.modules.syncronize.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.BuildConfig
import com.clo.accloss.R
import com.clo.accloss.core.modules.syncronize.presentation.state.SynchronizeState
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.DefaultBackArrow
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.core.presentation.components.ErrorComponent
import com.clo.accloss.core.presentation.components.LoadingComponent

@Composable
fun SynchronizeContent(
    state: SynchronizeState,
    onSync: () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = {
                    CustomText(
                        text = stringResource(id = R.string.synchronize),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                },
                navigationIcon = {
                    DefaultBackArrow {
                        navigator.pop()
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cloud_sync_24px),
                            contentDescription = "Synchronize"
                        )
                        CustomText(
                            text = stringResource(R.string.synchronize_all_your_data)
                        )
                    }
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            if (!state.synchronize.isLoading()) {
                                onSync()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync_24px),
                            contentDescription = "Synchronize"
                        )
                    }
                }
            }

            state.synchronize.DisplayResult(
                onLoading = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomText(text = stringResource(R.string.loading))
                        LoadingComponent(
                            modifier = Modifier.size(20.dp),
                            strokeWith = 2.dp
                        )
                    }
                },
                onError = {
                    ErrorComponent(it)
                },
                onSuccess = { sync ->
                    AnimatedContent(
                        targetState = sync,
                        label = "Synchronization"
                    ) { synchronize ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (BuildConfig.DEBUG) {
                                CustomText(
                                    text = "${
                                        stringResource(
                                            id = R.string.managements
                                        )
                                    }: ${synchronize.managements.getSuccessData().size}"
                                )
                                CustomText(
                                    text = "${
                                        stringResource(
                                            id = R.string.salesmen
                                        )
                                    }: ${synchronize.salesmen.getSuccessData().size}"
                                )
                                CustomText(
                                    text = "${
                                        stringResource(
                                            id = R.string.statistics
                                        )
                                    }: ${synchronize.salesmen.getSuccessData().size}"
                                )
                                CustomText(
                                    text = "${
                                        stringResource(
                                            id = R.string.customers
                                        )
                                    }: ${synchronize.customers.getSuccessData().size}"
                                )
                                CustomText(
                                    text = "${
                                        stringResource(
                                            id = R.string.orders
                                        )
                                    }: ${synchronize.orders.getSuccessData().size}"
                                )
                                CustomText(
                                    text = "${stringResource(id = R.string.bills)}: ${
                                        synchronize.bills.getSuccessData().size
                                    }"
                                )
                                CustomText(
                                    text = "${
                                        stringResource(
                                            id = R.string.products
                                        )
                                    }: ${synchronize.products.getSuccessData().size}"
                                )
                            }
                            synchronize.sync.DisplayResult(
                                onLoading = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            10.dp,
                                            Alignment.CenterHorizontally
                                        ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CustomText(text = stringResource(R.string.loading))
                                        LoadingComponent(
                                            modifier = Modifier.size(20.dp),
                                            strokeWith = 2.dp
                                        )
                                    }
                                },
                                onError = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            10.dp,
                                            Alignment.CenterHorizontally
                                        ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CustomText(
                                            text = "There was an error while synchronizing",
                                            color = MaterialTheme.colorScheme.error
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_cancel_24px),
                                            tint = MaterialTheme.colorScheme.error,
                                            contentDescription = "Correct"
                                        )
                                    }
                                },
                                onSuccess = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            10.dp,
                                            Alignment.CenterHorizontally
                                        ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CustomText(
                                            text = stringResource(R.string.sync_successful),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_check_circle_24px),
                                            tint = MaterialTheme.colorScheme.primary,
                                            contentDescription = "Correct"
                                        )
                                    }
                                },
                            )
                        }
                    }
                },
            )
        }
    }
}

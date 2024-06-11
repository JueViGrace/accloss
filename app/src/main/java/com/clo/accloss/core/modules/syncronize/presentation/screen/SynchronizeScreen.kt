package com.clo.accloss.core.modules.syncronize.presentation.screen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.clo.accloss.BuildConfig
import com.clo.accloss.R
import com.clo.accloss.core.modules.syncronize.presentation.model.Synchronize
import com.clo.accloss.core.modules.syncronize.presentation.state.SynchronizeState
import com.clo.accloss.core.modules.syncronize.presentation.viewmodel.SynchronizeViewModel
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.ErrorComponents.ErrorComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.LoadingComponents.LoadingComponent

object SynchronizeScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<SynchronizeViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        SynchronizeContent(
            state = state,
            onSync = {
                viewModel.synchronize()
            }
        )
    }

    @Composable
    fun SynchronizeContent(
        state: SynchronizeState,
        onSync: () -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow

        DefaultLayoutComponent(
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
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
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
                    onError = { message ->
                        ErrorComponent(message)
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
                                DebugDisplay(synchronize = synchronize)

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
                                                contentDescription = "Error"
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

    @Composable
    private fun DebugDisplay(
        synchronize: Synchronize
    ) {
        if (BuildConfig.DEBUG) {
            synchronize.managements.DisplayResult(
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
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel_24px),
                            tint = MaterialTheme.colorScheme.error,
                            contentDescription = "Error"
                        )
                    }
                },
                onSuccess = {
                    CustomText(
                        text = "${
                            stringResource(
                                id = R.string.managements
                            )
                        }: ${it.size}"
                    )
                },
            )

            synchronize.salesmen.DisplayResult(
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
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel_24px),
                            tint = MaterialTheme.colorScheme.error,
                            contentDescription = "Error"
                        )
                    }
                },
                onSuccess = {
                    CustomText(
                        text = "${
                            stringResource(
                                id = R.string.salesmen
                            )
                        }: ${it.size}"
                    )
                },
            )

            synchronize.statistics.DisplayResult(
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
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel_24px),
                            tint = MaterialTheme.colorScheme.error,
                            contentDescription = "Error"
                        )
                    }
                },
                onSuccess = {
                    CustomText(
                        text = "${
                            stringResource(
                                id = R.string.statistics
                            )
                        }: ${it.size}"
                    )
                },
            )

            synchronize.customers.DisplayResult(
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
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel_24px),
                            tint = MaterialTheme.colorScheme.error,
                            contentDescription = "Error"
                        )
                    }
                },
                onSuccess = {
                    CustomText(
                        text = "${
                            stringResource(
                                id = R.string.customers
                            )
                        }: ${it.size}"
                    )
                },
            )

            synchronize.orders.DisplayResult(
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
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel_24px),
                            tint = MaterialTheme.colorScheme.error,
                            contentDescription = "Error"
                        )
                    }
                },
                onSuccess = {
                    CustomText(
                        text = "${
                            stringResource(
                                id = R.string.orders
                            )
                        }: ${it.size}"
                    )
                },
            )

            synchronize.bills.DisplayResult(
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
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel_24px),
                            tint = MaterialTheme.colorScheme.error,
                            contentDescription = "Error"
                        )
                    }
                },
                onSuccess = {
                    CustomText(
                        text = "${
                            stringResource(
                                id = R.string.bills
                            )
                        }: ${it.size}"
                    )
                },
            )

            synchronize.products.DisplayResult(
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
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel_24px),
                            tint = MaterialTheme.colorScheme.error,
                            contentDescription = "Error"
                        )
                    }
                },
                onSuccess = {
                    CustomText(
                        text = "${
                            stringResource(
                                id = R.string.products
                            )
                        }: ${it.size}"
                    )
                },
            )
        }
    }
}

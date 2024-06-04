package com.clo.accloss.core.modules.contact.presentation.navigation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.modules.contact.presentation.viewmodel.ContactDetailsViewModel
import com.clo.accloss.core.presentation.components.DisplayComponents.CardLabel
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomClickableCard
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.DisplayComponents.LetterIcon
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBarActions
import com.clo.accloss.core.presentation.components.TopBarActions
import com.clo.accloss.customer.presentation.screens.CustomersScreen
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.statistic.presentation.screen.StatisticDetailsScreen
import org.koin.core.parameter.parametersOf

data class ContactDetailsScreen(
    val id: String
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ContactDetailsViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsStateWithLifecycle()

        DefaultLayoutComponent(
            topBar = {
                DefaultTopBar(
                    navigationIcon = {
                        DefaultBackArrow {
                            navigator.pop()
                        }
                    },
                    actions = {
                        DefaultTopBarActions(
                            onMenuClick = { action ->
                                when (action) {
                                    is TopBarActions.Statistics -> {
                                        navigator.push(StatisticDetailsScreen(id))
                                    }

                                    is TopBarActions.Customers -> {
                                        navigator.push(CustomersScreen(id))
                                    }

                                    else -> {}
                                }
                            },
                            items = listOf(TopBarActions.Statistics, TopBarActions.Customers)
                        )
                    }
                )
            },
            state = state.salesman
        ) { salesman ->
            SalesmanContent(
                salesman = salesman
            )
        }
    }

    @Composable
    private fun SalesmanContent(
        modifier: Modifier = Modifier,
        salesman: Salesman
    ) {
        var visible by rememberSaveable {
            mutableStateOf(false)
        }

        val context = LocalContext.current

        val startEmail = remember {
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${salesman.email}")
            }
        }

        val startMobile = remember {
            Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${salesman.telefonoMovil}")
            }
        }

        val startPhone = remember {
            Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${salesman.telefonos}")
            }
        }

        val startSms = remember {
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:${salesman.telefonoMovil}")
            }
        }

        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LetterIcon(
                    modifier = Modifier.defaultMinSize(minWidth = 100.dp, minHeight = 100.dp),
                    letter = salesman.nombre.first().toString(),
                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                    fontWeight = MaterialTheme.typography.displayMedium.fontWeight
                )

                CustomText(
                    text = salesman.nombre,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    softWrap = true,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )

                if (
                    salesman.telefonoMovil.isNotEmpty() ||
                    salesman.email.isNotEmpty() ||
                    salesman.telefonos.isNotEmpty()
                ) {
                    CustomClickableCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                10.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (salesman.telefonoMovil.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        CustomText(
                                            text = salesman.telefonoMovil,
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                        )
                                        CustomText(
                                            text = stringResource(R.string.mobile),
                                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = {
                                                context.startActivity(startMobile)
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_call_24px),
                                                contentDescription = "Call"
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                context.startActivity(startSms)
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_sms_24px),
                                                contentDescription = "Message"
                                            )
                                        }
                                    }
                                }
                            }

                            if (salesman.telefonos.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        CustomText(
                                            text = salesman.telefonos,
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                        )
                                        CustomText(
                                            text = stringResource(R.string.phone),
                                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = {
                                                context.startActivity(startPhone)
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_call_24px),
                                                contentDescription = "Call"
                                            )
                                        }
                                    }
                                }
                            }

                            if (salesman.email.isNotEmpty()) {
                                AnimatedVisibility(visible = visible) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            CustomText(
                                                text = salesman.email,
                                                softWrap = true,
                                                maxLines = 2
                                            )
                                            CustomText(
                                                text = stringResource(R.string.email),
                                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                                fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.6f
                                                )
                                            )
                                        }

                                        Row {
                                            IconButton(
                                                onClick = {
                                                    context.startActivity(startEmail)
                                                }
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_mail_24px),
                                                    contentDescription = "Message"
                                                )
                                            }
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        5.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CustomText(
                                        text = if (!visible) {
                                            stringResource(id = R.string.see_more)
                                        } else {
                                            stringResource(id = R.string.see_less)
                                        },
                                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                        fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                                    )
                                    Icon(
                                        modifier = Modifier.clickable {
                                            visible = !visible
                                        },
                                        painter = if (!visible) {
                                            painterResource(id = R.drawable.ic_expand_more_24px)
                                        } else {
                                            painterResource(id = R.drawable.ic_expand_less_24px)
                                        },
                                        contentDescription = "More"
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (salesman.vendedor.isNotEmpty()) {
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = R.string.salesman,
                                value = salesman.vendedor
                            )
                        }

                        if (salesman.ultSinc.isNotEmpty()) {
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = R.string.last_sync,
                                value = salesman.ultSinc,
                                valueFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                valueFontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            )
                        }

                        if (salesman.version.isNotEmpty()) {
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = R.string.app_version,
                                value = salesman.version,
                                valueFontSize = MaterialTheme.typography.titleMedium.fontSize,
                                valueFontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(
                            10.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (salesman.supervpor.isNotEmpty()) {
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = R.string.coordinator,
                                value = salesman.supervpor
                            )
                        }

                        if (salesman.sector.isNotEmpty()) {
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = R.string.zone,
                                value = salesman.sector.lowercase().capitalize(Locale.current)
                            )
                        }

                        if (salesman.subsector.isNotEmpty()) {
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = R.string.sub_zone,
                                value = salesman.subsector
                            )
                        }
                    }
                }
            }
        }
    }
}

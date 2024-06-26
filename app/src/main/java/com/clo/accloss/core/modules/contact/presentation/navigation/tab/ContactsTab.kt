package com.clo.accloss.core.modules.contact.presentation.navigation.tab

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.clo.accloss.R
import com.clo.accloss.core.modules.contact.presentation.navigation.screens.ContactDetailsScreen
import com.clo.accloss.core.modules.contact.presentation.viewmodel.ContactViewModel
import com.clo.accloss.core.modules.home.presentation.navigation.routes.HomeTabs
import com.clo.accloss.core.presentation.components.DisplayComponents
import com.clo.accloss.core.presentation.components.LayoutComponents
import com.clo.accloss.core.presentation.components.ListComponents
import com.clo.accloss.core.presentation.components.TextFieldComponents
import com.clo.accloss.core.presentation.components.TopBarActions
import com.clo.accloss.salesman.domain.model.Salesman
import kotlinx.coroutines.launch
import kotlin.math.abs

object ContactsTab : Tab {
    private fun readResolve(): Any = ContactsTab

    override val key: ScreenKey = uniqueScreenKey + super.key

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = HomeTabs.Contact.icon)
            val title = stringResource(id = HomeTabs.Contact.title)
            return remember {
                TabOptions(
                    index = HomeTabs.Contact.index,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ContactViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val focus = LocalFocusManager.current

        LayoutComponents.DefaultLayoutComponent(
            topBar = {
                AnimatedVisibility(
                    visible = !state.searchBarVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LayoutComponents.DefaultTopBar(
                        title = {
                            DisplayComponents.CustomText(
                                text = stringResource(id = R.string.salesmen),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                            )
                        },
                        actions = {
                            LayoutComponents.DefaultTopBarActions(
                                onMenuClick = { action ->
                                    when {
                                        action is TopBarActions.Search -> {
                                            viewModel.toggleVisibility(true)
                                        }
                                    }
                                },
                                items = listOf(TopBarActions.Search)
                            )
                        }
                    )
                }

                AnimatedVisibility(
                    visible = state.searchBarVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LayoutComponents.DefaultTopBar(
                        title = {
                            TextFieldComponents.SearchBarComponent(
                                query = state.searchText,
                                onQueryChange = viewModel::onSearchTextChange,
                                onSearch = {
                                    focus.clearFocus()
                                    viewModel.onSearchTextChange(it)
                                }
                            )
                        },
                        navigationIcon = {
                            LayoutComponents.DefaultBackArrow {
                                viewModel.onSearchTextChange("")
                                viewModel.toggleVisibility(false)
                            }
                        },
                    )
                }
            },
            state = state.salesmen
        ) { salesmen ->
            ContactsContent(
                salesmen = salesmen,
                onSelect = { salesman ->
                    navigator.parent?.parent?.push(ContactDetailsScreen(salesman))
                }
            )
        }
    }

    @Composable
    private fun ContactsContent(
        salesmen: List<Salesman>,
        onSelect: ((String) -> Unit)? = null,
    ) {
        val headers = remember {
            salesmen.map { it.nombre.first().uppercase() }.toSet().toList()
        }

        val listState = rememberLazyListState()

        Row {
            ListComponents.CustomLazyColumn(
                modifier = Modifier.weight(1f),
                lazyListState = listState,
                items = salesmen,
                grouped = salesmen.groupBy { it.nombre.first().toString() },
                stickyHeader = { char ->
                    char?.let {
                        ListComponents.ListStickyHeader(
                            textModifier = Modifier.padding(horizontal = 14.dp),
                            text = it
                        )
                    }
                },
                content = { state ->
                    AnimatedContent(
                        targetState = state,
                        label = stringResource(R.string.salesman)
                    ) { salesman ->
                        ContactsComponent(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            salesman = salesman,
                            onSelect = { onSelect?.invoke(it) }
                        )
                    }
                },
                footer = {
                    ListComponents.ListFooter(text = stringResource(R.string.end_of_list))
                },
            )

            val offsets = remember { mutableStateMapOf<Int, Float>() }
            var selectedHeaderIndex by remember { mutableIntStateOf(0) }
            val scope = rememberCoroutineScope()

            fun updateSelectedIndexIfNeeded(offset: Float) {
                val index = offsets
                    .mapValues { abs(it.value - offset) }
                    .entries
                    .minByOrNull { it.value }
                    ?.key ?: return
                if (selectedHeaderIndex == index) return
                selectedHeaderIndex = index
                val selectedItemIndex = salesmen.indexOfFirst {
                    it.nombre.first().uppercase() == headers[selectedHeaderIndex]
                }
                scope.launch {
                    listState.scrollToItem(
                        index = selectedItemIndex,
                        scrollOffset = offset.toInt()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            updateSelectedIndexIfNeeded(it.y)
                        }
                    }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, _ ->
                            updateSelectedIndexIfNeeded(change.position.y)
                        }
                    }
                    .padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                headers.forEachIndexed { i, header ->
                    DisplayComponents.CustomText(
                        text = header,
                        modifier = Modifier.onGloballyPositioned {
                            offsets[i] = it.boundsInParent().center.y
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun ContactsComponent(
        modifier: Modifier = Modifier,
        salesman: Salesman,
        onSelect: (String) -> Unit
    ) {
        val letter: String by remember {
            mutableStateOf(salesman.nombre.first().uppercase())
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onSelect(salesman.vendedor)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
        ) {
            DisplayComponents.LetterIcon(letter = letter)

            Column {
                DisplayComponents.CustomText(
                    text = salesman.nombre,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true
                )

                DisplayComponents.CustomText(
                    text = salesman.vendedor,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}

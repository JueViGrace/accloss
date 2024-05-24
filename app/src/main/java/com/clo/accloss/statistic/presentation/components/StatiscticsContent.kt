package com.clo.accloss.statistic.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.statistic.domain.model.Statistic

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsContent(
    salesmen: List<Statistic>,
    onSelect: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(5.dp)
    ) {
        salesmen.groupBy {
            it.codcoord
        }.forEach { (initial, salesman) ->
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = initial,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                }
            }

            items(salesman) { statistics ->
                StatisticsListContent(
                    statistic = statistics,
                    onSelect = onSelect
                )
            }
        }

        item {
            ListFooter(text = stringResource(id = R.string.end_of_list))
        }
    }
}

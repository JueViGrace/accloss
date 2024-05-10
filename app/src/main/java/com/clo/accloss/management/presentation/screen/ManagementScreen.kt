package com.clo.accloss.management.presentation.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.core.presentation.components.CustomText

class ManagementScreen : Screen {
    @Composable
    override fun Content() {
        CustomText(text = "Managements")
    }
}

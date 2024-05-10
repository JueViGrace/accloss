package com.clo.accloss.core.presentation.profile.presentation.components

import com.clo.accloss.R

sealed class ProfileMenu(
    val name: Int,
    val icon: Int
) {
    data object LogOut : ProfileMenu(
        name = R.string.log_out,
        icon = R.drawable.ic_logout_24px
    )
    data object Statistics : ProfileMenu(
        name = R.string.statistics,
        icon = R.drawable.ic_analytics_24px
    )
}

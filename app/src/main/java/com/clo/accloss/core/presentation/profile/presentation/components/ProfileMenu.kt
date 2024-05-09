package com.clo.accloss.core.presentation.profile.presentation.components

import com.clo.accloss.R

sealed class ProfileMenu(
    val name: String,
    val icon: Int
) {
    data object LogOut : ProfileMenu(
        name = "Log Out",
        icon = R.drawable.ic_logout_24px
    )
    data object Promotions : ProfileMenu(
        name = "Promociones",
        icon = R.drawable.ic_shopping_bag_24px
    )
    data object Statistics : ProfileMenu(
        name = "Estadísticas",
        icon = R.drawable.ic_analytics_24px
    )
}

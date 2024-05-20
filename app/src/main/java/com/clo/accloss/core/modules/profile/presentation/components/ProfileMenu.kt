package com.clo.accloss.core.modules.profile.presentation.components

import com.clo.accloss.R

sealed class ProfileMenu(
    val name: Int,
    val icon: Int
) {
    data object LogOut : ProfileMenu(
        name = R.string.log_out,
        icon = R.drawable.ic_logout_24px
    )
    data object Synchronize : ProfileMenu(
        name = R.string.synchronize,
        icon = R.drawable.ic_cloud_sync_24px
    )
}

package com.clo.accloss.user.data.source

import com.clo.accloss.user.data.local.UserLocal
import com.clo.accloss.user.data.remote.source.UserRemote

class UserDataSourceImpl(
    override val userRemote: UserRemote,
    override val userLocal: UserLocal,
) : UserDataSource

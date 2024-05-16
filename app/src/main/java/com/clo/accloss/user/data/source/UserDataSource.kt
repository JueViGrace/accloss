package com.clo.accloss.user.data.source

import com.clo.accloss.user.data.local.UserLocal
import com.clo.accloss.user.data.remote.source.UserRemote

interface UserDataSource {
    val userRemote: UserRemote
    val userLocal: UserLocal
}

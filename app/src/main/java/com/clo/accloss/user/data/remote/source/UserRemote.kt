package com.clo.accloss.user.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.user.data.remote.model.UserResponse

interface UserRemote {
    suspend fun getSafeUser(
        baseUrl: String,
        username: String,
        password: String
    ): ApiOperation<UserResponse>
}

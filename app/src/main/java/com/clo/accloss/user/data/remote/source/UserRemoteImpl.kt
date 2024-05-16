package com.clo.accloss.user.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.user.data.remote.model.UserResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserRemoteImpl(
    private val ktorClient: KtorClient
) : UserRemote {
    override suspend fun getSafeUser(
        baseUrl: String,
        username: String,
        password: String
    ): ApiOperation<UserResponse> = ktorClient.safeApiCall {
        val url =
            "/webservice/c_validar_usuario.php?username=$username&password=$password"
        ktorClient
            .client(baseUrl = baseUrl)
            .get(
                urlString = url
            )
            .body<UserResponse>()
    }
}

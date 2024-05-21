package com.clo.accloss.user.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.core.modules.syncronize.presentation.model.Estado
import com.clo.accloss.core.modules.syncronize.presentation.model.SyncBody
import com.clo.accloss.user.data.remote.model.UserResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.reflect.TypeInfo

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

    override suspend fun synchronize(
        baseUrl: String,
        syncBody: SyncBody
    ): ApiOperation<Estado> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .post(
                urlString = "/sincroonizacion",
                block = {
                    contentType(ContentType.Application.Json)
                    setBody(syncBody)
                }
            )
            .body<Estado>()
    }
}

package com.clo.accloss.user.data.remote.source

import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.user.data.remote.model.UserResponse

class UserRemoteDataSource(
    private val ktorClient: KtorClient
) {
    fun getSafeUser(
        baseUrl: String? = null,
        username: String,
        password: String,
        agencia: String = "mcbo"
    ): Flow<ApiOperation<UserResponse>> = ktorClient.safeApiCall {
        val url =
            "/webservice/c_validar_usuario.php?username=$username&password=$password"
        ktorClient
            .client(baseUrl)
            .get(
                urlString = url
            )
            .body<UserResponse>()
    }
}

package com.clo.accloss.core.network

import com.clo.accloss.core.common.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class KtorClient {
    fun client(baseUrl: String? = null): HttpClient = HttpClient {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(DefaultRequest) {
            url(urlString = baseUrl ?: BASE_URL)
        }
    }

    inline fun <T> safeApiCall(crossinline apiCall: suspend () -> T): Flow<ApiOperation<T>> = flow {
        emit(ApiOperation.Loading())

        try {
            emit(ApiOperation.Success(data = apiCall()))
        } catch (e: Exception) {
            emit(ApiOperation.Failure(error = e))
        }
    }.flowOn(Dispatchers.Default)
}

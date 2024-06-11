package com.clo.accloss.core.data.network

import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.BASE_URL
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
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

/*    inline fun <T> safeApiCall(crossinline apiCall: suspend () -> T): Flow<ApiOperation<T>> = flow {
        emit(ApiOperation.Loading())

        try {
            emit(ApiOperation.Success(data = apiCall()))
        } catch (e: Exception) {
            emit(ApiOperation.Failure(error = e))
        }
    }.flowOn(Dispatchers.Default)*/

    // TODO: MESSAGE AS R
    inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(
                error = when (e) {
                    is NoTransformationFoundException -> {
                        R.string.invalid_response_body_try_again_later
                    }
                    is SocketTimeoutException -> {
                        R.string.server_took_too_long_to_answer
                    }
                    else -> {
                        SERVER_ERROR
                    }
                }
            )
        }
    }
}

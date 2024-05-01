package com.clo.accloss.core.network

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val error: Exception) : ApiOperation<T>
    data class Loading<T>(val data: T? = null) : ApiOperation<T>
}

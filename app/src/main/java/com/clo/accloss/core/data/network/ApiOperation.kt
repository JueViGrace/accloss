package com.clo.accloss.core.data.network

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val error: Int) : ApiOperation<T>

    fun <R> mapSuccess(transform: (T) -> R): ApiOperation<R> {
        return when(this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(error)
        }
    }
}

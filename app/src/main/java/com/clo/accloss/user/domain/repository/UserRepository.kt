package com.clo.accloss.user.domain.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.core.presentation.auth.login.domain.model.Login
import com.clo.accloss.user.data.local.UserLocalDataSource
import com.clo.accloss.user.data.remote.source.UserRemoteDataSource
import com.clo.accloss.user.domain.mappers.toDatabase
import com.clo.accloss.user.domain.mappers.toDomain
import com.clo.accloss.user.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
    fun getRemoteUser(
        login: Login
    ): Flow<RequestState<User>> = flow {
        emit(RequestState.Loading)

        val apiOperation = userRemoteDataSource
            .getSafeUser(
                baseUrl = login.baseUrl,
                username = login.username,
                password = login.password
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error
                    )
                )
            }
            is ApiOperation.Success -> {
                emit(
                    RequestState.Success(
                        data = apiOperation.data.toDomain()
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUsers(): Flow<RequestState<User>> = flow {
        emit(RequestState.Loading)

        userLocalDataSource.getUsers()
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { list ->
                if (list.isNotEmpty()) {
                    emit(
                        RequestState.Success(
                            data = list.first().toDomain()
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = "No users"
                        )
                    )
                }
            }
    }.flowOn(Dispatchers.IO)

    suspend fun getUser(codigo: String, empresa: String): Flow<RequestState<User>> = flow {
        emit(RequestState.Loading)

        userLocalDataSource.getUser(codigo = codigo, empresa = empresa)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { userEntity ->
                emit(RequestState.Success(data = userEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    suspend fun addUser(user: User) = userLocalDataSource.addUser(user = user.toDatabase())
}

package com.clo.accloss.user.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.login.domain.model.Login
import com.clo.accloss.user.data.source.UserDataSource
import com.clo.accloss.user.domain.mappers.toDatabase
import com.clo.accloss.user.domain.mappers.toDomain
import com.clo.accloss.user.domain.model.User
import com.clo.accloss.user.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    override val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getRemoteUser(
        login: Login
    ): RequestState<User> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = userDataSource.userRemote
                    .getSafeUser(
                        baseUrl = login.baseUrl,
                        username = login.username,
                        password = login.password
                    )
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    RequestState.Success(
                        data = apiOperation.data.toDomain()
                    )
                }
            }
        }
    }

    override val getUsers: Flow<RequestState<List<User>>> = flow {
        emit(RequestState.Loading)

        userDataSource.userLocal.getUsers()
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("USER REPOSITORY: getUsers")
            }
            .collect { list ->
                if (list.isNotEmpty()) {
                    emit(
                        RequestState.Success(
                            data = list.map { userEntity ->
                                userEntity.toDomain()
                            }
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

    override suspend fun getUser(code: String, company: String): Flow<RequestState<User>> = flow {
        emit(RequestState.Loading)

        userDataSource.userLocal.getUser(code = code, company = company)
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("PRODUCT REPOSITORY: getProducts")
            }
            .collect { userEntity ->
                emit(RequestState.Success(data = userEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun addUser(user: User) =
        withContext(Dispatchers.IO) {
            userDataSource.userLocal.addUser(user = user.toDatabase())
        }
}

package com.clo.accloss.user.data.repository

import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.modules.syncronize.presentation.model.Estado
import com.clo.accloss.core.modules.syncronize.presentation.model.SyncBody
import com.clo.accloss.core.modules.syncronize.presentation.model.Synchronization
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.login.domain.model.Login
import com.clo.accloss.user.data.source.UserDataSource
import com.clo.accloss.user.domain.mappers.toDatabase
import com.clo.accloss.user.domain.mappers.toDomain
import com.clo.accloss.user.domain.model.User
import com.clo.accloss.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultUserRepository(
    override val userDataSource: UserDataSource,
    override val coroutineContext: CoroutineContext
) : UserRepository {
    override suspend fun getRemoteUser(
        login: Login
    ): RequestState<User> {
        return withContext(coroutineContext) {
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
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("USER REPOSITORY: getUsers")
            }
            .collect { list ->
                emit(
                    RequestState.Success(
                        data = list.map { userEntity ->
                            userEntity.toDomain()
                        }
                    )
                )
            }
    }.flowOn(coroutineContext)

    override suspend fun getUser(code: String, company: String): Flow<RequestState<User>> = flow {
        emit(RequestState.Loading)

        userDataSource.userLocal.getUser(code = code, company = company)
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("PRODUCT REPOSITORY: getProducts")
            }
            .collect { userEntity ->
                if (userEntity != null) {
                    emit(
                        RequestState.Success(
                            data = userEntity.toDomain()
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = R.string.this_user_doesn_t_exists
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override suspend fun addUser(user: User) {
        withContext(coroutineContext) {
            userDataSource.userLocal.addUser(user = user.toDatabase())
        }
    }

    override suspend fun updateSyncDate(lastSync: String, company: String) {
        withContext(coroutineContext) {
            userDataSource.userLocal.updateSyncDate(lastSync, company)
        }
    }

    override suspend fun synchronize(
        baseUrl: String,
        sync: Synchronization
    ): RequestState<Estado> {
        return withContext(coroutineContext) {
            when (
                val apiOperation = userDataSource.userRemote.synchronize(
                    baseUrl = baseUrl,
                    syncBody = SyncBody(sync)
                )
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(message = apiOperation.error)
                }

                is ApiOperation.Success -> {
                    val data = apiOperation.data

                    if (data.response.status != 200) {
                        RequestState.Error(
                            message = R.string.you_have_an_outdated_version_please_update
                        )
                    } else {
                        RequestState.Success(
                            data = data
                        )
                    }
                }
            }
        }
    }

    override suspend fun deleteUser(user: String, company: String) {
        withContext(coroutineContext) {
            userDataSource.userLocal.deleteUser(user = user, company = company)
        }
    }
}

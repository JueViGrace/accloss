package com.clo.accloss.user.domain.repository

import com.clo.accloss.core.modules.syncronize.presentation.model.Estado
import com.clo.accloss.core.modules.syncronize.presentation.model.Synchronization
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.login.domain.model.Login
import com.clo.accloss.user.data.source.UserDataSource
import com.clo.accloss.user.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface UserRepository {
    val userDataSource: UserDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteUser(login: Login): RequestState<User>

    val getUsers: Flow<RequestState<List<User>>>

    suspend fun getUser(code: String, company: String): Flow<RequestState<User>>

    suspend fun addUser(user: User)

    suspend fun updateSyncDate(lastSync: String, company: String)

    suspend fun synchronize(
        baseUrl: String,
        sync: Synchronization
    ): RequestState<Estado>

    suspend fun deleteUser(user: String, company: String)
}

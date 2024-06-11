package com.clo.accloss.user.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.User as UserEntity

interface UserLocal {
    val scope: CoroutineScope

    suspend fun getUsers(): Flow<List<UserEntity>>

    suspend fun getUser(code: String, company: String): Flow<UserEntity?>

    suspend fun addUser(user: UserEntity)

    suspend fun updateSyncDate(lastSync: String, company: String)

    suspend fun deleteUser(user: String, company: String)
}

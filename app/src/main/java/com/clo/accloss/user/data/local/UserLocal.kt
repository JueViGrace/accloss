package com.clo.accloss.user.data.local

import com.clo.accloss.User
import kotlinx.coroutines.flow.Flow

interface UserLocal {
    suspend fun getUsers(): Flow<List<User>>

    suspend fun getUser(code: String, company: String): Flow<User>

    suspend fun addUser(user: User)
}

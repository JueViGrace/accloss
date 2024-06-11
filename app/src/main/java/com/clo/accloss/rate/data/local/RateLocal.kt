package com.clo.accloss.rate.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Tasas as RateEntity

interface RateLocal {
    val scope: CoroutineScope

    suspend fun getRate(company: String): Flow<RateEntity?>

    suspend fun addRate(rate: RateEntity)

    suspend fun deleteRate(company: String)
}

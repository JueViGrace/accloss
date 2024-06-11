package com.clo.accloss.configuration.data.local

import kotlinx.coroutines.CoroutineScope
import com.clo.accloss.Configuration as ConfigurationEntity

interface ConfigurationLocal {
    val scope: CoroutineScope

    suspend fun getConfigNum(key: String, company: String): Double

    suspend fun getConfigBool(key: String, company: String): Double?

    suspend fun getConfigText(key: String, company: String): String

    suspend fun getConfigDate(key: String, company: String): String

    suspend fun addConfiguration(configurations: List<ConfigurationEntity>)

    suspend fun deleteConfiguration(company: String)
}

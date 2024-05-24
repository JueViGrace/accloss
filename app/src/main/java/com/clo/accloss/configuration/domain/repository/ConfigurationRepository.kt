package com.clo.accloss.configuration.domain.repository

import com.clo.accloss.configuration.data.source.ConfigurationDataSource
import com.clo.accloss.configuration.domain.model.Configuration
import com.clo.accloss.core.domain.state.RequestState

interface ConfigurationRepository {
    val configurationDataSource: ConfigurationDataSource

    suspend fun getRemoteConfiguration(
        baseUrl: String,
        salesman: String,
        company: String
    ): RequestState<List<Configuration>>

    suspend fun getConfigNum(key: String, company: String): RequestState<Double>

    suspend fun getConfigBool(key: String, company: String): RequestState<Boolean>

    suspend fun getConfigText(key: String, company: String): RequestState<String>

    suspend fun getConfigDate(key: String, company: String): RequestState<String>

    suspend fun addConfiguration(configurations: List<Configuration>)
}

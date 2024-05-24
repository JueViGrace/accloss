package com.clo.accloss.configuration.domain.repository

import com.clo.accloss.configuration.data.source.ConfigurationDataSource
import com.clo.accloss.configuration.domain.mappers.toDatabase
import com.clo.accloss.configuration.domain.mappers.toDomain
import com.clo.accloss.configuration.domain.model.Configuration
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.domain.state.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultConfigurationRepository(
    override val configurationDataSource: ConfigurationDataSource
) : ConfigurationRepository {
    override suspend fun getRemoteConfiguration(
        baseUrl: String,
        salesman: String,
        company: String,
    ): RequestState<List<Configuration>> {
        return withContext(Dispatchers.IO) {
            val apiOperation = configurationDataSource
                .configurationRemote
                .getRemoteConfiguration(
                    baseUrl = baseUrl,
                    salesman = salesman
                )

            when (apiOperation) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }
                is ApiOperation.Success -> {
                    val data = apiOperation.data.config.map { configurationItem ->
                        configurationItem
                            .toDomain()
                            .copy(
                                empresa = company
                            )
                    }

                    addConfiguration(data)

                    RequestState.Success(data = data)
                }
            }
        }
    }

    override suspend fun getConfigNum(key: String, company: String): RequestState<Double> {
        return try {
            RequestState.Success(
                data = configurationDataSource
                    .configurationLocal
                    .getConfigNum(
                        key = key,
                        company = company
                    )
            )
        } catch (e: Exception) {
            e.log("CONFIGURATION REPOSITORY: getConfigNum")
            RequestState.Error(message = DB_ERROR_MESSAGE)
        }
    }

    override suspend fun getConfigBool(key: String, company: String): RequestState<Boolean> {
        return try {
            val config = configurationDataSource
                .configurationLocal
                .getConfigBool(
                    key = key,
                    company = company
                )

            RequestState.Success(
                data = config == 1.0
            )
        } catch (e: Exception) {
            e.log("CONFIGURATION REPOSITORY: getConfigNum")
            RequestState.Error(message = DB_ERROR_MESSAGE)
        }
    }

    override suspend fun getConfigText(key: String, company: String): RequestState<String> {
        return try {
            RequestState.Success(
                data = configurationDataSource
                    .configurationLocal
                    .getConfigText(
                        key = key,
                        company = company
                    )
            )
        } catch (e: Exception) {
            e.log("CONFIGURATION REPOSITORY: getConfigNum")
            RequestState.Error(message = DB_ERROR_MESSAGE)
        }
    }

    override suspend fun getConfigDate(key: String, company: String): RequestState<String> {
        return try {
            RequestState.Success(
                data = configurationDataSource
                    .configurationLocal
                    .getConfigDate(
                        key = key,
                        company = company
                    )
            )
        } catch (e: Exception) {
            e.log("CONFIGURATION REPOSITORY: getConfigNum")
            RequestState.Error(message = DB_ERROR_MESSAGE)
        }
    }

    override suspend fun addConfiguration(configurations: List<Configuration>) {
        withContext(Dispatchers.IO) {
            configurationDataSource.configurationLocal.addConfiguration(
                configurations.map { configuration ->
                    configuration.toDatabase()
                }
            )
        }
    }
}

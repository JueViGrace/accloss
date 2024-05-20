package com.clo.accloss.rate.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.rate.data.source.RateDataSource
import com.clo.accloss.rate.domain.mappers.toDatabase
import com.clo.accloss.rate.domain.mappers.toDomain
import com.clo.accloss.rate.domain.model.Rate
import com.clo.accloss.rate.domain.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class RateRepositoryImpl(
    override val rateDataSource: RateDataSource
) : RateRepository {
    override suspend fun getRemoteRate(
        baseUrl: String,
        company: String
    ): RequestState<Rate> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = rateDataSource.rateRemote
                    .getSafeRates(baseUrl = baseUrl)
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    val data = apiOperation.data
                        .toDomain()
                        .copy(empresa = company)

                    addRate(data)

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override suspend fun getRate(
        company: String
    ): Flow<RequestState<Rate>> = flow<RequestState<Rate>> {
        emit(RequestState.Loading)

        rateDataSource.rateLocal.getRate(company)
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("RATES REPOSITORY: getRate")
            }
            .collect { rateEntity ->
                emit(
                    RequestState.Success(
                        data = rateEntity.toDomain()
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun addRate(rate: Rate) =
        rateDataSource.rateLocal.addRate(rate.toDatabase())

    override suspend fun deleteRate(company: String) =
        rateDataSource.rateLocal.deleteRate(company = company)
}

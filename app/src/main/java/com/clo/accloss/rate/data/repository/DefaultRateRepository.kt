package com.clo.accloss.rate.data.repository

import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
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
import kotlin.coroutines.CoroutineContext

class DefaultRateRepository(
    override val rateDataSource: RateDataSource,
    override val coroutineContext: CoroutineContext
) : RateRepository {
    override suspend fun getRemoteRate(
        baseUrl: String,
        company: String
    ): RequestState<Rate> {
        return withContext(coroutineContext) {
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
    ): Flow<RequestState<Rate>> = flow {
        emit(RequestState.Loading)

        rateDataSource.rateLocal.getRate(company)
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("RATES REPOSITORY: getRate")
            }
            .collect { rateEntity ->
                if (rateEntity != null) {
                    emit(
                        RequestState.Success(
                            data = rateEntity.toDomain()
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = R.string.there_are_no_rates
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override suspend fun addRate(rate: Rate) {
        withContext(coroutineContext){
            rateDataSource.rateLocal.addRate(rate.toDatabase())
        }
    }

    override suspend fun deleteRate(company: String)  {
        withContext(coroutineContext){
            rateDataSource.rateLocal.deleteRate(company = company)
        }
    }
}

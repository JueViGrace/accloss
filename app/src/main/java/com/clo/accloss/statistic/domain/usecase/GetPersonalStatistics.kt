package com.clo.accloss.statistic.domain.usecase

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPersonalStatistics(
    private val getCurrentUser: GetCurrentUser,
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke(code: String): Flow<RequestState<PersonalStatistics>> = flow {
        emit(RequestState.Loading)

        getCurrentUser().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(
                            message = sessionResult.message
                        )
                    )
                }
                is RequestState.Success -> {
                    var personalStatistics = PersonalStatistics()

                    val statistic = statisticRepository.getStatistic(
                        code = code,
                        company = sessionResult.data.empresa
                    )

                    when (statistic) {
                        is RequestState.Error -> {
                            emit(
                                RequestState.Error(
                                    message = statistic.message
                                )
                            )
                        }
                        is RequestState.Success -> {
                            personalStatistics = personalStatistics.copy(
                                statistic = statistic.data
                            )
                        }
                        else -> emit(RequestState.Loading)
                    }

                    val personalStatistic = statisticRepository.getPersonalStatistic(
                        code = code,
                        company = sessionResult.data.empresa
                    )

                    when (personalStatistic) {
                        is RequestState.Error -> {
                            emit(
                                RequestState.Error(
                                    message = personalStatistic.message
                                )
                            )
                        }
                        is RequestState.Success -> {
                            personalStatistics = personalStatistics.copy(
                                prcmeta = personalStatistic.data.prcmeta,
                                mtofactneto = personalStatistic.data.mtofactneto,
                                cantped = personalStatistic.data.cantped,
                                meta = personalStatistic.data.meta,
                                mtocob = personalStatistic.data.mtocob,
                                deuda = personalStatistic.data.deuda,
                                vencido = personalStatistic.data.vencido,
                                promdiasvta = personalStatistic.data.promdiasvta,
                                cantdocs = personalStatistic.data.cantdocs,
                                totmtodocs = personalStatistic.data.totmtodocs,
                                prommtopordoc = personalStatistic.data.prommtopordoc,
                                nombre = personalStatistic.data.nombre,
                                codigo = personalStatistic.data.codigo,
                            )
                        }
                        else -> emit(RequestState.Loading)
                    }

                    emit(
                        RequestState.Success(
                            data = personalStatistics
                        )
                    )
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}

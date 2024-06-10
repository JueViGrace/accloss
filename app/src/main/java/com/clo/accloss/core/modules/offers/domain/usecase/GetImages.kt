package com.clo.accloss.core.modules.offers.domain.usecase

import com.clo.accloss.core.common.Constants.UNEXPECTED_ERROR
import com.clo.accloss.core.common.log
import com.clo.accloss.core.modules.offers.domain.model.Image
import com.clo.accloss.core.modules.offers.domain.repository.OffersRepository
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetImages(
    private val getCurrentUser: GetCurrentUser,
    private val offersRepository: OffersRepository,
    private val coroutineContext: CoroutineContext
) {
    operator fun invoke(): Flow<RequestState<List<Image>>> = flow {
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
                    offersRepository.deleteImages(company = sessionResult.data.empresa)

                    val remote = offersRepository.getRemoteImages(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        company = sessionResult.data.empresa
                    )

                    when (remote) {
                        is RequestState.Error -> {
                            emit(
                                RequestState.Error(
                                    message = remote.message
                                )
                            )
                        }
                        is RequestState.Success -> {
                            offersRepository.getImages(
                                company = sessionResult.data.empresa
                            ).catch { e ->
                                emit(
                                    RequestState.Error(
                                        message = UNEXPECTED_ERROR
                                    )
                                )
                                e.log("GET IMAGES USE CASE: getImages")
                            }.collect { result ->
                                when (result) {
                                    is RequestState.Error -> {
                                        emit(
                                            RequestState.Error(
                                                message = result.message
                                            )
                                        )
                                    }
                                    is RequestState.Success -> {
                                        emit(
                                            RequestState.Success(
                                                data = result.data
                                            )
                                        )
                                    }
                                    else -> emit(RequestState.Loading)
                                }
                            }
                        }
                        else -> emit(RequestState.Loading)
                    }
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(coroutineContext)
}

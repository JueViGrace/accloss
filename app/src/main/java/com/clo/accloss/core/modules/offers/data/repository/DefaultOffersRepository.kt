package com.clo.accloss.core.modules.offers.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.modules.offers.data.mappers.toDatabase
import com.clo.accloss.core.modules.offers.data.mappers.toDomain
import com.clo.accloss.core.modules.offers.data.source.OffersDataSource
import com.clo.accloss.core.modules.offers.domain.model.Image
import com.clo.accloss.core.modules.offers.domain.repository.OffersRepository
import com.clo.accloss.core.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultOffersRepository(
    override val offersDataSource: OffersDataSource,
    override val coroutineContext: CoroutineContext,
) : OffersRepository {
    override suspend fun getRemoteImages(
        baseUrl: String,
        company: String,
    ): RequestState<List<Image>> {
        return withContext(coroutineContext) {
            when (val images = offersDataSource.offersRemote.getImages(baseUrl)) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = images.error
                    )
                }
                is ApiOperation.Success -> {
                    val data = images.data.imgs.map { imageItem ->
                        imageItem.toDomain().copy(
                            empresa = company
                        )
                    }

                    addImages(data)

                    RequestState.Success(data = data)
                }
            }
        }
    }

    override fun getImages(company: String): Flow<RequestState<List<Image>>> = flow {
        emit(RequestState.Loading)

        offersDataSource.offersLocal
            .getImages(company)
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("OFFERS REPOSITORY: getImages")
            }
            .collect { list ->
                if (list.isNotEmpty()){
                    emit(
                        RequestState.Success(
                            data = list.map { imageEntity ->
                                imageEntity.toDomain()
                            }
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = "No images"
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override suspend fun getImagesSync(company: String): List<Image> {
        return withContext(coroutineContext) {
            offersDataSource.offersLocal
                .getImagesSync(company)
                .map { imageEntity ->
                    imageEntity.toDomain()
                }
        }
    }

    override suspend fun addImages(images: List<Image>) {
        offersDataSource.offersLocal
            .addImages(
                images.map { image: Image ->
                    image.toDatabase()
                }
            )
    }

    override suspend fun deleteImages(company: String) {
        offersDataSource.offersLocal.deleteImages(company)
    }
}

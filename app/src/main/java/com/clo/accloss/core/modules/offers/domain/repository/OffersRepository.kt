package com.clo.accloss.core.modules.offers.domain.repository

import com.clo.accloss.core.modules.offers.data.source.OffersDataSource
import com.clo.accloss.core.modules.offers.domain.model.Image
import com.clo.accloss.core.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface OffersRepository {
    val coroutineContext: CoroutineContext
    val offersDataSource: OffersDataSource

    suspend fun getRemoteImages(
        baseUrl: String,
        company: String
    ): RequestState<List<Image>>

    fun getImages(
        company: String
    ): Flow<RequestState<List<Image>>>

    suspend fun getImagesSync(
        company: String
    ): List<Image>

    suspend fun addImages(images: List<Image>)

    suspend fun deleteImages(company: String)
}

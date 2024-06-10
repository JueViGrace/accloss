package com.clo.accloss.core.modules.offers.data.local

import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Imagen as ImageEntity

interface OffersLocal {
    suspend fun getImages(company: String): Flow<List<ImageEntity>>

    suspend fun getImagesSync(company: String): List<ImageEntity>

    suspend fun addImages(images: List<ImageEntity>)

    suspend fun deleteImages(company: String)
}

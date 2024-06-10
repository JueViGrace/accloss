package com.clo.accloss.core.modules.offers.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.clo.accloss.Imagen
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DefaultOffersLocal(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : OffersLocal {
    override suspend fun getImages(company: String): Flow<List<Imagen>> = scope.async {
        dbHelper.withDatabase { db ->
            db.imagenQueries
                .getImages(
                    empresa = company
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getImagesSync(company: String): List<Imagen> = scope.async {
        dbHelper.withDatabase { db ->
            db.imagenQueries
                .getImages(
                    empresa = company
                )
                .executeAsList()
        }
    }.await()

    override suspend fun addImages(images: List<Imagen>) {
        dbHelper.withDatabase { db ->
            images.forEach { image ->
                db.imagenQueries.addImage(image)
            }
        }
    }

    override suspend fun deleteImages(company: String) {
        dbHelper.withDatabase { db ->
            db.imagenQueries.deleteImages(company)
        }
    }
}

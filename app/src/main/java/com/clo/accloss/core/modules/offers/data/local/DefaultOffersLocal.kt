package com.clo.accloss.core.modules.offers.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.clo.accloss.Imagen
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class DefaultOffersLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : OffersLocal {
    override suspend fun getImages(company: String): Flow<List<Imagen>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.imagenQueries
                    .getImages(
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getImagesSync(company: String): List<Imagen> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.imagenQueries
                    .getImages(
                        empresa = company
                    )
                    .executeAsList()
            }
        }.await()
    }

    override suspend fun addImages(images: List<Imagen>) {
        dbHelper.withDatabase { db ->
            db.imagenQueries.transaction {
                images.forEach { image ->
                    db.imagenQueries.addImage(image)
                }
            }
        }
    }

    override suspend fun deleteImages(company: String) {
        dbHelper.withDatabase { db ->
            db.imagenQueries.transaction {
                db.imagenQueries.deleteImages(company)
            }
        }
    }
}

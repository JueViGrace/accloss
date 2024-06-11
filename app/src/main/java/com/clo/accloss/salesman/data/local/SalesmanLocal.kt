package com.clo.accloss.salesman.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Vendedor as SalesmenEntity

interface SalesmanLocal {
    val scope: CoroutineScope

    suspend fun getSalesmen(company: String): Flow<List<SalesmenEntity>>

    suspend fun getSalesman(
        salesman: String,
        company: String
    ): Flow<SalesmenEntity?>

    suspend fun addSalesmen(salesmen: List<SalesmenEntity>)

    suspend fun deleteSalesmen(company: String)
}

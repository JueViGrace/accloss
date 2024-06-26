package com.clo.accloss.salesman.domain.repository

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.salesman.data.source.SalesmanDataSource
import com.clo.accloss.salesman.domain.model.Salesman
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface SalesmanRepository {
    val salesmanDataSource: SalesmanDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteSalesman(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Salesman>>

    suspend fun getRemoteMasters(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Salesman>>

    fun getSalesmen(
        user: String,
        company: String,
    ): Flow<RequestState<List<Salesman>>>

    fun getSalesman(
        salesman: String,
        company: String
    ): Flow<RequestState<Salesman>>

    suspend fun addSalesmen(salesmen: List<Salesman>)

    suspend fun deleteSalesmen(company: String)
}

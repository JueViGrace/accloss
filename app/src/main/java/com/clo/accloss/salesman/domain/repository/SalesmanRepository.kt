package com.clo.accloss.salesman.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.salesman.data.source.SalesmanDataSource
import com.clo.accloss.salesman.domain.model.Salesman
import kotlinx.coroutines.flow.Flow

interface SalesmanRepository {
    val salesmanDataSource: SalesmanDataSource

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
        baseUrl: String,
        user: String,
        company: String,
    ): Flow<RequestState<List<Salesman>>>

    fun getSalesman(
        salesman: String,
        company: String
    ): Flow<RequestState<Salesman>>

    suspend fun addSalesmen(salesmen: List<Salesman>)
}

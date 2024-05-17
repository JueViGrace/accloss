package com.clo.accloss.billlines.di

import com.clo.accloss.billlines.data.local.BillLinesLocal
import com.clo.accloss.billlines.data.local.BillLinesLocalImpl
import com.clo.accloss.billlines.data.remote.source.BillLinesRemote
import com.clo.accloss.billlines.data.remote.source.BillLinesRemoteImpl
import com.clo.accloss.billlines.data.repository.BillLinesRepositoryImpl
import com.clo.accloss.billlines.data.source.BillLinesDataSource
import com.clo.accloss.billlines.data.source.BillLinesDataSourceImpl
import com.clo.accloss.billlines.domain.repository.BillLinesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val billLinesModule = module {
    singleOf(::BillLinesRemoteImpl) bind BillLinesRemote::class

    singleOf(::BillLinesLocalImpl) bind BillLinesLocal::class

    singleOf(::BillLinesDataSourceImpl) bind BillLinesDataSource::class

    singleOf(::BillLinesRepositoryImpl) bind BillLinesRepository::class
}

package com.clo.accloss.billlines.di

import com.clo.accloss.billlines.data.local.BillLinesLocalSource
import com.clo.accloss.billlines.data.remote.source.BillLinesRemoteSource
import com.clo.accloss.billlines.domain.repository.BillLinesRepository
import org.koin.dsl.module

val billLinesModule = module {
    single {
        BillLinesRemoteSource(get())
    }
    single {
        BillLinesLocalSource(get(), get())
    }
    single {
        BillLinesRepository(get(), get())
    }
}

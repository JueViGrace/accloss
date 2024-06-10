package com.clo.accloss.core.modules.offers.di

import com.clo.accloss.core.modules.offers.data.local.DefaultOffersLocal
import com.clo.accloss.core.modules.offers.data.local.OffersLocal
import com.clo.accloss.core.modules.offers.data.remote.source.DefaultOffersRemote
import com.clo.accloss.core.modules.offers.data.remote.source.OffersRemote
import com.clo.accloss.core.modules.offers.data.repository.DefaultOffersRepository
import com.clo.accloss.core.modules.offers.data.source.DefaultOffersDataSource
import com.clo.accloss.core.modules.offers.data.source.OffersDataSource
import com.clo.accloss.core.modules.offers.domain.repository.OffersRepository
import com.clo.accloss.core.modules.offers.domain.usecase.GetImages
import com.clo.accloss.core.modules.offers.presentation.viewmodel.OffersViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val offersModule = module {
    singleOf(::DefaultOffersRemote) bind OffersRemote::class

    singleOf(::DefaultOffersLocal) bind OffersLocal::class

    singleOf(::DefaultOffersDataSource) bind OffersDataSource::class

    singleOf(::DefaultOffersRepository) bind OffersRepository::class

    singleOf(::GetImages)

    factoryOf(::OffersViewModel)
}

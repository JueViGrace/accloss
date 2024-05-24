package com.clo.accloss.configuration.di

import com.clo.accloss.configuration.data.local.ConfigurationLocal
import com.clo.accloss.configuration.data.local.DefaultConfigurationLocal
import com.clo.accloss.configuration.data.remote.source.ConfigurationRemote
import com.clo.accloss.configuration.data.remote.source.DefaultConfigurationRemote
import com.clo.accloss.configuration.data.source.ConfigurationDataSource
import com.clo.accloss.configuration.data.source.DefaultConfigurationDataSource
import com.clo.accloss.configuration.domain.repository.ConfigurationRepository
import com.clo.accloss.configuration.domain.repository.DefaultConfigurationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val configurationModule = module {
    singleOf(::DefaultConfigurationRemote) bind ConfigurationRemote::class

    singleOf(::DefaultConfigurationLocal) bind ConfigurationLocal::class

    singleOf(::DefaultConfigurationDataSource) bind ConfigurationDataSource::class

    singleOf(::DefaultConfigurationRepository) bind ConfigurationRepository::class
}

package com.clo.accloss.configuration.data.source

import com.clo.accloss.configuration.data.local.ConfigurationLocal
import com.clo.accloss.configuration.data.remote.source.ConfigurationRemote

class DefaultConfigurationDataSource(
    override val configurationRemote: ConfigurationRemote,
    override val configurationLocal: ConfigurationLocal
) : ConfigurationDataSource

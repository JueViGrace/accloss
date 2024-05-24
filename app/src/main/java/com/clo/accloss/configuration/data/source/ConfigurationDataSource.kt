package com.clo.accloss.configuration.data.source

import com.clo.accloss.configuration.data.local.ConfigurationLocal
import com.clo.accloss.configuration.data.remote.source.ConfigurationRemote

interface ConfigurationDataSource {
    val configurationRemote: ConfigurationRemote
    val configurationLocal: ConfigurationLocal
}

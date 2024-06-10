package com.clo.accloss.core.modules.offers.data.source

import com.clo.accloss.core.modules.offers.data.local.OffersLocal
import com.clo.accloss.core.modules.offers.data.remote.source.OffersRemote

interface OffersDataSource {
    val offersRemote: OffersRemote
    val offersLocal: OffersLocal
}

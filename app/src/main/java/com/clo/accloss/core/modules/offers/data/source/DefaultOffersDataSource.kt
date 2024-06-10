package com.clo.accloss.core.modules.offers.data.source

import com.clo.accloss.core.modules.offers.data.local.OffersLocal
import com.clo.accloss.core.modules.offers.data.remote.source.OffersRemote

class DefaultOffersDataSource(
    override val offersRemote: OffersRemote,
    override val offersLocal: OffersLocal
) : OffersDataSource

package com.clo.accloss.rate.data.source

import com.clo.accloss.rate.data.local.RateLocal
import com.clo.accloss.rate.data.remote.source.RateRemote

class RateDataSourceImpl(
    override val rateRemote: RateRemote,
    override val rateLocal: RateLocal
) : RateDataSource

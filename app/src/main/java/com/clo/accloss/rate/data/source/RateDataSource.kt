package com.clo.accloss.rate.data.source

import com.clo.accloss.rate.data.local.RateLocal
import com.clo.accloss.rate.data.remote.source.RateRemote

interface RateDataSource {
    val rateRemote: RateRemote
    val rateLocal:RateLocal
}
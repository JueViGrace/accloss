package com.clo.accloss.products.data.source

import com.clo.accloss.products.data.local.ProductLocal
import com.clo.accloss.products.data.remote.source.ProductRemote

interface ProductDataSource {
    val productRemote: ProductRemote
    val productLocal: ProductLocal
}

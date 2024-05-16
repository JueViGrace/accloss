package com.clo.accloss.products.data.source

import com.clo.accloss.products.data.local.ProductLocal
import com.clo.accloss.products.data.remote.source.ProductRemote

class ProductDataSourceImpl(
    override val productRemote: ProductRemote,
    override val productLocal: ProductLocal
) : ProductDataSource
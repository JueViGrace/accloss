package com.clo.accloss.company.data.source

import com.clo.accloss.company.data.local.CompanyLocal
import com.clo.accloss.company.data.remote.source.CompanyRemote

class CompanyDataSourceImpl(
    override val companyRemote: CompanyRemote,
    override val companyLocal: CompanyLocal
) : CompanyDataSource

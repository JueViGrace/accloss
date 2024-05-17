package com.clo.accloss.company.data.source

import com.clo.accloss.company.data.local.CompanyLocal
import com.clo.accloss.company.data.remote.source.CompanyRemote

interface CompanyDataSource {
    val companyRemote: CompanyRemote
    val companyLocal: CompanyLocal
}

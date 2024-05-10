package com.clo.accloss.company.domain.rules

import com.clo.accloss.R

object CompanyValidator {
    fun validateErrors(company: String): CompanyValidationResult {
        var result = CompanyValidationResult()

        if (company.isEmpty()) {
            result = result.copy(companyError = R.string.company_empty_error)
        } else if (company.map { it.isDigit() }.contains(false)) {
            result = result.copy(companyError = R.string.company_not_number_error)
        }

        return result
    }

    data class CompanyValidationResult(
        val companyError: Int? = null
    )
}

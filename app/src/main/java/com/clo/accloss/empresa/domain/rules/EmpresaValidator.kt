package com.clo.accloss.empresa.domain.rules

object EmpresaValidator {
    fun validateErrors(codigo: String): EmpresaValidationResult {
        var result = EmpresaValidationResult()

        if (codigo.isEmpty()) {
            result = result.copy(codigoError = "Debe escribir un código")
        } else if (codigo.map { it.isDigit() }.contains(false)) {
            result = result.copy(codigoError = "El código debe ser un número")
        }

        return result
    }

    data class EmpresaValidationResult(
        val codigoError: String? = null
    )
}

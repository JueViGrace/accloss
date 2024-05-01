package com.clo.accloss.auth.login.domain.rules

object LoginValidator {

    fun validateErrors(username: String, password: String) {
        // TODO: VALIDATION
    }

    data class LoginValidationError(
        val usernameError: String? = null,
        val passwordError: String? = null
    )
}

package com.clo.accloss.login.domain.rules

import com.clo.accloss.R

object LoginValidator {

    fun validateErrors(username: String, password: String): LoginValidationError {
        var result = LoginValidationError()

        if (username.isBlank()) {
            result = result.copy(usernameError = R.string.username_empty_error)
        }

        if (password.isBlank()) {
            result = result.copy(passwordError = R.string.password_empty_error)
        }

        return result
    }

    data class LoginValidationError(
        val usernameError: Int? = null,
        val passwordError: Int? = null
    )
}

package com.clo.accloss.user.domain.rules

import com.clo.accloss.core.presentation.auth.login.domain.model.Login

object UserValidator {
    fun validateErrors(login: Login): UserValidationResult {
        var result = UserValidationResult()

        if (login.username.isBlank()) {
            result = result.copy(usernameError = "El nombre de usuario no puede estar vacío")
        }

        if (login.password.isBlank()) {
            result = result.copy(passwordError = "La contraseña no puede estar vacía")
        }

        return result
    }

    data class UserValidationResult(
        val usernameError: String? = null,
        val passwordError: String? = null
    )
}

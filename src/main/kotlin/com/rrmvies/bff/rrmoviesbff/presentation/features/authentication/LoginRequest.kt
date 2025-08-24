package com.rrmvies.bff.rrmoviesbff.presentation.features.authentication

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @NotBlank(message = "Username cannot be blank")
    val username: String,

    @NotBlank(message = "Password cannot be blank")
    val password: String
)

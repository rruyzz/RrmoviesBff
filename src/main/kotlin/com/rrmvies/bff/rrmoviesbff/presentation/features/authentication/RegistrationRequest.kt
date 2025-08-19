package com.rrmvies.bff.rrmoviesbff.presentation.features.authentication

data class RegistrationRequest(
    val email: String,
    val password: String
)
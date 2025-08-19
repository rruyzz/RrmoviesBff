package com.rrmvies.bff.rrmoviesbff.presentation.features.authentication

data class LoginRequest(
    val email: String,
    val password: String
)
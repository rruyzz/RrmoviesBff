package com.rrmvies.bff.rrmoviesbff.presentation.features.authentication

import com.fasterxml.jackson.annotation.JsonProperty

data class JwtResponse(
    @JsonProperty("accessToken")
    val token: String,

    @JsonProperty("tokenType")
    val type: String = "Bearer",

    val id: Long?,
    val username: String,
    val email: String,
    val roles: List<String>
) {
    constructor(
        token: String,
        id: Long?,
        username: String,
        email: String,
        roles: List<String>
    ) : this(token, "Bearer", id, username, email, roles)
}

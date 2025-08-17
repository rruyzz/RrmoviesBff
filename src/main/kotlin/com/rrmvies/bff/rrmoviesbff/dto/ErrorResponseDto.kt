package com.rrmvies.bff.rrmoviesbff.dto

import java.time.LocalDateTime

data class ErrorResponseDto(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)
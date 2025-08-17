package com.rrmvies.bff.rrmoviesbff.dto

data class MovieDto(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?
)

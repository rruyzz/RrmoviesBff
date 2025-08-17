package com.rrmvies.bff.rrmoviesbff.dto

data class MovieDto(
    val id: Long,
    val title: String,
    val backdropPath: String,
    val posterPath: String?
)

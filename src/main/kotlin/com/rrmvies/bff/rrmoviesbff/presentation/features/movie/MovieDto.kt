package com.rrmvies.bff.rrmoviesbff.presentation.features.movie

data class MovieDto(
    val id: Long,
    val title: String,
    val backdropPath: String,
    val posterPath: String?
)

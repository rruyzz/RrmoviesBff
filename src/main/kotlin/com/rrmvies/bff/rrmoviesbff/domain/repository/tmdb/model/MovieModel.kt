package com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.model

data class MovieModel(
    val id: Long,
    val title: String,
    val backdropPath: String,
    val posterPath: String?
)
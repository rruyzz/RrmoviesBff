package com.rrmvies.bff.rrmoviesbff.dto

data class MovieDetailDto(
    val isSaved: Boolean,
    val title: String,
    val backgroundPoster: String,
    val poster: String,
    val grade: String,
    val year: String,
    val minute: String,
    val gender: String,
    val description: String,
)
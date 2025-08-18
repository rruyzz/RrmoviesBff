package com.rrmvies.bff.rrmoviesbff.presentation.features.detail

data class DetailDto(
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
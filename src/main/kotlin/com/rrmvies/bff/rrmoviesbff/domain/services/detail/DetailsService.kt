package com.rrmvies.bff.rrmoviesbff.domain.services.detail

import com.rrmvies.bff.rrmoviesbff.domain.repository.TmdbRepository
import com.rrmvies.bff.rrmoviesbff.domain.repository.model.DetailModel
import com.rrmvies.bff.rrmoviesbff.presentation.features.detail.DetailDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DetailsService(
    private val tmdbClient: TmdbRepository,
    @Value("\${tmdb.api.image-base-url}") private val imageBaseUrl: String
) {

    suspend fun findMoviesDetails(movieId: String): DetailDto {
        val externalMovie = tmdbClient.findDetailMovie(movieId)
        return externalMovie.toDto()
    }

    private fun DetailModel.toDto(): DetailDto {
        return DetailDto(
            isSaved = false,
            title = this.title,
            backgroundPoster = this.backgroundPoster,
            poster = this.poster,
            grade = this.grade,
            year = this.year,
            minute = this.minute,
            gender = this.gender.firstOrNull().toString(),
            description = this.description
        )
    }
}
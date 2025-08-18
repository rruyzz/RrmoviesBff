package com.rrmvies.bff.rrmoviesbff.domain.services.movie

import com.rrmvies.bff.rrmoviesbff.client.tmdb.TmdbClient
import com.rrmvies.bff.rrmoviesbff.client.model.DetailResponse
import com.rrmvies.bff.rrmoviesbff.client.model.PopularMoviesResponse
import com.rrmvies.bff.rrmoviesbff.domain.core.extensions.ok
import com.rrmvies.bff.rrmoviesbff.domain.repository.TmdbRepository
import com.rrmvies.bff.rrmoviesbff.domain.repository.model.DetailModel
import com.rrmvies.bff.rrmoviesbff.domain.repository.model.MovieModel
import com.rrmvies.bff.rrmoviesbff.presentation.features.detail.DetailDto
import com.rrmvies.bff.rrmoviesbff.presentation.features.movie.MovieDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val tmdbClient: TmdbRepository,
) {

    suspend fun findPopularMovies(): ResponseEntity<List<MovieDto>> {
        val externalMovies = tmdbClient.findPopularMovies()
        return externalMovies?.toDto().ok()
    }

    suspend fun findNowPlayingMovies(): ResponseEntity<List<MovieDto>> {
        val externalMovies = tmdbClient.findNowPlayingMovies()
        return externalMovies?.toDto().ok()
    }

    suspend fun findTopRatedMovies(): ResponseEntity<List<MovieDto>> {
        val externalMovies = tmdbClient.findTopRatedMovies()
        return externalMovies?.toDto().ok()
    }

    private fun List<MovieModel>.toDto(): List<MovieDto> {
        return this.map {
            MovieDto(
                id = it.id,
                title = it.title,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath
            )
        }
    }
}

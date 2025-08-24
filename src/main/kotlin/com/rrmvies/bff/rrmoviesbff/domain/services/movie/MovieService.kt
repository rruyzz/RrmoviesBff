package com.rrmvies.bff.rrmoviesbff.domain.services.movie

import com.rrmvies.bff.rrmoviesbff.domain.core.exceptions.ResourceNotFoundException
import com.rrmvies.bff.rrmoviesbff.domain.core.extensions.ok
import com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.TmdbRepository
import com.rrmvies.bff.rrmoviesbff.domain.repository.model.MovieModel
import com.rrmvies.bff.rrmoviesbff.presentation.features.movie.MovieDto
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val tmdbClient: TmdbRepository,
) {

    suspend fun findPopularMovies(): ResponseEntity<List<MovieDto>> {
        val externalMovies = tmdbClient.findPopularMovies()
            ?: throw ResourceNotFoundException("Filmes populares não encontrado.")
        return externalMovies.toDto().ok()
    }

    suspend fun findNowPlayingMovies(): ResponseEntity<List<MovieDto>> {
        val externalMovies = tmdbClient.findNowPlayingMovies()
            ?: throw ResourceNotFoundException("Filmes em cartaz não encontrado.")
        return externalMovies.toDto().ok()
    }

    suspend fun findTopRatedMovies(): ResponseEntity<List<MovieDto>> {
        val externalMovies = tmdbClient.findTopRatedMovies()
            ?: throw ResourceNotFoundException("Filmes melhores avaliados não encontrado.")
        return externalMovies.toDto().ok()
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

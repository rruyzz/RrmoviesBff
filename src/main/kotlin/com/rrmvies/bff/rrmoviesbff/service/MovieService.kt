package com.rrmvies.bff.rrmoviesbff.service

import com.rrmvies.bff.rrmoviesbff.client.TmdbClient
import com.rrmvies.bff.rrmoviesbff.client.model.PopularMoviesResponse
import com.rrmvies.bff.rrmoviesbff.dto.MovieDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MovieService(
    private val tmdbClient: TmdbClient,
    @Value("\${tmdb.api.image-base-url}") private val imageBaseUrl: String
) {

    fun findPopularMovies(): List<MovieDto> {
        val externalMovies = tmdbClient.fetchPopularMovies()
        return mapToMovieDto(externalMovies)
    }

    fun findNowPlayingMovies(): List<MovieDto> {
        val externalMovies = tmdbClient.fetchNowPlayingMovies()
        return mapToMovieDto(externalMovies)
    }

    fun findTopRatedMovies(): List<MovieDto> {
        val externalMovies = tmdbClient.fetchTopRatedMovies()
        return mapToMovieDto(externalMovies)
    }


    private fun mapToMovieDto(externalMovies: PopularMoviesResponse?): List<MovieDto> {
        return externalMovies?.results?.map {
            MovieDto(
                id = it.id?.toLong() ?: 0L,
                title = it.title.toString(),
                // A lógica de negócio de montar a URL completa fica aqui!
                backdropPath = it.backdrop_path?.let { "$imageBaseUrl$it" } ?: "",
                posterPath = it.poster_path?.let { "$imageBaseUrl$it" }
            )
        } ?: listOf()
    }
}

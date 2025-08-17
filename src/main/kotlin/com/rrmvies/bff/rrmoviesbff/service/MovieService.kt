package com.rrmvies.bff.rrmoviesbff.service

import com.rrmvies.bff.rrmoviesbff.client.TmdbClient
import com.rrmvies.bff.rrmoviesbff.client.model.DetailResponse
import com.rrmvies.bff.rrmoviesbff.client.model.PopularMoviesResponse
import com.rrmvies.bff.rrmoviesbff.dto.MovieDetailDto
import com.rrmvies.bff.rrmoviesbff.dto.MovieDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

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

    fun findMoviesDetails(movieId: String) : MovieDetailDto? {
        val externalMovie = tmdbClient.fetchMoviesDetails(movieId)
        return externalMovie?.mapToDetailDto()
    }

    private fun DetailResponse.mapToDetailDto(): MovieDetailDto {
        return MovieDetailDto(
            isSaved = false,
            title = this.title.orEmpty(),
            backgroundPoster = this.backdrop_path?.let { "$imageBaseUrl$it" } ?: "",
            poster = this.poster_path?.let { "$imageBaseUrl$it" } ?: "",
            grade = this.vote_average.toString(),
            year = this.release_date.orEmpty(),
            minute = this.runtime.toString(),
            gender = this.genres?.firstOrNull().toString(),
            description = this.overview.orEmpty()
        )
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

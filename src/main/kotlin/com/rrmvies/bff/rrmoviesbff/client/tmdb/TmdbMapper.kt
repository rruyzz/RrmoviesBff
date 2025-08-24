package com.rrmvies.bff.rrmoviesbff.client.tmdb

import com.rrmvies.bff.rrmoviesbff.client.model.DetailResponse
import com.rrmvies.bff.rrmoviesbff.client.model.PopularMoviesResponse
import com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.model.DetailModel
import com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.model.MovieModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TmdbMapper(
    @Value("\${tmdb.api.image-base-url}") private val imageBaseUrl: String
) {

    fun map(response: DetailResponse?): DetailModel? {
        return response?.let {
            DetailModel(
                isSaved = false,
                title = response.title.orEmpty(),
                backgroundPoster = response.backdrop_path?.let { "$imageBaseUrl$it" } ?: "",
                poster = response.poster_path?.let { "$imageBaseUrl$it" } ?: "",
                grade = response.vote_average.toString(),
                year = response.release_date.orEmpty(),
                minute = response.runtime.toString(),
                gender = response.genres?.firstOrNull().toString(),
                description = response.overview.orEmpty()
            )
        }
    }

    fun map(response: PopularMoviesResponse?): List<MovieModel>? {
        return response?.results?.map {
            MovieModel(
                id = it.id?.toLong() ?: 0L,
                title = it.title.toString(),
                backdropPath = it.backdrop_path?.let { "$imageBaseUrl$it" } ?: "",
                posterPath = it.poster_path?.let { "$imageBaseUrl$it" }
            )
        }
    }
}
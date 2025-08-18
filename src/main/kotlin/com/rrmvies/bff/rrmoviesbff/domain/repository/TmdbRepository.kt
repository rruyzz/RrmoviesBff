package com.rrmvies.bff.rrmoviesbff.domain.repository

import com.rrmvies.bff.rrmoviesbff.domain.repository.model.DetailModel
import com.rrmvies.bff.rrmoviesbff.domain.repository.model.MovieModel
import com.rrmvies.bff.rrmoviesbff.presentation.features.movie.MovieDto

interface TmdbRepository {
    suspend fun findPopularMovies(): List<MovieModel>
    suspend fun findNowPlayingMovies(): List<MovieModel>
    suspend fun findTopRatedMovies(): List<MovieModel>
    suspend fun findDetailMovie(movieId: String): DetailModel
}
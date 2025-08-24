package com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb

import com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.model.DetailModel
import com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.model.MovieModel

interface TmdbRepository {
    suspend fun findPopularMovies(): List<MovieModel>?
    suspend fun findNowPlayingMovies(): List<MovieModel>?
    suspend fun findTopRatedMovies(): List<MovieModel>?
    suspend fun findDetailMovie(movieId: String): DetailModel?
}
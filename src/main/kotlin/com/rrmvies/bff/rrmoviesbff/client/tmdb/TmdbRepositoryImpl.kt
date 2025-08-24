package com.rrmvies.bff.rrmoviesbff.client.tmdb

import com.rrmvies.bff.rrmoviesbff.domain.core.exceptions.ResourceNotFoundException
import com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.TmdbRepository
import com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.model.DetailModel
import com.rrmvies.bff.rrmoviesbff.domain.repository.tmdb.model.MovieModel
import org.springframework.stereotype.Repository

@Repository
class TmdbRepositoryImpl(
    private val client: TmdbClient,
    private val mapper: TmdbMapper,
) : TmdbRepository {
    override suspend fun findPopularMovies(): List<MovieModel>? {
        val popularMovies = client.fetchPopularMovies()
        return mapper.map(popularMovies)
    }

    override suspend fun findNowPlayingMovies(): List<MovieModel>? {
        val nowPlayingMovies = client.fetchNowPlayingMovies()
            ?: throw ResourceNotFoundException("Filmes em cartaz não encontrado.")
        return mapper.map(nowPlayingMovies)
    }

    override suspend fun findTopRatedMovies(): List<MovieModel>? {
        val topRatedMovies = client.fetchTopRatedMovies()
            ?: throw ResourceNotFoundException("Filmes melhores avaliados não encontrado.")
        return mapper.map(topRatedMovies)
    }

    override suspend fun findDetailMovie(movieId: String): DetailModel? {
        val detailMovie = client.fetchMoviesDetails(movieId)
            ?: throw ResourceNotFoundException("Filme com ID '$movieId' não encontrado.")
        return mapper.map(detailMovie)
    }


}
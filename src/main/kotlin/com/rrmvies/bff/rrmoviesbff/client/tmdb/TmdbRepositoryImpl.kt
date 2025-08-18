package com.rrmvies.bff.rrmoviesbff.client.tmdb

import com.rrmvies.bff.rrmoviesbff.domain.core.exceptions.ResourceNotFoundException
import com.rrmvies.bff.rrmoviesbff.domain.repository.TmdbRepository
import com.rrmvies.bff.rrmoviesbff.domain.repository.model.DetailModel
import com.rrmvies.bff.rrmoviesbff.domain.repository.model.MovieModel
import org.springframework.stereotype.Repository

@Repository
class TmdbRepositoryImpl(
    private val client: TmdbClient,
    private val mapper: TmdbMapper,
) : TmdbRepository {
    override suspend fun findPopularMovies(): List<MovieModel> {
        val popularMovies = client.fetchPopularMovies()
            ?: throw ResourceNotFoundException("Filmes populares não encontrado.")
        return mapper.map(popularMovies)
    }

    override suspend fun findNowPlayingMovies(): List<MovieModel> {
        val nowPlayingMovies = client.fetchPopularMovies()
            ?: throw ResourceNotFoundException("Filmes em cartaz não encontrado.")
        return mapper.map(nowPlayingMovies)
    }

    override suspend fun findTopRatedMovies(): List<MovieModel> {
        val topRatedMovies = client.fetchPopularMovies()
            ?: throw ResourceNotFoundException("Filmes melhores avaliados não encontrado.")
        return mapper.map(topRatedMovies)
    }

    override suspend fun findDetailMovie(movieId: String): DetailModel {
        val detailMovie = client.fetchMoviesDetails(movieId)
            ?: throw ResourceNotFoundException("Filme com ID '$movieId' não encontrado.")
        return mapper.map(detailMovie)
    }


}
package com.rrmvies.bff.rrmoviesbff.client.tmdb

import com.rrmvies.bff.rrmoviesbff.client.model.DetailResponse
import com.rrmvies.bff.rrmoviesbff.client.model.PopularMoviesResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody

@Component
class TmdbClient(
    private val tmdbWebClient: WebClient, // Injeta o WebClient que configuramos
    @Value("\${tmdb.api.key}") private val apiKey: String,
    @Value("\${tmdb.api.base-url}") private val baseUrl: String
) {

    private val restTemplate = RestTemplate()

    suspend fun fetchPopularMovies(): PopularMoviesResponse? {
        val response = tmdbWebClient.get()
            .uri("/movie/popular?api_key=$apiKey")
            .retrieve()
            .awaitBody<PopularMoviesResponse>()
        return response
    }

    suspend fun fetchNowPlayingMovies(): PopularMoviesResponse? {
        val response = tmdbWebClient.get()
            .uri("/movie/now_playing?api_key=$apiKey")
            .retrieve()
            .awaitBody<PopularMoviesResponse>()
        return response
    }

    suspend fun fetchTopRatedMovies(): PopularMoviesResponse? {
        val response = tmdbWebClient.get()
            .uri("/movie/top_rated?api_key=$apiKey")
            .retrieve()
            .awaitBody<PopularMoviesResponse>()
        return response
    }

    suspend fun fetchMoviesDetails(movieId: String): DetailResponse? {
        return try {
            tmdbWebClient.get()
                .uri("/movie/$movieId?api_key=$apiKey")
                .retrieve()
                .awaitBody<DetailResponse>()
        } catch (ex: WebClientResponseException.NotFound) {
            // A API do TMDb retornou 404, então o filme não existe.
            null
        }
    }
}
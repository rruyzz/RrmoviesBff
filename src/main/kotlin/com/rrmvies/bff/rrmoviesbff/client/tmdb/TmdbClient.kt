package com.rrmvies.bff.rrmoviesbff.client.tmdb

import com.rrmvies.bff.rrmoviesbff.client.model.DetailResponse
import com.rrmvies.bff.rrmoviesbff.client.model.PopularMoviesResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody

@Component
class TmdbClient(
    private val tmdbWebClient: WebClient,
    @Value("\${tmdb.api.key}") private val apiKey: String
) {

    suspend fun fetchPopularMovies(): PopularMoviesResponse? =
        getMovies("/movie/popular")

    suspend fun fetchNowPlayingMovies(): PopularMoviesResponse? =
        getMovies("/movie/now_playing")

    suspend fun fetchTopRatedMovies(): PopularMoviesResponse? =
        getMovies("/movie/top_rated")

    suspend fun fetchMoviesDetails(movieId: String): DetailResponse? =
        safeRequest {
            tmdbWebClient.get()
                .uri("/movie/$movieId?api_key=$apiKey")
                .retrieve()
                .awaitBody()
        }

    // 🔹 Função genérica para requisições de listas de filmes
    private suspend fun getMovies(endpoint: String): PopularMoviesResponse? =
        safeRequest {
            tmdbWebClient.get()
                .uri("$endpoint?api_key=$apiKey")
                .retrieve()
                .awaitBody()
        }

    // 🔹 Wrapper para capturar erros de forma centralizada
    private suspend fun <T> safeRequest(block: suspend () -> T): T? {
        return try {
            block()
        } catch (ex: WebClientResponseException.NotFound) {
            null // filme ou endpoint não encontrado
        } catch (ex: WebClientResponseException) {
            println("Erro na API TMDb: ${ex.statusCode} - ${ex.responseBodyAsString}")
            null
        }
    }
}

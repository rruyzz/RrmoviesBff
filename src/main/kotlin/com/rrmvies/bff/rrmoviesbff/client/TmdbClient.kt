package com.rrmvies.bff.rrmoviesbff.client

import com.rrmvies.bff.rrmoviesbff.client.model.PopularMoviesResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TmdbClient(
    @Value("\${tmdb.api.key}") private val apiKey: String,
    @Value("\${tmdb.api.base-url}") private val baseUrl: String
) {

    private val restTemplate = RestTemplate()

    fun fetchPopularMovies(): PopularMoviesResponse? {
        val url = "$baseUrl/movie/popular?api_key=$apiKey"
        val response = restTemplate.getForObject(url, PopularMoviesResponse::class.java)
        return response
    }

    fun fetchNowPlayingMovies(): PopularMoviesResponse? {
        val url = "$baseUrl/movie/now_playing?api_key=$apiKey"
        val response = restTemplate.getForObject(url, PopularMoviesResponse::class.java)
        return response
    }

    fun fetchTopRatedMovies(): PopularMoviesResponse? {
        val url = "$baseUrl/movie/top_rated?api_key=$apiKey"
        val response = restTemplate.getForObject(url, PopularMoviesResponse::class.java)
        return response
    }
}
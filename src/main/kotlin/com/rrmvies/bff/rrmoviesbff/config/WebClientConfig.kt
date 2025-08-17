package com.rrmvies.bff.rrmoviesbff.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${tmdb.api.base-url}") private val baseUrl: String
) {

    @Bean
    fun tmdbWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(baseUrl) // A URL base já fica pré-configurada
            .build()
    }
}
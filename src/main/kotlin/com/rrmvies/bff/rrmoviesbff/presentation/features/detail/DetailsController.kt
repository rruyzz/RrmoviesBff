package com.rrmvies.bff.rrmoviesbff.presentation.features.detail

import com.rrmvies.bff.rrmoviesbff.domain.services.detail.DetailsService
import com.rrmvies.bff.rrmoviesbff.domain.core.extensions.ok
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies-bff")
class DetailsController(
    private val detailsService: DetailsService
) {

    @GetMapping("/{movieId}")
    suspend fun getMoviesDetails(@PathVariable movieId: String): ResponseEntity<DetailDto> {
        return detailsService.findMoviesDetails(movieId).ok()
    }
}
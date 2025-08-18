package com.rrmvies.bff.rrmoviesbff.presentation.features.movie

// Dentro do pacote controller
import com.rrmvies.bff.rrmoviesbff.domain.services.movie.MovieService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies-bff") // Prefixo para todos os endpoints neste controller
class MovieController(private val movieService: MovieService) {

    @GetMapping("/popular")
    suspend fun getPopularMovies(): ResponseEntity<List<MovieDto>> {
        return movieService.findPopularMovies()
    }

    @GetMapping("/now-playing")
    suspend fun getNowPlayingMovies(): ResponseEntity<List<MovieDto>> {
        return movieService.findNowPlayingMovies()
    }

    @GetMapping("/top-rated")
    suspend fun getTopRatedMovies(): ResponseEntity<List<MovieDto>> {
        return movieService.findTopRatedMovies()
    }

}

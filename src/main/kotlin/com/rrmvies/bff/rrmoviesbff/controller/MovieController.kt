package com.rrmvies.bff.rrmoviesbff.controller

// Dentro do pacote controller
import com.rrmvies.bff.rrmoviesbff.dto.MovieDto
import com.rrmvies.bff.rrmoviesbff.service.MovieService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies-bff") // Prefixo para todos os endpoints neste controller
class MovieController(private val movieService: MovieService) {

    @GetMapping("/popular")
    fun getPopularMovies(): List<MovieDto> {
        return movieService.getPopularMovies()
    }


    @GetMapping("/now-playing")
    fun getNowPlayingMovies(): List<MovieDto> {
        return movieService.getPopularMovies()
    }


    @GetMapping("/top-rated")
    fun getTopRatedMovies(): List<MovieDto> {
        return movieService.getPopularMovies()
    }
}

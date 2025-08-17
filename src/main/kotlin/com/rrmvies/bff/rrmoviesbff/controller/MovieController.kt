package com.rrmvies.bff.rrmoviesbff.controller

// Dentro do pacote controller
import com.rrmvies.bff.rrmoviesbff.dto.MovieDto
import com.rrmvies.bff.rrmoviesbff.service.MovieService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies-bff") // Prefixo para todos os endpoints neste controller
class MovieController(private val movieService: MovieService) {

    @GetMapping("/popular")
    fun getPopularMovies(): ResponseEntity<List<MovieDto>> {
        return ResponseEntity.ok(movieService.findPopularMovies())
    }


    @GetMapping("/now-playing")
    fun getNowPlayingMovies(): ResponseEntity<List<MovieDto>> {
        return ResponseEntity.ok(movieService.findNowPlayingMovies())
    }


    @GetMapping("/top-rated")
    fun getTopRatedMovies(): ResponseEntity<List<MovieDto>> {
        return ResponseEntity.ok(movieService.findTopRatedMovies())
    }
}

package com.rrmvies.bff.rrmoviesbff

import com.rrmvies.bff.rrmoviesbff.dto.MovieDto
import com.rrmvies.bff.rrmoviesbff.service.MovieService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class RrmoviesbffApplication

fun main(args: Array<String>) {
	runApplication<RrmoviesbffApplication>(*args)
}

data class MovieDto(
	val id: Long,
	val title: String,
	val backdropPath: String,
	val posterPath: String?
)


@Service
class MovieService {

	private val restTemplate = RestTemplate()
	private val apiKey = "e591023d8d396231d3045ea6341a6fd2"
	private val baseUrl = "https://api.themoviedb.org/3/"

	fun getPopularMovies(): List<MovieDto> {
		// NOTA: Esta é uma forma simplificada. O ideal é modelar a resposta completa
		// da API externa em suas próprias classes DTO.
		val response = restTemplate.getForObject(
			"$baseUrl/movie/popular?api_key=$apiKey",
			Map::class.java // Simplificando a resposta para um mapa
		)
		println("Rodolfeira $response")

		val results = response?.get("results") as? List<Map<String, Any>> ?: emptyList()

		return results.map { movieMap ->
			MovieDto(
				id = (movieMap["id"] as Number).toLong(),
				title = movieMap["title"] as String,
				backdropPath = "https://image.tmdb.org/t/p/w500" + (movieMap["backdrop_path"] as String),
				posterPath = "https://image.tmdb.org/t/p/w500" + movieMap["poster_path"] as? String
			)
		}
	}
}

@RestController
@RequestMapping("/movies-bff") // Prefixo para todos os endpoints neste controller
class MovieController(private val movieService: MovieService) {

	@GetMapping("/popular")
	fun getPopularMovies(): ResponseEntity<List<MovieDto>> {
		return ResponseEntity.ok(movieService.getPopularMovies())
	}


	@GetMapping("/now-playing")
	fun getNowPlayingMovies(): ResponseEntity<List<MovieDto>> {
		return ResponseEntity.ok(movieService.getPopularMovies())
	}


	@GetMapping("/top-rated")
	fun getTopRatedMovies(): ResponseEntity<List<MovieDto>> {
		return ResponseEntity.ok(movieService.getPopularMovies())
	}
}

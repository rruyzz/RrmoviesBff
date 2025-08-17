package com.rrmvies.bff.rrmoviesbff.client.model

data class DetailResponse(
    val adult: Boolean? = true,
    val backdrop_path: String? = null,
    val belongs_to_collection: BelongsToCollection? = null,
    val budget: Int? = 0,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int? = 0,
    val imdb_id: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = 0.0,
    val poster_path: String? = null,
    val production_companies: List<ProductionCompany>? = null,
    val production_countries: List<ProductionCountry>? = null,
    val release_date: String? = null,
    val revenue: Int? = 0,
    val runtime: Int? = 0,
    val spoken_languages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = true,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0
)

data class BelongsToCollection(
    val id: Int?,
    val name: String?,
    val poster_path: String?,
    val backdrop_path: String?
)

data class Genre(
    val id: Int?,
    val name: String?
)

data class ProductionCompany(
    val id: Int?,
    val logo_path: String?,
    val name: String?,
    val origin_country: String?
)

data class ProductionCountry(
    val iso_3166_1: String?,
    val name: String?
)

data class SpokenLanguage(
    val english_name: String?,
    val iso_639_1: String?,
    val name: String?
)

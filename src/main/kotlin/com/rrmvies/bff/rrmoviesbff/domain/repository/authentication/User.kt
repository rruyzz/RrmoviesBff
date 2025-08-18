package com.rrmvies.bff.rrmoviesbff.domain.repository.authentication

import jakarta.persistence.*

@Entity
@Table(name = "app_users") // "user" é uma palavra reservada em muitos bancos de dados
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val passwordHash: String, // NUNCA salve a senha em texto plano!

    // Relacionamento com os filmes favoritos
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_favorite_movies", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "movie_id")
    val favoriteMovieIds: MutableSet<Long> = mutableSetOf()
)

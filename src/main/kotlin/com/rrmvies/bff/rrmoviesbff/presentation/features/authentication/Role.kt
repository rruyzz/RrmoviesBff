package com.rrmvies.bff.rrmoviesbff.presentation.features.authentication

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    val name: RoleName
) {
    // Construtor vazio para JPA
    constructor() : this(null, RoleName.ROLE_USER)
}

enum class RoleName {
    ROLE_USER,
    ROLE_ADMIN
}
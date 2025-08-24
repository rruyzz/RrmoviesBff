package com.rrmvies.bff.rrmoviesbff.presentation.features.authentication

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    val username: String,

    @NotBlank
    @Size(max = 100)
    @Email
    @Column(unique = true, nullable = false)
    val email: String,

    @NotBlank
    @Size(min = 6, max = 100)
    @Column(nullable = false)
    val password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Set<Role> = HashSet(),

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val enabled: Boolean = true
)
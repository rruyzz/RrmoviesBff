package com.rrmvies.bff.rrmoviesbff.domain.repository.authentication

import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}
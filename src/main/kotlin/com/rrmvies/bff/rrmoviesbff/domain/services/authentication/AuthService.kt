package com.rrmvies.bff.rrmoviesbff.domain.services.authentication

import com.rrmvies.bff.rrmoviesbff.domain.core.extensions.JwtUtils
import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.RoleRepository
import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.UserRepository
import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.model.UserPrincipal
import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.*
import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.Role
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {

    fun authenticateUser(loginRequest: LoginRequest): JwtResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)

        val userPrincipal = authentication.principal as UserPrincipal
        val roles = userPrincipal.authorities.map { it.authority }

        return JwtResponse(
            jwt,
            userPrincipal.id,
            userPrincipal.username,
            userPrincipal.email,
            roles
        )
    }

    fun registerUser(signUpRequest: SignupRequest): String {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            throw RuntimeException("Error: Username is already taken!")
        }

        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw RuntimeException("Error: Email is already in use!")
        }

        // Create new user's account
        val user = User(
            username = signUpRequest.username,
            email = signUpRequest.email,
            password = passwordEncoder.encode(signUpRequest.password)
        )

        val strRoles = signUpRequest.role
        val roles: MutableSet<Role> = HashSet()

        if (strRoles == null || strRoles.isEmpty()) {
            val userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow { RuntimeException("Error: Role is not found.") }
            roles.add(userRole)
        } else {
            strRoles.forEach { role ->
                when (role) {
                    "admin" -> {
                        val adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(adminRole)
                    }
                    else -> {
                        val userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow { RuntimeException("Error: Role is not found.") }
                        roles.add(userRole)
                    }
                }
            }
        }

        user.roles = roles
        userRepository.save(user)

        return "User registered successfully!"
    }
}
package com.rrmvies.bff.rrmoviesbff.presentation.features.authentication

import com.rrmvies.bff.rrmoviesbff.domain.services.authentication.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        val jwtResponse = authService.authenticateUser(loginRequest)
        return ResponseEntity.ok(jwtResponse)
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignupRequest): ResponseEntity<MessageResponse> {
        val message = authService.registerUser(signUpRequest)
        return ResponseEntity.ok(MessageResponse(message))
    }
}
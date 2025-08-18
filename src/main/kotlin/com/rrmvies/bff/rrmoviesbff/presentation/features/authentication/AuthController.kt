package com.rrmvies.bff.rrmoviesbff.presentation.features.authentication

import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.AuthenticationModel
import com.rrmvies.bff.rrmoviesbff.domain.services.authentication.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody registrationRequest: RegistrationRequest): ResponseEntity<AuthenticationModel> {
        val response = authService.register(registrationRequest)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<AuthenticationModel> {
        val response = authService.authenticate(loginRequest)
        return ResponseEntity.ok(response)
    }
}
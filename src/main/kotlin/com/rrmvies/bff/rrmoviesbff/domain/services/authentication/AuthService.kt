package com.rrmvies.bff.rrmoviesbff.domain.services.authentication

import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.AuthenticationModel
import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.User
import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.UserRepository
import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.LoginRequest
import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.RegistrationRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder, // Bean para criptografar senhas
    private val jwtService: JwtService,           // Nosso serviço para criar tokens
    private val authenticationManager: AuthenticationManager
) {
    /**
     * Registra um novo usuário no sistema.
     */
    @Transactional // Garante que a operação no banco de dados seja atômica
    fun register(request: RegistrationRequest): AuthenticationModel {
        // 1. Verificar se o usuário já existe
        if (userRepository.findByEmail(request.email).isPresent) {
            throw IllegalStateException("Usuário com o e-mail ${request.email} já existe.")
        }

        // 2. Criar a nova entidade User
        val user = User(
            email = request.email,
            // 3. Criptografar a senha ANTES de salvar!
            passwordHash = passwordEncoder.encode(request.password)
        )

        // 4. Salvar o usuário no banco de dados
        val savedUser = userRepository.save(user)

        // 5. Gerar um token JWT para o novo usuário
        val jwtToken = jwtService.generateToken(savedUser)

        // 6. Retornar a resposta com o token
        return AuthenticationModel(token = jwtToken)
    }

    /**
     * Autentica um usuário existente e retorna um token JWT.
     */
    fun authenticate(request: LoginRequest): AuthenticationModel {
        // 1. O AuthenticationManager valida as credenciais.
        // Ele usa o UserDetailsService (que configuraremos) para buscar o usuário
        // e o PasswordEncoder para comparar as senhas.
        // Se as credenciais estiverem erradas, ele lança uma exceção (ex: BadCredentialsException).
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        // 2. Se a autenticação foi bem-sucedida, buscar o usuário para gerar o token
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { IllegalStateException("Usuário não encontrado após autenticação bem-sucedida.") }

        // 3. Gerar e retornar o token JWT
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationModel(token = jwtToken)
    }

}
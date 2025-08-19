package com.rrmvies.bff.rrmoviesbff.config

import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.UserRepository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration // Marca esta classe como uma fonte de definições de beans
class ApplicationConfig() {

    /**
     * Define como o Spring Security deve buscar um usuário.
     * Ele será usado pelo AuthenticationManager para encontrar um usuário pelo e-mail durante o login.
     */
    @Bean
    fun userDetailsService(
        userRepository: UserRepository
    ): UserDetailsService {
        return UserDetailsService { email -> // O 'username' aqui é o nosso e-mail
            println("--- Buscando usuário pelo e-mail: $email ---") // Adicione este log

            userRepository.findByEmail(email)
                .map { user ->
                    println("--- Usuário encontrado: ${user.email} ---") // Adicione este log

                    // Converte nossa entidade User para o User do Spring Security
                    org.springframework.security.core.userdetails.User
                        .withUsername(user.email)
                        .password(user.passwordHash)
                        .authorities(listOf(SimpleGrantedAuthority("ROLE_USER")))
//                        .authorities(emptyList()) // Podemos adicionar roles/permissões aqui no futuro
                        .build()
                }
                .orElseThrow {
                    println("--- ERRO: Usuário não encontrado com o e-mail: $email ---") // Adicione este log

                    UsernameNotFoundException("Usuário não encontrado com o e-mail: $email") }
        }
    }

    /**
     * Define o algoritmo de criptografia de senhas.
     * Usamos BCrypt, que é forte e inclui um "salt" aleatório automaticamente.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * O provedor de autenticação que une o serviço de busca de usuário (UserDetailsService)
     * com o codificador de senhas (PasswordEncoder).
     */
    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService(userRepository))
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    /**
     * O gerenciador de autenticação que o nosso AuthService usará.
     * Ele orquestra o processo de autenticação usando o AuthenticationProvider.
     */
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}

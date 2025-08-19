package com.rrmvies.bff.rrmoviesbff.config

import com.rrmvies.bff.rrmoviesbff.config.filter.JwtAuthenticationFilter
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity // Habilita a configuração de segurança web do Spring
class SecurityConfig(
    @Lazy private val jwtAuthFilter: JwtAuthenticationFilter,
    @Lazy private val authenticationProvider: AuthenticationProvider // O bean que criamos na ApplicationConfig
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // 1. Desabilitar CSRF
            .csrf { it.disable() }

            // 2. Definir as regras de autorização para cada endpoint
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/auth/**").permitAll() // Endpoints de autenticação são públicos
                    // A forma correta de liberar o console do H2 no Spring Boot 3
//                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    // Adicione aqui outros endpoints públicos, se houver (ex: para documentação)
                    // .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
//                    .requestMatchers("/h2-console/**").permitAll() // Se você estiver usando

                    .anyRequest().authenticated() // Todas as outras requisições exigem autenticação
            }

            // 3. Configurar o gerenciamento de sessão para ser stateless
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            // 4. Registrar nosso provedor de autenticação
            .authenticationProvider(authenticationProvider)

            // 5. Adicionar nosso filtro JWT para ser executado antes do filtro padrão de username/password
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            // Necessário para o console do H2 funcionar, pois ele usa frames
            .headers { headers ->
                headers.frameOptions { it.disable() } // Desabilitar para o H2, ou sameOrigin()
            }

        return http.build()
    }
}

package com.rrmvies.bff.rrmoviesbff.config.filter

import com.rrmvies.bff.rrmoviesbff.domain.services.authentication.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component // Para que o Spring possa injetá-lo na nossa SecurityConfig
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val context: ApplicationContext,
) : OncePerRequestFilter() { // Garante que o filtro seja executado apenas uma vez por requisição

    private val userDetailsService: UserDetailsService by lazy {
        context.getBean(UserDetailsService::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        // 1. Se não houver cabeçalho de autorização ou não começar com "Bearer ",
        // passamos para o próximo filtro da cadeia.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        // 2. Extrair o token (removendo o prefixo "Bearer ")
        val jwt = authHeader.substring(7)
        val userEmail = jwtService.extractEmail(jwt)

        // 3. Se o token for válido e o usuário ainda não estiver autenticado no contexto atual
        if (SecurityContextHolder.getContext().authentication == null) {
            val userDetails = this.userDetailsService.loadUserByUsername(userEmail)

            if (jwtService.isTokenValid(jwt, userDetails.username)) {
                // 4. Criar um objeto de autenticação e colocá-lo no SecurityContextHolder.
                // A partir deste ponto, o Spring Security considera o usuário como autenticado para esta requisição.
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // Credenciais (senha) são nulas, pois estamos usando token
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        // 5. Passar a requisição para o próximo filtro na cadeia.
        filterChain.doFilter(request, response)
    }
}

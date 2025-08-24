package com.rrmvies.bff.rrmoviesbff.domain.core.extensions

import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.model.UserPrincipal
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils {

    @Value("\${rrmovies.app.jwtSecret}")
    private lateinit var jwtSecret: String

    @Value("\${rrmovies.app.jwtExpirationMs}")
    private val jwtExpirationMs: Int = 86400000 // 24 horas

    private val key: SecretKey by lazy {
        // Garante que a chave tenha pelo menos 256 bits
        val secretBytes = if (jwtSecret.length < 32) {
            // Se a chave for muito curta, preenche com zeros (não seguro para produção!)
            jwtSecret.padEnd(32, '0').toByteArray()
        } else {
            jwtSecret.toByteArray()
        }
        Keys.hmacShaKeyFor(secretBytes)
    }

    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserPrincipal
        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUserNameFromJwtToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken)
            return true
        } catch (e: Exception) {
            logger.error("Invalid JWT token: {}", e.message)
        }
        return false
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtUtils::class.java)
    }
}
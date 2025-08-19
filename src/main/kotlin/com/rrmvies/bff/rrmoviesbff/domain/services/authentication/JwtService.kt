package com.rrmvies.bff.rrmoviesbff.domain.services.authentication

import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret-key}") private val secretKeyString: String,
    @Value("\${jwt.expiration-ms}") private val expirationTime: Long
) {
    // A chave de assinatura, gerada a partir da string secreta.
    private val signingKey: SecretKey by lazy {
        val keyBytes = Decoders.BASE64.decode(secretKeyString)
        Keys.hmacShaKeyFor(keyBytes)
    }

    /**
     * Gera um novo token JWT para um usuário.
     */
    fun generateToken(user: User): String {
        return generateToken(emptyMap(), user)
    }

    fun generateToken(extraClaims: Map<String, Any>, user: User): String {
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .claims(extraClaims) // Adiciona quaisquer dados extras ao token
            .subject(user.email) // O "assunto" do token, identifica o usuário
            .issuedAt(Date(now)) // Data de emissão
            .expiration(Date(now + expirationTime)) // Data de expiração
            .signWith(signingKey) // Assina o token com nossa chave secreta
            .compact()
    }

    /**
     * Valida um token verificando a assinatura e a data de expiração.
     */
    fun isTokenValid(token: String, userEmail: String): Boolean {
        val extractedEmail = extractEmail(token)
        return (extractedEmail == userEmail) && !isTokenExpired(token)
    }

    /**
     * Extrai o e-mail (subject) de um token.
     */
    fun extractEmail(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    // --- Funções auxiliares ---

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    /**
     * Função genérica para extrair qualquer "claim" (dado) do token.
     */
    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    /**
     * Faz o parse do token e retorna todos os seus dados (claims).
     * Este método valida a assinatura. Se a assinatura for inválida, ele lança uma exceção.
     */
    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}

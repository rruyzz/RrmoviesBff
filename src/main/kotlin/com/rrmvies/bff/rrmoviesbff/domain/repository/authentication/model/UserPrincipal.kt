package com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.model

import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
    val id: Long?,
    private val username: String,
    val email: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun create(user: User): UserPrincipal {
            val authorities = user.roles.map {
                SimpleGrantedAuthority(it.name.name)
            }

            return UserPrincipal(
                user.id,
                user.username,
                user.email,
                user.password,
                authorities
            )
        }
    }
}
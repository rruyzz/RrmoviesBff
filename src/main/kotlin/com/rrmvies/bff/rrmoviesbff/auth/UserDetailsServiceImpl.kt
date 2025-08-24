package com.rrmvies.bff.rrmoviesbff.auth


import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.UserRepository
import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.model.UserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow {
                UsernameNotFoundException("User Not Found with username: $username")
            }

        return UserPrincipal.create(user)
    }
}
package com.rrmvies.bff.rrmoviesbff.domain.repository.authentication

import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.Role
import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(roleName: RoleName): Optional<Role>
}
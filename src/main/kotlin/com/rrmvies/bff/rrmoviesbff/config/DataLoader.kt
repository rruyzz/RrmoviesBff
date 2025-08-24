package com.rrmvies.bff.rrmoviesbff.config

import com.rrmvies.bff.rrmoviesbff.domain.repository.authentication.RoleRepository
import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.Role
import com.rrmvies.bff.rrmoviesbff.presentation.features.authentication.RoleName
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(private val roleRepository: RoleRepository) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(DataLoader::class.java)

    override fun run(vararg args: String?) {
        try {
            // Create roles if they don't exist
            if (roleRepository.findByName(RoleName.ROLE_USER).isEmpty) {
                roleRepository.save(Role(name = RoleName.ROLE_USER))
                logger.info("Role ROLE_USER created")
            }

            if (roleRepository.findByName(RoleName.ROLE_ADMIN).isEmpty) {
                roleRepository.save(Role(name = RoleName.ROLE_ADMIN))
                logger.info("Role ROLE_ADMIN created")
            }

            logger.info("DataLoader completed successfully")
        } catch (e: Exception) {
            logger.error("Error in DataLoader: ${e.message}", e)
        }
    }
}
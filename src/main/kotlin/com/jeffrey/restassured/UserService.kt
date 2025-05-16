package com.jeffrey.restassured

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService : UserUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun logout(userId: Long) {
        log.info("logout success: $userId")
    }
}
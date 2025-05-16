package com.jeffrey.restassured

import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user", name = "User")
class UserController(
    private val userUseCase: UserUseCase,
) {

    @PatchMapping(value = ["/logout/{userId}"], name = "logout")
    fun logout(
        @PathVariable userId: Long
    ): BaseResponse<Void> {
        userUseCase.logout(userId)
        return BaseResponse.ofSuccess()
    }

}
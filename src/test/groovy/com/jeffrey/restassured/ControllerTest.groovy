package com.jeffrey.restassured

import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@AutoConfigureRestDocs
abstract class ControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    WebApplicationContext context

    protected MockMvcRequestSpecification spec

    def setup() {

        spec = new MockMvcRequestSpecBuilder()
            .addHeader(
                HttpHeaders.AUTHORIZATION, "Bearer accessToken"
            )
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .setWebAppContextSetup(context)
            .setMockMvc(mockMvc)
            .build()

    }

}

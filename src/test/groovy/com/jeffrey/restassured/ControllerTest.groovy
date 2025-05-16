package com.jeffrey.restassured


import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*

@AutoConfigureRestDocs
abstract class ControllerTest extends Specification {

    @Autowired
    WebApplicationContext context

    @Autowired
    RestDocumentationContextProvider restDocumentation

    protected MockMvcRequestSpecification spec

    def setup() {
        spec = RestAssuredMockMvc
                .given()
                .headers(
                        HttpHeaders.AUTHORIZATION, "Bearer accessToken",
                        HttpHeaders.ACCEPT, ContentType.JSON,
                        HttpHeaders.CONTENT_TYPE, ContentType.JSON
                )
                .mockMvc(
                    MockMvcBuilders.webAppContextSetup(context)
                    .apply(
                        MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris()
                                .scheme("https")
                                .host("docs.api.com")
                                .removePort(),
                                // Temporary fix to make Content-Type appear only once since it's currently showing twice
                                // modifyHeaders().set(HttpHeaders.CONTENT_TYPE, ContentType.JSON as String),
                                prettyPrint()
                        )
                        .withResponseDefaults(prettyPrint())
                    )
                    .build())
                .log().all()
    }

}

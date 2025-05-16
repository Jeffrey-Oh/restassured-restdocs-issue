package com.jeffrey.restassured

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType

import static com.jeffrey.restassured.OperationPreprocessor.getDocumentRequest
import static com.jeffrey.restassured.OperationPreprocessor.getDocumentResponse
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters

@WebMvcTest(UserController)
class UserControllerSpec extends ControllerTest {

    @SpringBean
    UserUseCase userUseCase = Mock()

    def "logout"() {
        given:
        def userId = 1L
        userUseCase.logout(userId)

        expect:
        RestAssuredMockMvc
            .given()
            .spec(spec)
            .when()
                .patch("/api/user/logout/{userId}", userId)
            .then()
                .statusCode(200)
                .apply(
                    MockMvcRestDocumentation.document(
                        "user/logout",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                            parameterWithName("userId").description("userId")
                        ),
                        responseFields(
                            fieldWithPath("code").type(JsonFieldType.NUMBER).description("response code"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
                            fieldWithPath("data").type(JsonFieldType.NULL).description("response data")
                        )
                    )
                )
    }

}

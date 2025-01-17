package com.example.yota_pay.application.features.sequence_processing.integration

import com.example.yota_pay.application.features.sequence_processing.request.SequenceValidationRequest
import com.example.yota_pay.application.features.sequence_processing.response.SequenceValidationResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SequenceValidationIntegrationTests(
    @LocalServerPort private val port: Int,
) : BehaviorSpec({

    beforeSpec {
        RestAssured.requestSpecification = RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("http://localhost:$port")
            .build()
    }

    given("validate endpoint") {
        val endpoint = "/api/sequence/validate"

        `when`("valid DNA sequence is provided") {
            val request = SequenceValidationRequest("ATCG")
            
            then("should return valid true") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(SequenceValidationResponse::class.java)
                }

                response.valid shouldBe true
            }
        }

        `when`("invalid DNA sequence is provided") {
            val request = SequenceValidationRequest("ATCX")
            
            then("should return valid false") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(SequenceValidationResponse::class.java)
                }

                response.valid shouldBe false
            }
        }

        `when`("sequence contains invalid characters") {
            val request = SequenceValidationRequest("1234")
            
            then("should return valid false") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(SequenceValidationResponse::class.java)
                }

                response.valid shouldBe false
            }
        }

        `when`("sequence contains whitespace") {
            val request = SequenceValidationRequest("ATCG ")
            
            then("should return valid false") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(SequenceValidationResponse::class.java)
                }

                response.valid shouldBe false
            }
        }

        `when`("empty sequence is provided") {
            val request = SequenceValidationRequest("")
            
            then("should return bad request") {
                Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.BAD_REQUEST.value())
                }
            }
        }
    }
})

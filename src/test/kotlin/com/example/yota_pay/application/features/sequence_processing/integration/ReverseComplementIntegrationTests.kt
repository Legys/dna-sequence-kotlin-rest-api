package com.example.yota_pay.application.features.sequence_processing.integration

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError
import com.example.yota_pay.application.features.sequence_processing.request.ReverseComplementRequest
import com.example.yota_pay.application.features.sequence_processing.response.ReverseComplementResponse
import com.example.yota_pay.intrastructure.errors.ErrorResponse
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
class ReverseComplementIntegrationTests(
    @LocalServerPort private val port: Int,
) : BehaviorSpec({

    beforeSpec {
        RestAssured.requestSpecification = RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("http://localhost:$port")
            .build()
    }

    given("reverse-complement endpoint") {
        val endpoint = "/api/sequence/reverse-complement"

        `when`("calculating reverse complement for valid DNA sequence") {
            val request = ReverseComplementRequest("ATGC")
            
            then("should return correct reverse complement") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(ReverseComplementResponse::class.java)
                }

                response.sequence shouldBe "GCAT"
            }
        }

        `when`("calculating reverse complement for longer sequence") {
            val request = ReverseComplementRequest("AAAACCCGGT")
            
            then("should return correct reverse complement") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(ReverseComplementResponse::class.java)
                }

                response.sequence shouldBe "ACCGGGTTTT"
            }
        }

        `when`("calculating reverse complement with empty sequence") {
            val request = ReverseComplementRequest("")
            
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

        `when`("calculating reverse complement with invalid DNA sequence") {
            val request = ReverseComplementRequest("ATXGC")
            
            then("should return bad request") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.BAD_REQUEST.value())
                } Extract {
                    `as`(ErrorResponse::class.java)
                }

                response.errors.first().detail shouldBe SequenceError.InvalidDnaSequence().message
            }
        }

        `when`("calculating reverse complement with sequence containing whitespace") {
            val request = ReverseComplementRequest("ATGC GCTA")
            
            then("should return bad request") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.BAD_REQUEST.value())
                } Extract {
                    `as`(ErrorResponse::class.java)
                }

                response.errors.first().detail shouldBe SequenceError.InvalidDnaSequence().message
            }
        }

        `when`("calculating reverse complement with lowercase letters") {
            val request = ReverseComplementRequest("atgc")
            
            then("should return bad request") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.BAD_REQUEST.value())
                } Extract {
                    `as`(ErrorResponse::class.java)
                }

                response.errors.first().detail shouldBe SequenceError.InvalidDnaSequence().message
            }
        }
    }
})

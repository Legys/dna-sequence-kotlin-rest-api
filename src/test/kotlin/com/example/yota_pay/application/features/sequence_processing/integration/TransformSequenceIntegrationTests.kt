package com.example.yota_pay.application.features.sequence_processing.integration

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError
import com.example.yota_pay.application.features.sequence_processing.request.TransformSequenceRequest
import com.example.yota_pay.application.features.sequence_processing.response.TransformSequenceResponse
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
class TransformSequenceIntegrationTests(
    @LocalServerPort private val port: Int,
) : BehaviorSpec({

    beforeSpec {
        RestAssured.requestSpecification = RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("http://localhost:$port")
            .build()
    }

    given("transform endpoint") {
        val endpoint = "/api/sequence/transform"

        `when`("transforming valid DNA sequence to RNA") {
            val request = TransformSequenceRequest("ATGC")
            
            then("should return transformed RNA sequence") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(TransformSequenceResponse::class.java)
                }

                response.rnaSequence shouldBe "AUGC"
            }
        }

        `when`("transforming DNA sequence with multiple T nucleotides") {
            val request = TransformSequenceRequest("TTTACGT")
            
            then("should replace all T with U") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(TransformSequenceResponse::class.java)
                }

                response.rnaSequence shouldBe "UUUACGU"
            }
        }

        `when`("transforming with empty sequence") {
            val request = TransformSequenceRequest("")
            
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

                response.errors.first().detail shouldBe "Sequence must not be blank"
            }
        }

        `when`("transforming with invalid DNA sequence") {
            val request = TransformSequenceRequest("ATXGC")
            
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

package com.example.yota_pay.application.features.sequence_processing.integration

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError
import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import com.example.yota_pay.intrastructure.errors.ErrorResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.doubles.plusOrMinus
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
class GcContentIntegrationTests(
    @LocalServerPort private val port: Int,
) : BehaviorSpec({

    beforeSpec {
        RestAssured.requestSpecification = RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("http://localhost:$port")
            .build()
    }

    given("gc-content endpoint") {
        val endpoint = "/api/sequence/gc-content"

        `when`("calculating GC content for valid DNA sequence") {
            val request = GcContentRequest("ATGCGCTA")
            
            then("should return correct GC content") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(GcContentResponse::class.java)
                }

                response.gcContent shouldBe (0.5 plusOrMinus 0.001)
            }
        }

        `when`("calculating GC content for sequence with only GC bases") {
            val request = GcContentRequest("GCGCGC")
            
            then("should return GC content of 1.0") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(GcContentResponse::class.java)
                }

                response.gcContent shouldBe (1.0 plusOrMinus 0.001)
            }
        }

        `when`("calculating GC content for sequence with no GC bases") {
            val request = GcContentRequest("ATATAT")
            
            then("should return GC content of 0.0") {
                val response = Given {
                    body(request)
                } When {
                    post(endpoint)
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(GcContentResponse::class.java)
                }

                response.gcContent shouldBe (0.0 plusOrMinus 0.001)
            }
        }

        `when`("calculating GC content with empty sequence") {
            val request = GcContentRequest("")
            
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

        `when`("calculating GC content with invalid sequence") {
            val request = GcContentRequest("atgcgcta")
            
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

        `when`("calculating GC content with sequence containing whitespace") {
            val request = GcContentRequest("ATGC GCTA")
            
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

        `when`("calculating GC content with sequence containing invalid characters") {
            val request = GcContentRequest("ATGXGCTA")
            
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

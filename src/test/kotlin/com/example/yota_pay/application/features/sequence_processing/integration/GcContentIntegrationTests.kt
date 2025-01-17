package com.example.yota_pay.application.features.sequence_processing.integration

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError
import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import com.example.yota_pay.intrastructure.errors.ErrorResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GcContentIntegrationTests(
    @LocalServerPort val port: Int,
) : BehaviorSpec({

        beforeSpec {
            RestAssured.port = port
        }

        given("a valid DNA sequence request") {
            `when`("POST /api/sequence/gc-content is called") {
                val response =
                    Given {
                        contentType("application/json")
                        body(GcContentRequest("ATGCGCTA"))
                    } When {
                        post("/api/sequence/gc-content")
                    } Then {
                        statusCode(200)
                    } Extract {
                        `as`(GcContentResponse::class.java)
                    }

                then("should return correct GC content") {
                    response.gcContent shouldBe (0.5 plusOrMinus 0.001)
                }
            }
        }

        given("a sequence with only GC bases") {
            `when`("POST /api/sequence/gc-content is called") {
                val response =
                    Given {
                        contentType("application/json")
                        body(GcContentRequest("GCGCGC"))
                    } When {
                        post("/api/sequence/gc-content")
                    } Then {
                        statusCode(200)
                    } Extract {
                        `as`(GcContentResponse::class.java)
                    }

                then("should return GC content of 1.0") {
                    response.gcContent shouldBe (1.0 plusOrMinus 0.001)
                }
            }
        }

        given("a sequence with no GC bases") {
            `when`("POST /api/sequence/gc-content is called") {
                val response =
                    Given {
                        contentType("application/json")
                        body(GcContentRequest("ATATAT"))
                    } When {
                        post("/api/sequence/gc-content")
                    } Then {
                        statusCode(200)
                    } Extract {
                        `as`(GcContentResponse::class.java)
                    }

                then("should return GC content of 0.0") {
                    response.gcContent shouldBe (0.0 plusOrMinus 0.001)
                }
            }
        }

        given("an empty sequence request") {
            `when`("POST /api/sequence/gc-content is called") {
                Given {
                    contentType("application/json")
                    body(GcContentRequest(""))
                } When {
                    post("/api/sequence/gc-content")
                } Then {
                    statusCode(400)
                    body("errors[0].detail", equalTo("Sequence cannot be blank"))
                }
            }
        }

        given("an invalid sequence request") {
            `when`("sequence contains lowercase letters") {
                val response =
                    Given {
                        contentType("application/json")
                        body(GcContentRequest("atgcgcta"))
                    } When {
                        post("/api/sequence/gc-content")
                    } Then {
                        statusCode(400)
                    } Extract {
                        `as`(ErrorResponse::class.java)
                    }

                response.errors.first().detail shouldBeEqual SequenceError.InvalidDnaSequence().message
            }

            `when`("sequence contains whitespace") {
                val response =
                    Given {
                        contentType("application/json")
                        body(GcContentRequest("ATGC GCTA"))
                    } When {
                        post("/api/sequence/gc-content")
                    } Then {
                        statusCode(400)
                    } Extract {
                        `as`(ErrorResponse::class.java)
                    }

                response.errors.first().detail shouldBeEqual SequenceError.InvalidDnaSequence().message
            }

            `when`("sequence contains invalid characters") {
                val response =
                    Given {
                        contentType("application/json")
                        body(GcContentRequest("ATGXGCTA"))
                    } When {
                        post("/api/sequence/gc-content")
                    } Then {
                        statusCode(400)
                    } Extract {
                        `as`(ErrorResponse::class.java)
                    }
                response.errors.first().detail shouldBeEqual SequenceError.InvalidDnaSequence().message
            }
        }
    })

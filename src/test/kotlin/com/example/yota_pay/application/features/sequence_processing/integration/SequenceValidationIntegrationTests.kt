package com.example.yota_pay.application.features.sequence_processing.integration

import com.example.yota_pay.application.features.sequence_processing.request.SequenceValidationRequest
import com.example.yota_pay.application.features.sequence_processing.response.SequenceValidationResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SequenceValidationIntegrationTests(
    @LocalServerPort val port: Int,
) : BehaviorSpec({

        beforeSpec {
            RestAssured.port = port
        }

        given("a valid DNA sequence request") {
            `when`("POST /api/sequence/validate is called") {
                val response =
                    Given {
                        contentType("application/json")
                        body(SequenceValidationRequest("ATCG"))
                    } When {
                        post("/api/sequence/validate")
                    } Then {
                        statusCode(200)
                    } Extract {
                        `as`(SequenceValidationResponse::class.java)
                    }

                then("should return valid true") {
                    response.valid shouldBe true
                }
            }
        }

        given("an invalid DNA sequence request") {
            `when`("POST /api/sequence/validate is called") {
                val response =
                    Given {
                        contentType("application/json")
                        body(SequenceValidationRequest("ATCX"))
                    } When {
                        post("/api/sequence/validate")
                    } Then {
                        statusCode(200)
                    } Extract {
                        `as`(SequenceValidationResponse::class.java)
                    }

                then("should return valid false") {
                    response.valid shouldBe false
                }
            }
        }

        given("a request with invalid characters") {
            `when`("POST /api/sequence/validate is called") {
                val response =
                    Given {
                        contentType("application/json")
                        body(SequenceValidationRequest("1234"))
                    } When {
                        post("/api/sequence/validate")
                    } Then {
                        statusCode(200)
                    } Extract {
                        `as`(SequenceValidationResponse::class.java)
                    }

                then("should return valid false") {
                    response.valid shouldBe false
                }
            }
        }

        given("a request with whitespace") {
            `when`("POST /api/sequence/validate is called") {
                val response =
                    Given {
                        contentType("application/json")
                        body(SequenceValidationRequest("ATCG "))
                    } When {
                        post("/api/sequence/validate")
                    } Then {
                        statusCode(200)
                    } Extract {
                        `as`(SequenceValidationResponse::class.java)
                    }

                then("should return error") {
                    response.valid shouldBe false
                }
            }
        }

        given("an empty sequence request") {
            `when`("POST /api/sequence/validate is called") {
                Given {
                    contentType("application/json")
                    body(SequenceValidationRequest(""))
                } When {
                    post("/api/sequence/validate")
                } Then {
                    statusCode(400)
                }
            }
        }
    })

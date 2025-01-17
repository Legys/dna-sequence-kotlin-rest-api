package com.example.yota_pay.application.features.sequence_processing.integration

import com.example.yota_pay.application.features.sequence_processing.request.FindMotifRequest
import com.example.yota_pay.application.features.sequence_processing.response.FindMotifResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
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
class FindMotifIntegrationTests(
    @LocalServerPort private val port: Int,
) : BehaviorSpec({
        beforeSpec {
            RestAssured.requestSpecification =
                RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri("http://localhost:$port")
                    .build()
        }

        given("find-motif endpoint") {
            val endpoint = "/api/sequence/find-motif"

            `when`("searching for a valid motif in a valid sequence") {
                val request =
                    FindMotifRequest(
                        sequence = "ACGTACGT",
                        motif = "ACG",
                    )

                then("should return positions where motif is found") {
                    val response =
                        Given {
                            body(request)
                        } When {
                            post(endpoint)
                        } Then {
                            statusCode(HttpStatus.OK.value())
                        } Extract {
                            `as`(FindMotifResponse::class.java)
                        }

                    response.motifIndices shouldContainExactly listOf(0, 4)
                }
            }

            `when`("searching for a motif that doesn't exist") {
                val request =
                    FindMotifRequest(
                        sequence = "ACGTACGT",
                        motif = "TTT",
                    )

                then("should return empty list") {
                    val response =
                        Given {
                            body(request)
                        } When {
                            post(endpoint)
                        } Then {
                            statusCode(HttpStatus.OK.value())
                        } Extract
                            {
                                `as`(FindMotifResponse::class.java)
                            }

                    response.motifIndices.shouldBeEmpty()
                }
            }

            `when`("searching with invalid DNA sequence") {
                val request =
                    FindMotifRequest(
                        sequence = "ACGT123XYZ",
                        motif = "ACG",
                    )

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

            `when`("searching with invalid motif") {
                val request =
                    FindMotifRequest(
                        sequence = "ACGTACGT",
                        motif = "X123",
                    )

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

            `when`("searching with empty sequence") {
                val request =
                    FindMotifRequest(
                        sequence = "",
                        motif = "ACG",
                    )

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

            `when`("searching with empty motif") {
                val request =
                    FindMotifRequest(
                        sequence = "ACGTACGT",
                        motif = "",
                    )

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

            `when`("searching with motif longer than sequence") {
                val request =
                    FindMotifRequest(
                        sequence = "ACG",
                        motif = "ACGTACGT",
                    )

                then("should return empty list") {
                    val response =
                        Given {
                            body(request)
                        } When {
                            post(endpoint)
                        } Then {
                            statusCode(HttpStatus.OK.value())
                        } Extract
                            {
                                `as`(FindMotifResponse::class.java)
                            }

                    response.motifIndices.shouldBeEmpty()
                }
            }
        }
    }) 

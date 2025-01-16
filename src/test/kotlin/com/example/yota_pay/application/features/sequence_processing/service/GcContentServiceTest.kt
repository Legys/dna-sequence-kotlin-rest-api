package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class GcContentServiceTest : BehaviorSpec({

    val service = GcContentService()

    given("GcContentService") {
        context("with valid sequences") {
            `when`("sequence has mixed bases") {
                val response = service.calculateGcContent(GcContentRequest("ATGCGCTA"))
                then("should calculate correct GC content") {
                    response shouldBe GcContentResponse(0.5)
                }
            }

            `when`("sequence has only GC bases") {
                val response = service.calculateGcContent(GcContentRequest("GCGCGC"))
                then("should return 1.0 GC content") {
                    response shouldBe GcContentResponse(1.0)
                }
            }

            `when`("sequence has no GC bases") {
                val response = service.calculateGcContent(GcContentRequest("ATATAT"))
                then("should return 0.0 GC content") {
                    response shouldBe GcContentResponse(0.0)
                }
            }

            `when`("sequence is empty") {
                val response = service.calculateGcContent(GcContentRequest(""))
                then("should return 0.0 GC content") {
                    response shouldBe GcContentResponse(0.0)
                }
            }

            `when`("sequence has lowercase letters") {
                val response = service.calculateGcContent(GcContentRequest("atgcgcta"))
                then("should calculate correct GC content") {
                    response shouldBe GcContentResponse(0.5)
                }
            }
        }

        context("with potentially invalid sequences") {
            `when`("sequence contains whitespace") {
                val response = service.calculateGcContent(GcContentRequest("ATGC GCTA"))
                then("should ignore whitespace in calculation") {
                    response shouldBe GcContentResponse(0.5)
                }
            }

            `when`("sequence contains invalid characters") {
                val response = service.calculateGcContent(GcContentRequest("ATGXGCTA"))
                then("should ignore invalid characters in calculation") {
                    response shouldBe GcContentResponse(0.5)
                }
            }
        }
    }
})

package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.DnaSequence
import com.example.yota_pay.application.features.sequence_processing.domain.GcContentCalculator
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class GcContentCalculatorTest : BehaviorSpec({

    given("GcContentCalculator") {
        context("with valid sequences") {
            `when`("sequence has mixed bases") {
                val response = GcContentCalculator.calculateGcContent(DnaSequence("ATGCGCTA"))
                then("should calculate correct GC content") {
                    response shouldBe 0.5
                }
            }

            `when`("sequence has only GC bases") {
                val response = GcContentCalculator.calculateGcContent(DnaSequence("GCGCGC"))
                then("should return 1.0 GC content") {
                    response shouldBe 1.0
                }
            }

            `when`("sequence has no GC bases") {
                val response = GcContentCalculator.calculateGcContent(DnaSequence("ATATAT"))
                then("should return 0.0 GC content") {
                    response shouldBe 0.0
                }
            }

            `when`("sequence is empty") {
                val response = GcContentCalculator.calculateGcContent(DnaSequence(""))
                then("should return 0.0 GC content") {
                    response shouldBe 0.0
                }
            }

            `when`("sequence has lowercase letters") {
                val response = GcContentCalculator.calculateGcContent(DnaSequence("atgcgcta"))
                then("should calculate correct GC content") {
                    response shouldBe 0.5
                }
            }
        }

        context("with potentially invalid sequences") {
            `when`("sequence contains whitespace") {
                val response = GcContentCalculator.calculateGcContent(DnaSequence("ATGC GCTA"))
                then("should ignore whitespace in calculation") {
                    response shouldBe 0.5
                }
            }

            `when`("sequence contains invalid characters") {
                val response = GcContentCalculator.calculateGcContent(DnaSequence("ATGXGCTA"))
                then("should ignore invalid characters in calculation") {
                    response shouldBe 0.5
                }
            }
        }
    }
})

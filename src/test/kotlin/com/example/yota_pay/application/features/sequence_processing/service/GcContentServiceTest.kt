package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.DnaSequence
import com.example.yota_pay.application.features.sequence_processing.domain.GcContentCalculator
import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class GcContentCalculatorTest : BehaviorSpec({
    given("GcContentCalculator") {
        context("with valid sequences") {
            `when`("sequence has mixed bases") {
                val sequence = DnaSequence.create("ATGCGCTA").getOrThrow()
                val response = GcContentCalculator.calculateGcContent(sequence)
                then("should calculate correct GC content") {
                    response shouldBe 0.5
                }
            }

            `when`("sequence has only GC bases") {
                val sequence = DnaSequence.create("GCGCGC").getOrThrow()
                val response = GcContentCalculator.calculateGcContent(sequence)
                then("should return 1.0 GC content") {
                    response shouldBe 1.0
                }
            }

            `when`("sequence has no GC bases") {
                val sequence = DnaSequence.create("ATATAT").getOrThrow()
                val response = GcContentCalculator.calculateGcContent(sequence)
                then("should return 0.0 GC content") {
                    response shouldBe 0.0
                }
            }
        }
    }
})

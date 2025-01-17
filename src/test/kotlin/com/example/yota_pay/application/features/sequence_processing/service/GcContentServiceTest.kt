package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.dna.ContentOperation.calculateContent
import com.example.yota_pay.application.features.sequence_processing.domain.dna.DnaSequence
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class GcContentCalculatorTest :
    BehaviorSpec({
        given("GcContentCalculator") {
            context("with valid sequences") {
                `when`("sequence has mixed bases") {
                    val sequence = DnaSequence.create("ATGCGCTA").getOrThrow()
                    val response = sequence.calculateContent()
                    then("should calculate correct GC content") {
                        response shouldBe 0.5
                    }
                }

                `when`("sequence has only GC bases") {
                    val sequence = DnaSequence.create("GCGCGC").getOrThrow()
                    val response = sequence.calculateContent()
                    then("should return 1.0 GC content") {
                        response shouldBe 1.0
                    }
                }

                `when`("sequence has no GC bases") {
                    val sequence = DnaSequence.create("ATATAT").getOrThrow()
                    val response = sequence.calculateContent()
                    then("should return 0.0 GC content") {
                        response shouldBe 0.0
                    }
                }
            }
        }
    })

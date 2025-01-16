package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.DnaSequence
import com.example.yota_pay.application.features.sequence_processing.domain.GcContentCalculator
import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class GcContentCalculatorTest :
    BehaviorSpec({

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

                `when`("sequence is empty") {
                    val result = DnaSequence.create("")
                    then("should fail with EmptySequence error") {
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBeInstanceOf SequenceError.EmptySequence::class
                    }
                }

                `when`("sequence has lowercase letters") {
                    val result = DnaSequence.create("atgcgcta")
                    then("should fail with InvalidSequence error") {
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBeInstanceOf SequenceError.InvalidSequence::class
                    }
                }
            }

            context("with potentially invalid sequences") {
                `when`("sequence contains whitespace") {
                    val result = DnaSequence.create("ATGC GCTA")
                    then("should fail with InvalidSequence error") {
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBeInstanceOf SequenceError.InvalidSequence::class
                    }
                }

                `when`("sequence contains invalid characters") {
                    val result = DnaSequence.create("ATGXGCTA")
                    then("should fail with InvalidSequence error") {
                        result.isFailure shouldBe true
                        result.exceptionOrNull().shouldBeInstanceOf<SequenceError.InvalidSequence>()
                    }
                }
            }
        }
    })

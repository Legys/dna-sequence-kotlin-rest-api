package com.example.yota_pay.application.features.sequence_processing.domain.dna

import com.example.yota_pay.application.features.sequence_processing.domain.dna.MotifSearchOperation.findMotif
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe

class MotifSearchOperationTests :
    BehaviorSpec({
        given("a DNA sequence") {
            `when`("searching for a motif that exists once") {
                then("should return the correct index") {
                    val sequence = DnaSequence.create("ACGTACGT").getOrThrow()
                    sequence.findMotif("ACG") shouldBeSuccess listOf(0, 4)
                }
            }

            `when`("searching for a motif that doesn't exist") {
                then("should return empty list") {
                    val sequence = DnaSequence.create("ACGTACGT").getOrThrow()
                    sequence.findMotif("TTT") shouldBeSuccess emptyList()
                }
            }

            `when`("searching for a motif longer than the sequence") {
                then("should return empty list") {
                    val sequence = DnaSequence.create("ACG").getOrThrow()
                    sequence.findMotif("ACGTACGT") shouldBeSuccess emptyList()
                }
            }

            `when`("searching with an invalid motif") {
                then("should return failure") {
                    val sequence = DnaSequence.create("ACGTACGT").getOrThrow()
                    sequence.findMotif("X123").isFailure shouldBe true
                }
            }

            `when`("searching for overlapping motifs") {
                then("should find all occurrences") {
                    val sequence = DnaSequence.create("AAAAA").getOrThrow()
                    sequence.findMotif("AA") shouldBeSuccess listOf(0, 1, 2, 3)
                }
            }
        }
    }) 

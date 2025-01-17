package com.example.yota_pay.application.features.sequence_processing.domain.dna

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe

class DnaSequenceTests :
    BehaviorSpec({

        given("a DNA sequence") {
            `when`("creating with valid sequence") {
                then("it should return success") {
                    DnaSequence.create("ATCG").shouldBeSuccess()
                    DnaSequence.create("GCTA").shouldBeSuccess()
                    DnaSequence.create("AAAA").shouldBeSuccess()
                    DnaSequence.create("T").shouldBeSuccess()
                    DnaSequence.create("ATCGATCGATCG").shouldBeSuccess()
                }
            }

            `when`("creating with invalid sequence") {
                then("it should return failure") {
                    DnaSequence.create("ATCX").shouldBeFailure()
                    DnaSequence.create("1234").shouldBeFailure()
                    DnaSequence.create("ATCG ").shouldBeFailure()
                    DnaSequence.create("").shouldBeFailure()
                    DnaSequence.create("ATCG\n").shouldBeFailure()
                    DnaSequence.create("ATCG\t").shouldBeFailure()
                    DnaSequence.create("ATCGATCGATCGX").shouldBeFailure()
                    DnaSequence.create("ATGC GCTA").shouldBeFailure()
                    DnaSequence.create("atgcgcta").shouldBeFailure()
                }
            }

            `when`("converting to string") {
                then("it should return the sequence value") {
                    DnaSequence
                        .create("ATCG")
                        .getOrThrow()
                        .toString() shouldBe "ATCG"
                }
            }
        }
    })

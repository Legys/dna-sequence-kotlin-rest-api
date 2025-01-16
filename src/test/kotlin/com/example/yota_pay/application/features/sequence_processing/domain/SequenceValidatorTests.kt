package com.example.yota_pay.application.features.sequence_processing.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SequenceValidatorTests :
    BehaviorSpec({

        val validator = SequenceValidator::validateSequence

        given("a valid DNA sequence") {
            `when`("validating the sequence") {
                then("it should return true") {
                    validator("ATCG") shouldBe true
                    validator("GCTA") shouldBe true
                    validator("AAAA") shouldBe true
                    validator("T") shouldBe true // single character
                    validator("ATCGATCGATCG") shouldBe true // repeated pattern
                }
            }
        }

        given("an invalid DNA sequence") {
            `when`("validating the sequence") {
                then("it should return false") {
                    validator("ATCX") shouldBe false
                    validator("1234") shouldBe false
                    validator("ATCG ") shouldBe false
                    validator("") shouldBe false
                    validator("ATCG\n") shouldBe false // newline character
                    validator("ATCG\t") shouldBe false // tab character
                    validator("ATCGATCGATCGX") shouldBe false // invalid character at end
                    validator("ATGC GCTA") shouldBe false // whitespace
                    validator("atgcgcta") shouldBe false
                }
            }
        }
    })

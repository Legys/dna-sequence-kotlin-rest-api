package com.example.yota_pay.application.features.sequence_processing.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SequenceValidatorTests :
    BehaviorSpec({

        val validator = SequenceValidator::validateSequence

        given("a valid DNA sequence") {
            `when`("validating the sequence") {
                then("it should return true") {
                    validator("ATCG").isSuccess shouldBe true
                    validator("GCTA").isSuccess shouldBe true
                    validator("AAAA").isSuccess shouldBe true
                    validator("T").isSuccess shouldBe true // single character
                    validator("ATCGATCGATCG").isSuccess shouldBe true // repeated pattern
                }
            }
        }

        given("an invalid DNA sequence") {
            `when`("validating the sequence") {
                then("it should return false") {
                    validator("ATCX").isSuccess shouldBe false
                    validator("1234").isSuccess shouldBe false
                    validator("ATCG ").isSuccess shouldBe false
                    validator("").isSuccess shouldBe false
                    validator("ATCG\n").isSuccess shouldBe false // newline character
                    validator("ATCG\t").isSuccess shouldBe false // tab character
                    validator("ATCGATCGATCGX").isSuccess shouldBe false // invalid character at end
                    validator("ATGC GCTA").isSuccess shouldBe false // whitespace
                    validator("atgcgcta").isSuccess shouldBe false
                }
            }
        }
    })

package com.example.yota_pay.application.features.sequence_processing.services

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class SequenceValidationServiceTest : BehaviorSpec({

    val service = SequenceValidationService()

    given("a valid DNA sequence") {
        `when`("validating the sequence") {
            then("it should return true") {
                service.validateSequence("ATCG").shouldBeTrue()
                service.validateSequence("GCTA").shouldBeTrue()
                service.validateSequence("AAAA").shouldBeTrue()
            }
        }
    }

    given("an invalid DNA sequence") {
        `when`("validating the sequence") {
            then("it should return false") {
                service.validateSequence("ATCX").shouldBeFalse()
                service.validateSequence("1234").shouldBeFalse()
                service.validateSequence("ATCG ").shouldBeFalse()
                service.validateSequence("").shouldBeFalse()
            }
        }
    }
})

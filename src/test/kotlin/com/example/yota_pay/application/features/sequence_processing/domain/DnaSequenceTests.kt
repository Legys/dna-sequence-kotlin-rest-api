package com.example.yota_pay.application.features.sequence_processing.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class DnaSequenceTests : BehaviorSpec({

    given("a DNA sequence") {
        `when`("creating with valid sequence") {
            then("it should return success") {
                DnaSequence.create("ATCG").isSuccess shouldBe true
                DnaSequence.create("GCTA").isSuccess shouldBe true
                DnaSequence.create("AAAA").isSuccess shouldBe true 
                DnaSequence.create("T").isSuccess shouldBe true
                DnaSequence.create("ATCGATCGATCG").isSuccess shouldBe true
            }
        }

        `when`("creating with invalid sequence") {
            then("it should return failure") {
                DnaSequence.create("ATCX").isSuccess shouldBe false
                DnaSequence.create("1234").isSuccess shouldBe false
                DnaSequence.create("ATCG ").isSuccess shouldBe false
                DnaSequence.create("").isSuccess shouldBe false
                DnaSequence.create("ATCG\n").isSuccess shouldBe false
                DnaSequence.create("ATCG\t").isSuccess shouldBe false
                DnaSequence.create("ATCGATCGATCGX").isSuccess shouldBe false
                DnaSequence.create("ATGC GCTA").isSuccess shouldBe false
                DnaSequence.create("atgcgcta").isSuccess shouldBe false
            }
        }

        `when`("creating reverse complement") {
            then("it should return correct sequence") {
                val sequence = DnaSequence.create("ATCG").getOrThrow()
                sequence.reverseComplement().value shouldBe "CGAT"
                
                DnaSequence.create("GCTA").getOrThrow()
                    .reverseComplement().value shouldBe "TAGC"
                    
                DnaSequence.create("AAAA").getOrThrow()
                    .reverseComplement().value shouldBe "TTTT"
            }
        }

        `when`("converting to string") {
            then("it should return the sequence value") {
                DnaSequence.create("ATCG").getOrThrow()
                    .toString() shouldBe "ATCG"
            }
        }
    }
})

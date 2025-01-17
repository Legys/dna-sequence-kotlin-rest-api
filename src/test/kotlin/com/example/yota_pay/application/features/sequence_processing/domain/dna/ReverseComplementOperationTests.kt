package com.example.yota_pay.application.features.sequence_processing.domain.dna

import com.example.yota_pay.application.features.sequence_processing.domain.dna.ReverseComplementOperation.reverseComplement
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess

class ReverseComplementOperationTests :
    BehaviorSpec({
        given("a DNA sequence") {
            `when`("calculating reverse complement of a simple sequence") {
                then("should return correct reverse complement") {
                    val sequence = DnaSequence.create("AAAACCCGGT").getOrThrow()
                    sequence.reverseComplement() shouldBeSuccess DnaSequence.create("ACCGGGTTTT").getOrThrow()
                }
            }

            `when`("calculating reverse complement of a palindromic sequence") {
                then("should return the same sequence") {
                    val sequence = DnaSequence.create("GCATGC").getOrThrow()
                    sequence.reverseComplement() shouldBeSuccess DnaSequence.create("GCATGC").getOrThrow()
                }
            }

            `when`("calculating reverse complement of a single nucleotide") {
                then("should return its complement") {
                    val sequence = DnaSequence.create("A").getOrThrow()
                    sequence.reverseComplement() shouldBeSuccess DnaSequence.create("T").getOrThrow()
                }
            }

            `when`("calculating reverse complement of alternating sequence") {
                then("should correctly handle alternating pattern") {
                    val sequence = DnaSequence.create("ATATATCGCG").getOrThrow()
                    sequence.reverseComplement() shouldBeSuccess DnaSequence.create("CGCGATATAT").getOrThrow()
                }
            }

            `when`("calculating reverse complement of all possible nucleotides") {
                then("should correctly complement each nucleotide") {
                    val sequence = DnaSequence.create("ATCG").getOrThrow()
                    sequence.reverseComplement() shouldBeSuccess DnaSequence.create("CGAT").getOrThrow()
                }
            }
        }
    }) 

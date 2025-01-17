package com.example.yota_pay.application.features.sequence_processing.domain.dna

import com.example.yota_pay.application.features.sequence_processing.domain.dna.TransformOperation.toRna
import com.example.yota_pay.application.features.sequence_processing.domain.rna.RnaSequence
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess

class TransformOperationTests :
    BehaviorSpec({
        given("a DNA sequence") {
            `when`("transforming sequence with all nucleotides") {
                then("should correctly replace T with U") {
                    val sequence = DnaSequence.create("GCTA").getOrThrow()
                    sequence.toRna() shouldBeSuccess RnaSequence.create("GCUA").getOrThrow()
                }
            }

            `when`("transforming sequence without thymine") {
                then("should return same sequence") {
                    val sequence = DnaSequence.create("GCAGC").getOrThrow()
                    sequence.toRna() shouldBeSuccess RnaSequence.create("GCAGC").getOrThrow()
                }
            }

            `when`("transforming sequence with only thymine") {
                then("should replace all T with U") {
                    val sequence = DnaSequence.create("TTTT").getOrThrow()
                    sequence.toRna() shouldBeSuccess RnaSequence.create("UUUU").getOrThrow()
                }
            }

            `when`("transforming sequence with alternating thymine") {
                then("should correctly replace alternating T with U") {
                    val sequence = DnaSequence.create("ATATCGTG").getOrThrow()
                    sequence.toRna() shouldBeSuccess RnaSequence.create("AUAUCGUG").getOrThrow()
                }
            }

            `when`("transforming long sequence") {
                then("should handle multiple T replacements") {
                    val sequence = DnaSequence.create("GCTAACGTTTACG").getOrThrow()
                    sequence.toRna() shouldBeSuccess RnaSequence.create("GCUAACGUUUACG").getOrThrow()
                }
            }
        }
    }) 

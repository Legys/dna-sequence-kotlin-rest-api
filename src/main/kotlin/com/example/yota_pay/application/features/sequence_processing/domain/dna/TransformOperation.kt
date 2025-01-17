package com.example.yota_pay.application.features.sequence_processing.domain.dna

import com.example.yota_pay.application.features.sequence_processing.domain.rna.RnaSequence

object TransformOperation {
    /**
     * Converts a DNA sequence to its corresponding RNA sequence.
     *
     * In molecular biology, transcription is the process of converting DNA to RNA:
     * - All nucleotides remain the same except Thymine (T) which is replaced with Uracil (U)
     *
     * For example:
     * DNA: 5'-GCTA-3'
     * RNA: 5'-GCUA-3'
     *
     * @return Result containing the transcribed RNA sequence if valid
     */
    fun DnaSequence.toRna(): Result<RnaSequence> {
        val transformed = value.replace('T', 'U')
        return RnaSequence.create(transformed)
    }
} 

package com.example.yota_pay.application.features.sequence_processing.domain.dna

/**
 * Operation for DNA sequence complement calculations.
 */
object ComplementOperation {
    private val complementMap =
        mapOf(
            'A' to 'T',
            'T' to 'A',
            'C' to 'G',
            'G' to 'C',
        )

    fun calculateReverse(sequence: DnaSequence): DnaSequence =
        DnaSequence
            .create(
                sequence.value
                    .reversed()
                    .map { complementMap[it] }
                    .joinToString(""),
            ).getOrThrow()
} 

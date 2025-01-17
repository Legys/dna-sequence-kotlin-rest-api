package com.example.yota_pay.application.features.sequence_processing.domain.dna

/**
 * Operation for DNA sequence complement calculations.
 */
object ReverseComplementOperation {
    private val complementMap =
        mapOf(
            'A' to 'T',
            'T' to 'A',
            'C' to 'G',
            'G' to 'C',
        )

    /**
     * Calculates the reverse complement of a DNA sequence.
     *
     * In molecular biology, a reverse complement is created by:
     * 1. Reversing the sequence
     * 2. Replacing each nucleotide with its complement (A<->T, C<->G)
     *
     * For example:
     * Original:  5'-AAAACCCGGT-3'
     * Reversed:  3'-TGGCCCAAAA-5'
     *
     * @param sequence The DNA sequence to find the reverse complement for
     * @return A new DnaSequence containing the reverse complement
     * @throws IllegalStateException if the complement mapping fails (should never happen for valid DnaSequence)
     */
    fun DnaSequence.reverseComplement(): Result<DnaSequence> =
        DnaSequence.create(
            value
                .reversed()
                .map { complementMap[it] }
                .joinToString(""),
        )
} 

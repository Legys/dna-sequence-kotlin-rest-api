package com.example.yota_pay.application.features.sequence_processing.domain.dna

/**
 * Operation for calculating nucleotide content in DNA sequences.
 *
 * In DNA theory, the characters G (Guanine) and C (Cytosine) are often referred to as
 * GC content when discussing their combined presence in a DNA sequence.
 * The GC content is a measure of the proportion of these nucleotides in
 * a DNA molecule, which can be significant for various biological processes and properties.
 */
object ContentOperation {
    val gcSet = setOf('G', 'C')

    /**
     * Calculates the proportion of specified nucleotides in a DNA sequence.
     *
     * @param nucleotides Set of nucleotide characters to count (e.g. setOf('G', 'C') for GC content)
     * @return The proportion of specified nucleotides as a decimal between 0.0 and 1.0
     *         Returns 0.0 if sequence is empty
     */
    fun DnaSequence.calculateContent(nucleotides: Set<Char>): Double {
        val nucleotidesCount = value.count { it in nucleotides }
        val totalLength = value.length
        return if (totalLength > 0) nucleotidesCount.toDouble() / totalLength else 0.0
    }
} 

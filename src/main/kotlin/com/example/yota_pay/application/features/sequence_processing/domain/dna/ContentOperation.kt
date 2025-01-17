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

    fun DnaSequence.calculateContent(nucleotides: Set<Char>): Double {
        val nucleotidesCount = value.count { it in nucleotides }
        val totalLength = value.length
        return if (totalLength > 0) nucleotidesCount.toDouble() / totalLength else 0.0
    }
} 

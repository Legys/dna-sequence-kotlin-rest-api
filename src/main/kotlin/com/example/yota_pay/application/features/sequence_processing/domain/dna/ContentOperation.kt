package com.example.yota_pay.application.features.sequence_processing.domain.dna

/**
 * Operation for calculating motif occurrences in DNA sequences.
 */
object ContentOperation {
    val gcSet = setOf('G', 'C')

    fun DnaSequence.calculateContent(nucleotides: Set<Char>): Double {
        val nucleotidesCount = value.count { it in nucleotides }
        val totalLength = value.length
        return if (totalLength > 0) nucleotidesCount.toDouble() / totalLength else 0.0
    }
} 

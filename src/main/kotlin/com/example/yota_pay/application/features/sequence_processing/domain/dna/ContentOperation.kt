package com.example.yota_pay.application.features.sequence_processing.domain.dna

/**
 * Operation for calculating motif occurrences in DNA sequences.
 */
object ContentOperation {
    fun DnaSequence.calculateContent(): Double {
        val gcCount = value.count { it == 'G' || it == 'C' }
        val totalLength = value.length
        return if (totalLength > 0) gcCount.toDouble() / totalLength else 0.0
    }
} 

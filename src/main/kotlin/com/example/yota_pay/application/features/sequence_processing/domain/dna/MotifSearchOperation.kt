package com.example.yota_pay.application.features.sequence_processing.domain.dna

import com.example.yota_pay.application.features.sequence_processing.domain.dna.DnaSequence.Companion.create

/**
 * Operation for finding motif patterns in DNA sequences.
 */
object MotifSearchOperation {
    /**
     * Finds all occurrences of the given motif in this sequence.
     * Returns indices of where the motif starts in the sequence (0-based).
     */
    fun DnaSequence.findMotif(motif: String): Result<List<Int>> =
        create(motif).map { _ ->
            value
                .windowedIndices(motif.length)
                .filter { i -> value.substring(i, i + motif.length) == motif }
        }

    private fun String.windowedIndices(windowSize: Int): IntRange = 0..this.length - windowSize
} 

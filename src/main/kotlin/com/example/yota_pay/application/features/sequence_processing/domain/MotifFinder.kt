package com.example.yota_pay.application.features.sequence_processing.domain

/**
 * Service for finding motif patterns in DNA sequences.
 * Implements pure functions for pattern matching operations.
 */
object MotifFinder {
    /**
     * Finds all occurrences of the given motif in this sequence.
     * Returns indices of where the motif starts in the sequence (0-based).
     *
     * @param motif The pattern to search for
     * @return Result containing list of indices where motif occurs or error if motif is invalid
     */
    fun DnaSequence.findMotif(motif: String): Result<List<Int>> =
        DnaSequence.create(motif).map { validMotif ->
            this.value
                .windowedIndices(motif.length)
                .filter { i -> this.value.substring(i, i + motif.length) == motif }
        }

    private fun String.windowedIndices(windowSize: Int): IntRange = 0..this.length - windowSize
} 

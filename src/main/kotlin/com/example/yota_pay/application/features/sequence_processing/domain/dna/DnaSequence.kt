package com.example.yota_pay.application.features.sequence_processing.domain.dna

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError

/**
 * Represents a valid DNA sequence.
 * Ensures sequence contains only valid nucleotides (A, T, C, G).
 */
@JvmInline
value class DnaSequence private constructor(
    val value: String,
) {
    companion object {
        fun create(value: String): Result<DnaSequence> =
            when {
                isEmpty(value) -> Result.failure(SequenceError.EmptySequence())
                containsInvalidCharacters(value) ->
                    Result.failure(SequenceError.InvalidSequence())

                else -> Result.success(DnaSequence(value))
            }

        private fun isEmpty(value: String): Boolean = value.isEmpty()

        private fun containsInvalidCharacters(value: String): Boolean = !value.all { it in setOf('A', 'T', 'C', 'G') }
    }

    override fun toString(): String = value
}

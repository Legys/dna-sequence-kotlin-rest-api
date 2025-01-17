package com.example.yota_pay.application.features.sequence_processing.domain.rna

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError

/**
 * Represents a validated RNA sequence containing only A, C, G, U characters.
 *
 * Uses a factory method pattern with Result type for type-safe initialization:
 * - Constructor is private to enforce validation
 * - create() method returns Result<RnaSequence> instead of throwing exceptions
 * - Ensures all instances are valid by construction
 * - Provides clear error handling through Result type
 * - Maintains immutability and value semantics
 */
@JvmInline
value class RnaSequence private constructor(
    val value: String,
) {
    companion object {
        fun create(value: String): Result<RnaSequence> =
            when {
                isEmpty(value) -> Result.failure(SequenceError.EmptySequence())
                containsInvalidCharacters(value) ->
                    Result.failure(SequenceError.InvalidSequence())

                else -> Result.success(RnaSequence(value))
            }

        private fun isEmpty(value: String): Boolean = value.isEmpty()

        private fun containsInvalidCharacters(value: String): Boolean = !value.all { it in setOf('A', 'U', 'C', 'G') }
    }

    override fun toString(): String = value
} 

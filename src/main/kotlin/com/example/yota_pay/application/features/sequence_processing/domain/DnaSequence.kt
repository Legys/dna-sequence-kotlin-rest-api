package com.example.yota_pay.application.features.sequence_processing.domain

/**
 * Represents a validated DNA sequence containing only A, C, G, T characters.
 *
 * Uses a factory method pattern with Result type for type-safe initialization:
 * - Constructor is private to enforce validation
 * - create() method returns Result<DnaSequence> instead of throwing exceptions
 * - Ensures all instances are valid by construction
 * - Provides clear error handling through Result type
 * - Maintains immutability and value semantics
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

        private fun containsInvalidCharacters(value: String): Boolean =
            !value.all { it in setOf('A', 'T', 'C', 'G') }
    }

    fun reverseComplement(): DnaSequence {
        val complement = mapOf('A' to 'T', 'T' to 'A', 'C' to 'G', 'G' to 'C')
        return DnaSequence(value.reversed().map { complement[it] }.joinToString(""))
    }

    override fun toString(): String = value
}

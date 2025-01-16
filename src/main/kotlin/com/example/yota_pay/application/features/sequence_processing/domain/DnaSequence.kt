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
        fun create(value: String): Result<DnaSequence> {
            return if (value.all { it in "ACGT" }) {
                Result.success(DnaSequence(value))
            } else {
                Result.failure(SequenceError.InvalidSequence(value))
            }
        }
    }

    fun reverseComplement(): DnaSequence {
        val complement = mapOf('A' to 'T', 'T' to 'A', 'C' to 'G', 'G' to 'C')
        return DnaSequence(value.reversed().map { complement[it] }.joinToString(""))
    }

    override fun toString(): String = value
}

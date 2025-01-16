package com.example.yota_pay.application.features.sequence_processing.domain

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

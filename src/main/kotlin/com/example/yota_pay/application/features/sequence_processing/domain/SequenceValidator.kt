package com.example.yota_pay.application.features.sequence_processing.domain

object SequenceValidator {
    fun validateSequence(sequence: String): Result<DnaSequence> =
        when {
            sequence.isEmpty() -> Result.failure(SequenceError.EmptySequence("Sequence cannot be empty"))
            !sequence.all { it in setOf('A', 'T', 'C', 'G') } ->
                Result.failure(SequenceError.InvalidSequence("Sequence contains invalid characters"))

            else -> Result.success(DnaSequence(sequence))
        }
}

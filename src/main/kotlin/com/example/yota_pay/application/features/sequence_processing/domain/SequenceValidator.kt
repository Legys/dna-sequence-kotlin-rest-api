package com.example.yota_pay.application.features.sequence_processing.domain

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError.*

object SequenceValidator {
    fun validateSequence(sequence: String): Result<String> {
        return when {
            sequence.isEmpty() -> Result.failure(EmptySequence("Sequence cannot be empty"))
            !sequence.all { it in setOf('A', 'T', 'C', 'G') } ->
                Result.failure(InvalidSequence("Sequence contains invalid characters"))
            else -> Result.success(sequence)
        }
    }
}

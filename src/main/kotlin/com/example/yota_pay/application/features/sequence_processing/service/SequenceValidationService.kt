package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceError
import org.springframework.stereotype.Service

@Service
class SequenceValidationService {
    fun validateSequence(sequence: String): Result<String> =
        when {
            sequence.isEmpty() -> Result.failure(SequenceError.EmptySequence("Sequence cannot be empty"))
            !sequence.all { it in setOf('A', 'T', 'C', 'G') } ->
                Result.failure(SequenceError.InvalidSequence("Sequence contains invalid characters"))

            else -> Result.success(sequence)
        }
}

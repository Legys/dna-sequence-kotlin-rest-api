package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.SequenceValidator
import org.springframework.stereotype.Service

@Service
class SequenceValidationService {
    fun validateSequence(sequence: String) = SequenceValidator.validateSequence(sequence)
}

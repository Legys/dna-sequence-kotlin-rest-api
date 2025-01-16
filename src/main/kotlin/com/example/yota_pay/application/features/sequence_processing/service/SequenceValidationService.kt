package com.example.yota_pay.application.features.sequence_processing.service

import org.springframework.stereotype.Service

@Service
class SequenceValidationService {
    fun validateSequence(sequence: String): Boolean {
        val validCharacters = setOf('A', 'T', 'C', 'G')
        return sequence.all { it in validCharacters }
    }
}

package com.example.yota_pay.application.features.sequence_processing.intrastructure.errors

sealed class SequenceError {
    data class InvalidSequence(val message: String) : SequenceError()
    data class EmptySequence(val message: String) : SequenceError()
    data class UnknownError(val message: String) : SequenceError()
}

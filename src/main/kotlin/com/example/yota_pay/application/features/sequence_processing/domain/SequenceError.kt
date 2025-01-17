package com.example.yota_pay.application.features.sequence_processing.domain

sealed class SequenceError(
    message: String,
) : Throwable(message) {
    data class InvalidSequence(
        override val message: String = "DNA sequence can only contain A, T, C, G characters"
    ) : SequenceError(message)

    data class EmptySequence(
        override val message: String = "DNA sequence cannot be empty"
    ) : SequenceError(message)

    data class UnknownError(
        override val message: String = "Unknown error occurred"
    ) : SequenceError(message)
}

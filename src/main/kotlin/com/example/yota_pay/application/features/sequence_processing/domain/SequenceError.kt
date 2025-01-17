package com.example.yota_pay.application.features.sequence_processing.domain

sealed class SequenceError(
    message: String,
) : Throwable(message) {
    data class InvalidDnaSequence(
        override val message: String = "DNA sequence can only contain A, T, C, G characters",
    ) : SequenceError(message)

    data class InvalidRnaSequence(
        override val message: String = "RNA sequence can only contain A, U, C, G characters",
    ) : SequenceError(message)

    data class EmptySequence(
        override val message: String = "Sequence cannot be empty",
    ) : SequenceError(message)

    data class UnknownError(
        override val message: String = "Unknown error occurred",
    ) : SequenceError(message)
}

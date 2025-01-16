package com.example.yota_pay.application.features.sequence_processing.domain

sealed class SequenceError(
    message: String,
) : Throwable(message) {
    data class InvalidSequence(
        override val message: String,
    ) : SequenceError(message)

    data class EmptySequence(
        override val message: String,
    ) : SequenceError(message)

    data class UnknownError(
        override val message: String,
    ) : SequenceError(message)
}

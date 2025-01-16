package com.example.yota_pay.application.features.sequence_processing.response

data class SequenceValidationResponse(
    val valid: Boolean,
    val message: String,
)

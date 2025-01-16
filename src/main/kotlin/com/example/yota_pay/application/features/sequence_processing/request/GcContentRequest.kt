package com.example.yota_pay.application.features.sequence_processing.request

import jakarta.validation.constraints.NotBlank

data class GcContentRequest(
    @field:NotBlank(message = "Sequence cannot be blank")
    val sequence: String,
)

package com.example.yota_pay.application.features.sequence_processing.request

import jakarta.validation.constraints.NotBlank

data class ReverseComplementRequest(
    @field:NotBlank(message = "Sequence must not be blank")
    val sequence: String
) 
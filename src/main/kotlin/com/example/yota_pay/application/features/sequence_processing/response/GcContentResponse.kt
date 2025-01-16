package com.example.yota_pay.application.features.sequence_processing.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GcContentResponse(
    @field:JsonProperty("gc_content")
    val gcContent: Double,
)

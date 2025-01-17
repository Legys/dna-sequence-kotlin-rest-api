package com.example.yota_pay.application.features.sequence_processing.response

import com.fasterxml.jackson.annotation.JsonProperty

data class FindMotifResponse(
    @field:JsonProperty("motif_indices")
    val motifIndices: List<Int>,
) 

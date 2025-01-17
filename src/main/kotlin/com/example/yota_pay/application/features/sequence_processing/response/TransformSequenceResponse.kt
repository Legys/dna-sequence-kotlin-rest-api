package com.example.yota_pay.application.features.sequence_processing.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TransformSequenceResponse(
    @field:JsonProperty("rna_sequence")
    val rnaSequence: String,
) 

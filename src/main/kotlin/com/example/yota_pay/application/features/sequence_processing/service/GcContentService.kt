package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.dna.ContentOperation.calculateContent
import com.example.yota_pay.application.features.sequence_processing.domain.dna.DnaSequence
import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import org.springframework.stereotype.Service

@Service
class GcContentService {
    fun calculateGcContent(request: GcContentRequest): Result<GcContentResponse> =
        DnaSequence
            .create(request.sequence)
            .map { validSequence ->
                val gcContent = validSequence.calculateContent()
                GcContentResponse(gcContent)
            }
}

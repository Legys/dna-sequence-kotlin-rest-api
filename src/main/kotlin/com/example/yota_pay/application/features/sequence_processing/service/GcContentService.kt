package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.DnaSequence
import com.example.yota_pay.application.features.sequence_processing.domain.GcContentCalculator
import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import org.springframework.stereotype.Service

@Service
class GcContentService {
    fun calculateGcContent(request: GcContentRequest): Result<GcContentResponse> =
        DnaSequence
            .create(request.sequence)
            .map { validSequence ->
                val gcContent = GcContentCalculator.calculateGcContent(validSequence)
                GcContentResponse(gcContent)
            }
}

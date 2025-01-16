package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.GcContentCalculator
import com.example.yota_pay.application.features.sequence_processing.domain.SequenceValidator
import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import org.springframework.stereotype.Service

@Service
class GcContentService {
    fun calculateGcContent(request: GcContentRequest): Result<GcContentResponse> {
        return SequenceValidator.validateSequence(request.sequence)
            .mapCatching { validSequence ->
                val gcContent = GcContentCalculator.calculateGcContent(validSequence)
                GcContentResponse(gcContent)
            }
    }
}

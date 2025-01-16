package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import org.springframework.stereotype.Service

@Service
class GcContentService(
    private val sequenceValidationService: SequenceValidationService
) {
    fun calculateGcContent(request: GcContentRequest): Result<GcContentResponse> {
        return sequenceValidationService.validateSequence(request.sequence)
            .mapCatching { validSequence ->
                val gcCount = validSequence.count { it == 'G' || it == 'C' }
                val totalLength = validSequence.length
                val gcContent = if (totalLength > 0) gcCount.toDouble() / totalLength else 0.0
                GcContentResponse(gcContent)
            }
    }
}

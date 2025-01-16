package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import org.springframework.stereotype.Service

@Service
class GcContentService(
    private val sequenceValidationService: SequenceValidationService,
) {
    fun calculateGcContent(request: GcContentRequest): Result<GcContentResponse> =
        sequenceValidationService
            .validateSequence(request.sequence)
            .map { sequence ->
                countGcContent(sequence)
            }.map { gcContent -> GcContentResponse(gcContent) }

    private fun countGcContent(sequence: String): Double {
        val gcCount = sequence.count { it == 'G' || it == 'C' }
        val totalLength = sequence.length
        val gcContent = if (totalLength > 0) gcCount.toDouble() / totalLength else 0.0
        return gcContent
    }
}

package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.response.GcContentResponse
import org.springframework.stereotype.Service

@Service
class GcContentService {

    fun calculateGcContent(request: GcContentRequest): GcContentResponse {
        val sequence = request.sequence.uppercase()
        val gcCount = sequence.count { it == 'G' || it == 'C' }
        val totalLength = sequence.length
        val gcContent = if (totalLength > 0) gcCount.toDouble() / totalLength else 0.0
        
        return GcContentResponse(gcContent)
    }
}

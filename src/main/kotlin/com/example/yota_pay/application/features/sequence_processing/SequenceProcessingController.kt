package com.example.yota_pay.application.features.sequence_processing

import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.request.SequenceValidationRequest
import com.example.yota_pay.application.features.sequence_processing.response.SequenceValidationResponse
import com.example.yota_pay.application.features.sequence_processing.service.GcContentService
import com.example.yota_pay.application.features.sequence_processing.service.SequenceValidationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sequence")
class SequenceProcessingController(
    private val sequenceValidationService: SequenceValidationService,
    private val gcContentService: GcContentService,
) {
    @PostMapping("/validate")
    fun validateSequence(
        @Valid @RequestBody request: SequenceValidationRequest,
    ): SequenceValidationResponse {
        val isValid = sequenceValidationService.validateSequence(request.sequence)
        return SequenceValidationResponse(isValid)
    }

    @PostMapping("/gc-content")
    fun calculateGcContent(
        @Valid @RequestBody request: GcContentRequest,
    ) = gcContentService.calculateGcContent(request)
}

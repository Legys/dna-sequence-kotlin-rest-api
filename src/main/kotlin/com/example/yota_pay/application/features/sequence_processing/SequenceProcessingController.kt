package com.example.yota_pay.application.features.sequence_processing

import com.example.yota_pay.application.features.sequence_processing.request.SequenceValidationRequest
import com.example.yota_pay.application.features.sequence_processing.response.SequenceValidationResponse
import com.example.yota_pay.application.features.sequence_processing.services.SequenceValidationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sequence")
class SequenceProcessingController(
    private val sequenceValidationService: SequenceValidationService
) {

    @PostMapping("/validate")
    fun validateSequence(@RequestBody request: SequenceValidationRequest): SequenceValidationResponse {
        val isValid = sequenceValidationService.validateSequence(request.sequence)
        return SequenceValidationResponse(isValid)
    }
}

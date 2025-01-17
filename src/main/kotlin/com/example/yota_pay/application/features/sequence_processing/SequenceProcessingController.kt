package com.example.yota_pay.application.features.sequence_processing

import com.example.yota_pay.application.features.sequence_processing.request.FindMotifRequest
import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.request.SequenceValidationRequest
import com.example.yota_pay.application.features.sequence_processing.response.SequenceValidationResponse
import com.example.yota_pay.application.features.sequence_processing.service.FindMotifService
import com.example.yota_pay.application.features.sequence_processing.service.GcContentService
import com.example.yota_pay.application.features.sequence_processing.service.SequenceValidationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sequence")
class SequenceProcessingController(
    private val sequenceValidationService: SequenceValidationService,
    private val gcContentService: GcContentService,
    private val findMotifService: FindMotifService,
) {
    @PostMapping("/validate")
    fun validateSequence(
        @Valid @RequestBody request: SequenceValidationRequest,
    ): SequenceValidationResponse =
        sequenceValidationService.validateSequence(request.sequence).fold(
            onSuccess = { _ ->
                SequenceValidationResponse(
                    true,
                    "Sequence is valid",
                )
            },
            onFailure = { error ->
                SequenceValidationResponse(
                    false,
                    error.message ?: "Sequence is invalid",
                )
            },
        )

    @PostMapping("/gc-content")
    fun calculateGcContent(
        @Valid @RequestBody request: GcContentRequest,
    ): ResponseEntity<*> =
        gcContentService.calculateGcContent(request).fold(
            onSuccess = { ResponseEntity.ok(it) },
            onFailure = { ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it) },
        )

    @PostMapping("/find-motif")
    fun findMotif(
        @Valid @RequestBody request: FindMotifRequest,
    ): ResponseEntity<*> =
        findMotifService.execute(request).fold(
            onSuccess = { ResponseEntity.ok(it) },
            onFailure = { ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message) },
        )
}

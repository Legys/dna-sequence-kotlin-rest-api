package com.example.yota_pay.application.features.sequence_processing.controller

import com.example.yota_pay.application.features.sequence_processing.request.GcContentRequest
import com.example.yota_pay.application.features.sequence_processing.service.GcContentService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sequence")
class GcContentController(
    private val gcContentService: GcContentService
) {

    @PostMapping("/gc-content")
    fun calculateGcContent(@Valid @RequestBody request: GcContentRequest) =
        gcContentService.calculateGcContent(request)
}

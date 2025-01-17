package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.dna.DnaSequence
import com.example.yota_pay.application.features.sequence_processing.domain.dna.TransformOperation.toRna
import com.example.yota_pay.application.features.sequence_processing.request.TransformSequenceRequest
import com.example.yota_pay.application.features.sequence_processing.response.TransformSequenceResponse
import org.springframework.stereotype.Service

@Service
class TransformSequenceService {
    fun execute(request: TransformSequenceRequest): Result<TransformSequenceResponse> =
        DnaSequence
            .create(request.sequence)
            .map { sequence ->
                TransformSequenceResponse(sequence.toRna(request.sequence).getOrThrow())
            }
} 

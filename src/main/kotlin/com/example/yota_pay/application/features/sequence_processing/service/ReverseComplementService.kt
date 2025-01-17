package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.dna.DnaSequence
import com.example.yota_pay.application.features.sequence_processing.domain.dna.ReverseComplementOperation.reverseComplement
import com.example.yota_pay.application.features.sequence_processing.request.ReverseComplementRequest
import com.example.yota_pay.application.features.sequence_processing.response.ReverseComplementResponse
import org.springframework.stereotype.Service

@Service
class ReverseComplementService {
    fun execute(request: ReverseComplementRequest): Result<ReverseComplementResponse> =
        runCatching {
            val sequence =
                DnaSequence
                    .create(request.sequence)
                    .getOrThrow()

            val reversed =
                sequence
                    .reverseComplement()
                    .getOrThrow()

            ReverseComplementResponse(
                reversed.value,
            )
        }
} 

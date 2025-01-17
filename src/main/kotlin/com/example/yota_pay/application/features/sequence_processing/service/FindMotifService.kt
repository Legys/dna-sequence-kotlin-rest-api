package com.example.yota_pay.application.features.sequence_processing.service

import com.example.yota_pay.application.features.sequence_processing.domain.dna.DnaSequence
import com.example.yota_pay.application.features.sequence_processing.domain.dna.MotifSearchOperation.findMotif
import com.example.yota_pay.application.features.sequence_processing.request.FindMotifRequest
import com.example.yota_pay.application.features.sequence_processing.response.FindMotifResponse
import org.springframework.stereotype.Service

@Service
class FindMotifService {
    fun findMotif(request: FindMotifRequest): Result<FindMotifResponse> =
        runCatching {
            val sequence = DnaSequence.create(request.sequence).getOrThrow()
            val indices = sequence.findMotif(request.motif).getOrThrow()
            FindMotifResponse(indices)
        }
}

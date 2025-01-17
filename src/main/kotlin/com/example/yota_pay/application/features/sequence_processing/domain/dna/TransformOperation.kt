package com.example.yota_pay.application.features.sequence_processing.domain.dna

import com.example.yota_pay.application.features.sequence_processing.domain.rna.RnaSequence

object TransformOperation {
    fun DnaSequence.toRna(): Result<RnaSequence> {
        val transformed = value.replace('T', 'U')
        return RnaSequence.create(transformed)
    }
} 

package com.example.yota_pay.application.features.sequence_processing.domain.dna

object TransformOperation {
    fun DnaSequence.toRna(): String {
        val transformed = value.replace('T', 'U')
//        return DnaSequence.create(transformed).getOrThrow()
        return transformed
    }
} 

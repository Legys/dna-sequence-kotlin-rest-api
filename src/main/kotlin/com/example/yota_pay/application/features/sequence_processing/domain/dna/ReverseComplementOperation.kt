package com.example.yota_pay.application.features.sequence_processing.domain.dna

object ReverseComplementOperation {
    fun DnaSequence.reverseComplement(): Result<DnaSequence> {
        val reversedSequence =
            value
                .map { complementBase(it) }
                .reversed()
                .joinToString("")
        return DnaSequence.create(
            reversedSequence,
        )
    }

    private fun complementBase(base: Char): Char =
        when (base) {
            'A' -> 'T'
            'T' -> 'A'
            'C' -> 'G'
            'G' -> 'C'
            else -> base
        }
} 

package com.example.yota_pay.application.features.sequence_processing.domain

object GcContentCalculator {
    fun calculateGcContent(sequence: DnaSequence): Double {
        val gcCount = sequence.value.count { it == 'G' || it == 'C' }
        val totalLength = sequence.value.length
        return if (totalLength > 0) gcCount.toDouble() / totalLength else 0.0
    }
}

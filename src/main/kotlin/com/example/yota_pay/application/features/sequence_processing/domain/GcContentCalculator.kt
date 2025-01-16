package com.example.yota_pay.application.features.sequence_processing.domain

object GcContentCalculator {
    fun calculateGcContent(sequence: String): Double {
        val gcCount = sequence.count { it == 'G' || it == 'C' }
        val totalLength = sequence.length
        return if (totalLength > 0) gcCount.toDouble() / totalLength else 0.0
    }
}

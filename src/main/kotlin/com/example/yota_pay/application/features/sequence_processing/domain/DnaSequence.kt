package com.example.yota_pay.application.features.sequence_processing.domain

@JvmInline
value class DnaSequence(
    val value: String,
) {
    init {
        require(value.all { it in "ACGT" }) { "Invalid DNA sequence: $value" }
    }

    fun reverseComplement(): DnaSequence {
        val complement = mapOf('A' to 'T', 'T' to 'A', 'C' to 'G', 'G' to 'C')
        return DnaSequence(value.reversed().map { complement[it] }.joinToString(""))
    }

    override fun toString(): String = value
}

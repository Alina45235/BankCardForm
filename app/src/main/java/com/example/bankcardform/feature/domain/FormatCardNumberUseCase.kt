package com.example.bankcardform.feature.domain

internal class FormatCardNumberUseCase {
    operator fun invoke(rawNumber: String): String {
        val cleaned = rawNumber.filter { it.isDigit() }.take(16)
        return cleaned.chunked(4).joinToString(" ")
    }
}
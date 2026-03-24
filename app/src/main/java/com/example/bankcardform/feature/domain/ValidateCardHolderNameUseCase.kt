package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardValidationResult

internal class ValidateCardHolderNameUseCase {
    operator fun invoke(name: String): CardValidationResult {
        val trimmed = name.trim()

        if (trimmed.length < 2) {
            return CardValidationResult.Invalid("Minimum 2 characters")
        }

        if (!trimmed.matches(Regex("^[A-Za-z\\s\\-']+$"))) {
            return CardValidationResult.Invalid("Use only Latin letters, spaces, hyphens, or apostrophes")
        }

        return CardValidationResult.Valid
    }
}
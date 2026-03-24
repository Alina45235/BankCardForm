package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardValidationResult

internal class ValidateCvvUseCase {
    operator fun invoke(cvv: String): CardValidationResult {
        return if (cvv.length == 3 && cvv.all { it.isDigit() }) {
            CardValidationResult.Valid
        } else {
            CardValidationResult.Invalid("CVV must be 3 digits")
        }
    }
}
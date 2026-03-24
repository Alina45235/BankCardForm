package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardValidationResult

internal class ValidateCardNumberUseCase {
    operator fun invoke(cardNumber: String): CardValidationResult {
        val cleaned = cardNumber.filter { it.isDigit() }

        if (cleaned.length != 16) {
            return CardValidationResult.Invalid("Card number must be 16 digits")
        }

        var sum = 0
        var alternate = false
        for (i in cleaned.length - 1 downTo 0) {
            var n = cleaned[i].digitToInt()
            if (alternate) {
                n *= 2
                if (n > 9) n -= 9
            }
            sum += n
            alternate = !alternate
        }

        return if (sum % 10 == 0) {
            CardValidationResult.Valid
        } else {
            CardValidationResult.Invalid("Invalid card number")
        }
    }
}
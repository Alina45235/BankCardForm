package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardValidationResult
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class ValidateExpiryDateUseCase {
    operator fun invoke(expiryDate: String): CardValidationResult {
        if (!expiryDate.matches(Regex("^(0[1-9]|1[0-2])/([0-9]{2})$"))) {
            return CardValidationResult.Invalid("Use MM/YY format")
        }

        val parts = expiryDate.split("/")
        val month = parts[0].toInt()
        val year = parts[1].toInt()

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val currentYear = now.year % 100
        val currentMonth = now.monthNumber

        return if (year > currentYear || (year == currentYear && month >= currentMonth)) {
            CardValidationResult.Valid
        } else {
            CardValidationResult.Invalid("Card has expired")
        }
    }
}
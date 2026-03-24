package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardValidationResult
import org.junit.Assert.*
import org.junit.Test

class ValidateCardNumberUseCaseTest {

    private val useCase = ValidateCardNumberUseCase()

    @Test
    fun `should validate card number with Luhn algorithm`() {
        val validVisa = "4111111111111111"
        val result = useCase(validVisa)
        assertTrue(result is CardValidationResult.Valid)
    }

    @Test
    fun `should reject invalid card number`() {
        val invalidCard = "4111111111111112"
        val result = useCase(invalidCard)
        assertTrue(result is CardValidationResult.Invalid)
    }
}
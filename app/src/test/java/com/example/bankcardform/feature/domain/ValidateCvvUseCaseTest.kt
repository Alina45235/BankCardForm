package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardValidationResult
import org.junit.Assert.*
import org.junit.Test

class ValidateCvvUseCaseTest {

    private val useCase = ValidateCvvUseCase()

    @Test
    fun `should validate CVV length`() {
        val validCvv = "123"
        val result = useCase(validCvv)
        assertTrue(result is CardValidationResult.Valid)
    }

    @Test
    fun `should reject CVV shorter than 3 digits`() {
        val shortCvv = "12"
        val result = useCase(shortCvv)
        assertTrue(result is CardValidationResult.Invalid)
    }

    @Test
    fun `should reject CVV with letters`() {
        val invalidCvv = "12a"
        val result = useCase(invalidCvv)
        assertTrue(result is CardValidationResult.Invalid)
    }
}
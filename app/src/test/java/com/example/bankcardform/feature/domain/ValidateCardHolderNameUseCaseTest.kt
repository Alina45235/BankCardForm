package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardValidationResult
import org.junit.Assert.*
import org.junit.Test

class ValidateCardHolderNameUseCaseTest {

    private val useCase = ValidateCardHolderNameUseCase()

    @Test
    fun `should validate card holder name format`() {
        val validName = "ALINA LOGACEVA"
        val result = useCase(validName)
        assertTrue(result is CardValidationResult.Valid)
    }

    @Test
    fun `should reject name with numbers`() {
        val invalidName = "ALINA123"
        val result = useCase(invalidName)
        assertTrue(result is CardValidationResult.Invalid)
    }

    @Test
    fun `should reject name shorter than 2 characters`() {
        val shortName = "A"
        val result = useCase(shortName)
        assertTrue(result is CardValidationResult.Invalid)
    }
}
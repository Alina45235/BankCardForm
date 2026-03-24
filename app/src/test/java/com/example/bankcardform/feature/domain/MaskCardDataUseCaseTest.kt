package com.example.bankcardform.feature.domain

import org.junit.Assert.*
import org.junit.Test

class MaskCardDataUseCaseTest {

    private val useCase = MaskCardDataUseCase()

    @Test
    fun `should not mask when isMasked is false`() {
        val result = useCase("4111111111111111", "ALINA LOGACEVA", false)
        assertTrue(result.cardNumber.contains("4111"))
        assertFalse(result.cardNumber.contains("*"))
        assertEquals("ALINA LOGACEVA", result.holderName)
    }

    @Test
    fun `should mask when isMasked is true`() {
        val result = useCase("4111111111111111", "ALINA LOGACEVA", true)
        val cardNoSpaces = result.cardNumber.replace(" ", "")
        assertTrue(cardNoSpaces.contains("******"))
        assertTrue(result.holderName.startsWith("A"))
        assertTrue(result.holderName.endsWith("A"))
        assertTrue(result.holderName.contains("*"))
    }

    @Test
    fun `should mask holder name correctly`() {
        val result = useCase("4111111111111111", "ALINA LOGACEVA", true)
        assertEquals('A', result.holderName.first())
        assertEquals('A', result.holderName.last())
        assertTrue(result.holderName.length > 2)
        assertTrue(result.holderName.substring(1, result.holderName.length - 1).all { it == '*' })
    }
}
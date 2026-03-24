package com.example.bankcardform.feature.domain

import org.junit.Assert.*
import org.junit.Test

class FormatCardNumberUseCaseTest {

    private val useCase = FormatCardNumberUseCase()

    @Test
    fun `should format card number with spaces`() {
        val rawNumber = "4111111111111111"
        val formatted = useCase(rawNumber)
        assertEquals("4111 1111 1111 1111", formatted)
    }

    @Test
    fun `should handle card number with spaces`() {
        val rawNumber = "4111 1111 1111 1111"
        val formatted = useCase(rawNumber)
        assertEquals("4111 1111 1111 1111", formatted)
    }

    @Test
    fun `should limit to 16 digits`() {
        val rawNumber = "41111111111111119999"
        val formatted = useCase(rawNumber)
        assertEquals("4111 1111 1111 1111", formatted)
    }
}
package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardType
import org.junit.Assert.*
import org.junit.Test

class DetectCardTypeUseCaseTest {

    private val useCase = DetectCardTypeUseCase()

    @Test
    fun `should detect VISA card type`() {
        val visaNumber = "4111111111111111"
        val result = useCase(visaNumber)
        assertEquals(CardType.VISA, result)
    }

    @Test
    fun `should detect MASTERCARD card type`() {
        val mastercardNumber = "5500000000000004"
        val result = useCase(mastercardNumber)
        assertEquals(CardType.MASTERCARD, result)
    }
}
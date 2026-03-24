package com.example.bankcardform.feature.domain

import com.example.bankcardform.model.domain.CardValidationResult
import org.junit.Assert.*
import org.junit.Test
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class ValidateExpiryDateUseCaseTest {

    private val useCase = ValidateExpiryDateUseCase()

    @Test
    fun `should validate expiry date not in past`() {
        val futureDate = YearMonth.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM/yy"))
        val result = useCase(futureDate)
        assertTrue(result is CardValidationResult.Valid)
    }

    @Test
    fun `should reject expired card`() {
        val expiredDate = "01/20"
        val result = useCase(expiredDate)
        assertTrue(result is CardValidationResult.Invalid)
    }
}
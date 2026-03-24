package com.example.bankcardform.feature.domain

data class MaskedCardData(
    val cardNumber: String,
    val holderName: String,
)

internal class MaskCardDataUseCase {
    operator fun invoke(
        cardNumber: String,
        holderName: String,
        isMasked: Boolean,
    ): MaskedCardData {
        if (!isMasked) {
            return MaskedCardData(
                cardNumber = cardNumber,
                holderName = holderName,
            )
        }

        return MaskedCardData(
            cardNumber = maskCardNumber(cardNumber),
            holderName = maskHolderName(holderName),
        )
    }

    private fun maskCardNumber(number: String): String {
        val cleaned = number.filter { it.isDigit() }
        if (cleaned.length < 10) return "•••• •••• •••• ${cleaned.takeLast(4)}"

        val firstSix = cleaned.take(6)
        val lastFour = cleaned.takeLast(4)
        val masked = "$firstSix******$lastFour"
        return masked.chunked(4).joinToString(" ")
    }

    private fun maskHolderName(name: String): String {
        if (name.length <= 3) return name
        return "${name.first()}${"*".repeat(name.length - 2)}${name.last()}"
    }

    // Для тестирования
    internal fun maskCardNumberForTest(number: String): String = maskCardNumber(number)
}
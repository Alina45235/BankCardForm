package com.example.bankcardform.feature

import com.example.bankcardform.model.domain.CardType
import com.example.bankcardform.model.domain.CardValidationResult

internal data class BankCardFormState(
    val cardNumber: String = "",
    val cardNumberError: CardValidationResult? = null,
    val cardType: CardType = CardType.UNKNOWN,
    val bankName: String? = null,
    val holderName: String = "",
    val holderNameError: CardValidationResult? = null,
    val expiryDate: String = "",
    val expiryDateError: CardValidationResult? = null,
    val cvv: String = "",
    val cvvError: CardValidationResult? = null,
    val isDataMasked: Boolean = false,
    val isSaveButtonEnabled: Boolean = false,
    val showSuccessMessage: Boolean = false,
)
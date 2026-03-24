package com.example.bankcardform.model.domain

data class BankCard(
    val cardNumber: String = "",
    val holderName: String = "",
    val expiryDate: String = "",
    val cvv: String = ""
)
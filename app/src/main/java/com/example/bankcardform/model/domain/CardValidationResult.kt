package com.example.bankcardform.model.domain

sealed class CardValidationResult {
    object Valid : CardValidationResult()
    data class Invalid(val errorMessage: String) : CardValidationResult()
}
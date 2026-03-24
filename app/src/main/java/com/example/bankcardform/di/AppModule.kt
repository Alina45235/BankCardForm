package com.example.bankcardform.di

import com.example.bankcardform.feature.BankCardFormViewModel
import com.example.bankcardform.feature.domain.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // UseCases
    single { ValidateCardNumberUseCase() }
    single { ValidateCardHolderNameUseCase() }
    single { ValidateExpiryDateUseCase() }
    single { ValidateCvvUseCase() }
    single { DetectCardTypeUseCase() }
    single { FormatCardNumberUseCase() }
    single { MaskCardDataUseCase() }

    // ViewModel
    viewModel { BankCardFormViewModel(
        validateCardNumberUseCase = get(),
        validateCardHolderNameUseCase = get(),
        validateExpiryDateUseCase = get(),
        validateCvvUseCase = get(),
        detectCardTypeUseCase = get(),
        formatCardNumberUseCase = get(),
        maskCardDataUseCase = get()
    ) }
}
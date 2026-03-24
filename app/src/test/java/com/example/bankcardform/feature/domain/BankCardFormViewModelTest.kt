package com.example.bankcardform.feature.domain

import com.example.bankcardform.feature.BankCardFormEvent
import com.example.bankcardform.feature.BankCardFormViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BankCardFormViewModelTest {

    private lateinit var viewModel: BankCardFormViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = BankCardFormViewModel(
            validateCardNumberUseCase = ValidateCardNumberUseCase(),
            validateCardHolderNameUseCase = ValidateCardHolderNameUseCase(),
            validateExpiryDateUseCase = ValidateExpiryDateUseCase(),
            validateCvvUseCase = ValidateCvvUseCase(),
            detectCardTypeUseCase = DetectCardTypeUseCase(),
            formatCardNumberUseCase = FormatCardNumberUseCase(),
            maskCardDataUseCase = MaskCardDataUseCase()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should enable save button only when all fields valid`() = runTest {
        // Изначально кнопка неактивна
        assertFalse(viewModel.state.value.isSaveButtonEnabled)

        // Вводим валидные данные
        viewModel.onEvent(BankCardFormEvent.OnCardNumberChange("4111111111111111"))
        viewModel.onEvent(BankCardFormEvent.OnHolderNameChange("ALINA LOGACEVA"))
        viewModel.onEvent(BankCardFormEvent.OnExpiryDateChange("12/28"))
        viewModel.onEvent(BankCardFormEvent.OnCvvChange("123"))

        // Кнопка должна стать активной
        assertTrue(viewModel.state.value.isSaveButtonEnabled)

        // Удаляем CVV — кнопка должна стать неактивной
        viewModel.onEvent(BankCardFormEvent.OnCvvChange(""))

        assertFalse(viewModel.state.value.isSaveButtonEnabled)
    }
}
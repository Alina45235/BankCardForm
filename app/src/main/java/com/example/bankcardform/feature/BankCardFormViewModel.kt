package com.example.bankcardform.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardform.feature.domain.*
import com.example.bankcardform.model.domain.CardValidationResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class BankCardFormViewModel(
    private val validateCardNumberUseCase: ValidateCardNumberUseCase,
    private val validateCardHolderNameUseCase: ValidateCardHolderNameUseCase,
    private val validateExpiryDateUseCase: ValidateExpiryDateUseCase,
    private val validateCvvUseCase: ValidateCvvUseCase,
    private val detectCardTypeUseCase: DetectCardTypeUseCase,
    private val formatCardNumberUseCase: FormatCardNumberUseCase,
    private val maskCardDataUseCase: MaskCardDataUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(BankCardFormState())
    val state: StateFlow<BankCardFormState> = _state.asStateFlow()

    fun onEvent(event: BankCardFormEvent) {
        when (event) {
            is BankCardFormEvent.OnCardNumberChange -> onCardNumberChange(event.value)
            is BankCardFormEvent.OnHolderNameChange -> onHolderNameChange(event.value)
            is BankCardFormEvent.OnExpiryDateChange -> onExpiryDateChange(event.value)
            is BankCardFormEvent.OnCvvChange -> onCvvChange(event.value)
            BankCardFormEvent.OnToggleMask -> toggleMask()
            BankCardFormEvent.OnSaveClick -> saveCard()
        }
    }

    private fun onCardNumberChange(rawNumber: String) {
        val formatted = formatCardNumberUseCase(rawNumber)
        val cardType = detectCardTypeUseCase(formatted)
        val validationResult = validateCardNumberUseCase(formatted)
        val bankName = detectCardTypeUseCase.detectBank(formatted)

        _state.update {
            it.copy(
                cardNumber = formatted,
                cardNumberError = if (validationResult !is CardValidationResult.Valid) validationResult else null,
                cardType = cardType,
                bankName = bankName,
            )
        }
        updateSaveButtonState()
    }

    private fun onHolderNameChange(name: String) {
        val upperName = name.uppercase()
        val validationResult = validateCardHolderNameUseCase(upperName)

        _state.update {
            it.copy(
                holderName = upperName,
                holderNameError = if (validationResult !is CardValidationResult.Valid) validationResult else null,
            )
        }
        updateSaveButtonState()
    }

    private fun onExpiryDateChange(date: String) {
        var processed = date.filter { it.isDigit() }
        if (processed.length > 2) {
            processed = processed.substring(0, 2) + "/" + processed.substring(2, processed.length.coerceAtMost(4))
        }

        val validationResult = validateExpiryDateUseCase(processed)

        _state.update {
            it.copy(
                expiryDate = processed,
                expiryDateError = if (validationResult !is CardValidationResult.Valid) validationResult else null,
            )
        }
        updateSaveButtonState()
    }

    private fun onCvvChange(cvv: String) {
        val cleaned = cvv.filter { it.isDigit() }.take(3)
        val validationResult = validateCvvUseCase(cleaned)

        _state.update {
            it.copy(
                cvv = cleaned,
                cvvError = if (validationResult !is CardValidationResult.Valid) validationResult else null,
            )
        }
        updateSaveButtonState()
    }

    private fun toggleMask() {
        _state.update { it.copy(isDataMasked = !it.isDataMasked) }
    }

    private fun saveCard() {
        viewModelScope.launch {
            // Показываем сообщение об успехе
            _state.update { it.copy(showSuccessMessage = true) }

            // Ждем 3 секунды
            delay(3000)

            // Очищаем все поля и скрываем сообщение
            _state.update {
                it.copy(
                    showSuccessMessage = false,
                    // Очищаем поля
                    cardNumber = "",
                    holderName = "",
                    expiryDate = "",
                    cvv = "",
                    // Сбрасываем ошибки
                    cardNumberError = null,
                    holderNameError = null,
                    expiryDateError = null,
                    cvvError = null,
                    // Сбрасываем тип карты
                    cardType = com.example.bankcardform.model.domain.CardType.UNKNOWN,
                    // Сбрасываем название банка
                    bankName = null,
                    // Кнопка сохранения станет неактивной
                    isSaveButtonEnabled = false,
                )
            }
        }
    }

    private fun updateSaveButtonState() {
        val isValid = _state.value.cardNumberError == null &&
                _state.value.holderNameError == null &&
                _state.value.expiryDateError == null &&
                _state.value.cvvError == null &&
                _state.value.cardNumber.isNotBlank() &&
                _state.value.holderName.isNotBlank() &&
                _state.value.expiryDate.isNotBlank() &&
                _state.value.cvv.isNotBlank()

        _state.update { it.copy(isSaveButtonEnabled = isValid) }
    }
}

internal sealed class BankCardFormEvent {
    data class OnCardNumberChange(
        val value: String,
    ) : BankCardFormEvent()

    data class OnHolderNameChange(
        val value: String,
    ) : BankCardFormEvent()

    data class OnExpiryDateChange(
        val value: String,
    ) : BankCardFormEvent()

    data class OnCvvChange(
        val value: String,
    ) : BankCardFormEvent()

    object OnToggleMask : BankCardFormEvent()
    object OnSaveClick : BankCardFormEvent()
}
package com.example.bankcardform.feature

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.bankcardform.feature.components.CardVisual
import com.example.bankcardform.feature.domain.MaskCardDataUseCase
import com.example.bankcardform.model.domain.CardValidationResult
import org.koin.androidx.compose.getViewModel

@Composable
internal fun BankCardFormScreen(
    viewModel: BankCardFormViewModel = getViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val maskUseCase = remember { MaskCardDataUseCase() }
    val maskedData = maskUseCase(state.cardNumber, state.holderName, state.isDataMasked)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Заголовок
        Text(
            text = "Add Bank Card",
            style = MaterialTheme.typography.headlineMedium,
        )

        // Визуальная карта с анимацией
        AnimatedContent(
            targetState = Pair(state.isDataMasked, maskedData),
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) +
                        slideInHorizontally(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300)) +
                        slideOutHorizontally(animationSpec = tween(300))
            },
            label = "cardAnimation",
        ) { (isMasked, data) ->
            CardVisual(
                cardNumber = if (isMasked) data.cardNumber else state.cardNumber,
                holderName = if (isMasked) data.holderName else state.holderName,
                expiryDate = state.expiryDate,
                cardType = state.cardType,
                bankName = state.bankName,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        // Поле номера карты
        OutlinedTextField(
            value = state.cardNumber,
            onValueChange = { viewModel.onEvent(BankCardFormEvent.OnCardNumberChange(it)) },
            label = { Text("Card Number") },
            isError = state.cardNumberError != null,
            supportingText = {
                (state.cardNumberError as? CardValidationResult.Invalid)?.errorMessage?.let {
                    Text(it)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )

        // Поле имени
        OutlinedTextField(
            value = state.holderName,
            onValueChange = { viewModel.onEvent(BankCardFormEvent.OnHolderNameChange(it)) },
            label = { Text("Cardholder Name") },
            isError = state.holderNameError != null,
            supportingText = {
                (state.holderNameError as? CardValidationResult.Invalid)?.errorMessage?.let {
                    Text(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = state.expiryDate,
                onValueChange = { viewModel.onEvent(BankCardFormEvent.OnExpiryDateChange(it)) },
                label = { Text("Expiry (MM/YY)") },
                isError = state.expiryDateError != null,
                supportingText = {
                    (state.expiryDateError as? CardValidationResult.Invalid)?.errorMessage?.let {
                        Text(it)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
            )

            OutlinedTextField(
                value = state.cvv,
                onValueChange = { viewModel.onEvent(BankCardFormEvent.OnCvvChange(it)) },
                label = { Text("CVV") },
                isError = state.cvvError != null,
                supportingText = {
                    (state.cvvError as? CardValidationResult.Invalid)?.errorMessage?.let {
                        Text(it)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.weight(1f),
            )
        }

        // Переключатель скрытия данных
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Hide sensitive data")
            Switch(
                checked = state.isDataMasked,
                onCheckedChange = { viewModel.onEvent(BankCardFormEvent.OnToggleMask) },
            )
        }

        // Кнопка сохранения
        Button(
            onClick = { viewModel.onEvent(BankCardFormEvent.OnSaveClick) },
            enabled = state.isSaveButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Save Card")
        }

        // Сообщение об успехе с анимацией
        AnimatedVisibility(
            visible = state.showSuccessMessage,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Card saved successfully!",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}
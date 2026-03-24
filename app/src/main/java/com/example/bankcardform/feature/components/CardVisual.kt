package com.example.bankcardform.feature.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bankcardform.model.domain.CardType

@Composable
internal fun CardVisual(
    cardNumber: String,
    holderName: String,
    expiryDate: String,
    cardType: CardType,
    bankName: String?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (cardType) {
                CardType.VISA -> Color(0xFF1A1F71)
                CardType.MASTERCARD -> Color(0xFFCC0000)
                CardType.MIR -> Color(0xFF00A651)
                CardType.UNKNOWN -> Color(0xFF333333)
            },
        ),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // Верхняя часть: логотип карты и банк
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = when (cardType) {
                        CardType.VISA -> "VISA"
                        CardType.MASTERCARD -> "MASTERCARD"
                        CardType.MIR -> "MIR"
                        CardType.UNKNOWN -> "CARD"
                    },
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                )

                // Показываем название банка в правом верхнем углу
                if (!bankName.isNullOrBlank()) {
                    Text(
                        text = bankName,
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Номер карты
            Text(
                text = cardNumber.ifBlank { "**** **** **** ****" },
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
            )

            // Нижняя часть: имя и срок
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = holderName.ifBlank { "YOUR NAME" },
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = expiryDate.ifBlank { "MM/YY" },
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
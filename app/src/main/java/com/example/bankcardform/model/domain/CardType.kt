package com.example.bankcardform.model.domain

enum class CardType(val prefixes: List<String>) {
    VISA(listOf("4")),
    MASTERCARD(listOf("51", "52", "53", "54", "55", "2221")),
    MIR(listOf("2200", "2201", "2202", "2203", "2204")),
    UNKNOWN(emptyList())
}
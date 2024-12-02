package com.example.CryptoPortolio.model.dto;

public record CoinCapResponseDto(
        String id,
        String rank,
        String symbol,
        String name,
        String supply,
        String maxSupply,
        String marketCapUsd,
        String volumeUsd24Hr,
        double priceUsd,
        String changePercent24Hr,
        String vwap24Hr
) {
}


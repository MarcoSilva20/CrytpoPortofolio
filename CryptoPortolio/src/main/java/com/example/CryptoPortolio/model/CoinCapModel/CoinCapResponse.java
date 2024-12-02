package com.example.CryptoPortolio.model.CoinCapModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinCapResponse {

    private String id;
    private String rank;
    private String symbol;
    private String name;
    private String supply;
    private String maxSupply;
    private String marketCapUsd;
    private String volumeUsd24Hr;
    private double priceUsd;
    private String changePercent24Hr;
    private String vwap24Hr;
}

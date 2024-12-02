package com.example.CryptoPortolio.model.CoinCapModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinCapData {

    private CoinCapResponse coinCapResponse;
    private long timestamp;
}

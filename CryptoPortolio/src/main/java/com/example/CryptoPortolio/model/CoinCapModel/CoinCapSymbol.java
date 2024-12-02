package com.example.CryptoPortolio.model.CoinCapModel;

import com.example.CryptoPortolio.model.dto.CoinCapResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinCapSymbol {

    private CoinCapResponse[] data;
    private long timestamp;
}

package com.example.CryptoPortolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor

public class AveragePriceResult {

    private String symbol;
    private double averagePrice;
    private double quantity;

}

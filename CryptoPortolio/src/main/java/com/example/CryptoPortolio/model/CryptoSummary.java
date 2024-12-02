package com.example.CryptoPortolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoSummary {

    private double total;
    private String bestAsset;
    private double bestPerformance;
    private String worstAsset;
    private double worstPerformance;
}

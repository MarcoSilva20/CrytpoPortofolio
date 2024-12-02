package com.example.CryptoPortolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class CryptoPrices {

    @Id
    private String name;
    private String symbol;
    private double price;
}

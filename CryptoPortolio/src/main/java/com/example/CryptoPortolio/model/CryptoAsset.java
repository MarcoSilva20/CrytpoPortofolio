package com.example.CryptoPortolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String symbol;
    private double quantity;
    private double price;
    private Long   clientId;

    public CryptoAsset(double quantity, double price, String symbol, Long clientId) {
        this.quantity = quantity;
        this.price = price;
        this.symbol = symbol;
        this.clientId = clientId;
    }
}

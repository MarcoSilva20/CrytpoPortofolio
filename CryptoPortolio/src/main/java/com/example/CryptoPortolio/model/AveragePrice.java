package com.example.CryptoPortolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AveragePrice {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String symbol;
        private double averagePrice;
        private double currentPrice;
        private Long clientId;

        public AveragePrice(String symbol, double averagePrice, double currentPrice, Long clientId) {
            this.symbol = symbol;
            this.averagePrice = averagePrice;
            this.currentPrice = currentPrice;
            this.clientId = clientId;
        }
}

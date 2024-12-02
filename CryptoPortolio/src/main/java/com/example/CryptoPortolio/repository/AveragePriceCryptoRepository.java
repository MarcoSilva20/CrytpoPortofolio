package com.example.CryptoPortolio.repository;


import com.example.CryptoPortolio.model.AveragePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AveragePriceCryptoRepository extends JpaRepository<AveragePrice, Long> {
    AveragePrice findBySymbolAndClientId(String symbol, Long clientId);
    List<AveragePrice> findByClientId(Long clientId);
}

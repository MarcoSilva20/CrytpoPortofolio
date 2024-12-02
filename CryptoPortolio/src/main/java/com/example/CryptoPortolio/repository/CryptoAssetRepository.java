package com.example.CryptoPortolio.repository;

import com.example.CryptoPortolio.model.AveragePriceResult;
import com.example.CryptoPortolio.model.CryptoAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoAssetRepository extends JpaRepository<CryptoAsset, Long> {


    @Query("SELECT new com.example.CryptoPortolio.model.AveragePriceResult(a.symbol, AVG(a.price), SUM(a.quantity)) " +
            "FROM CryptoAsset a " +
            "WHERE a.clientId = :clientId " +
            "GROUP BY a.symbol")
    List<AveragePriceResult> findAveragePriceBySymbolAndClientId(@Param("clientId") Long clientId);


}

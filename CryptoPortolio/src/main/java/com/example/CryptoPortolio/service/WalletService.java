package com.example.CryptoPortolio.service;

import com.example.CryptoPortolio.apiClient.CoinCapService;
import com.example.CryptoPortolio.model.AveragePrice;
import com.example.CryptoPortolio.model.AveragePriceResult;
import com.example.CryptoPortolio.model.CryptoAsset;
import com.example.CryptoPortolio.model.CryptoSummary;
import com.example.CryptoPortolio.model.dto.CrytoAssetDto;
import com.example.CryptoPortolio.model.mappers.CryptoAssetDtoToCryptoAssetMapper;
import com.example.CryptoPortolio.repository.AveragePriceCryptoRepository;
import com.example.CryptoPortolio.repository.CryptoAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    private final CryptoAssetRepository cryptoClientRepository;
    private final AveragePriceCryptoRepository averagePriceCryptoRepository;
    private final CoinCapService coinCapService;

    // Constructor injection (Autowired can be omitted in Spring 4.3+ if only one constructor)
    @Autowired
    public WalletService(CryptoAssetRepository cryptoClientRepository,
                         AveragePriceCryptoRepository averagePriceCryptoRepository,
                         CoinCapService coinCapService) {
        this.cryptoClientRepository = cryptoClientRepository;
        this.averagePriceCryptoRepository = averagePriceCryptoRepository;
        this.coinCapService = coinCapService;
    }


    public CryptoSummary processCryptoWallet(List<CrytoAssetDto> wallet, Long clientID) {
        saveWallet(wallet, clientID);
        return calculateSummayWallet(clientID);
    }

    public void saveWallet(List<CrytoAssetDto> wallet, Long clientID) {
        List<CryptoAsset> cryptoAssets = CryptoAssetDtoToCryptoAssetMapper.mapDtoToModel(wallet, clientID);
        cryptoClientRepository.saveAll(cryptoAssets);
    }

    public CryptoSummary calculateSummayWallet(Long clientId) {
        List<AveragePriceResult> cryptoAssets = cryptoClientRepository.findAveragePriceBySymbolAndClientId(clientId);
        double total = 0.0;
        String bestAsset = null, worstAsset = null;
        double bestPerformance = Double.NEGATIVE_INFINITY, worstPerformance = Double.POSITIVE_INFINITY;

        for (AveragePriceResult asset : cryptoAssets) {
            double priceUsd = coinCapService.getDetailsAsset(coinCapService.getNameBySymbol(asset.getSymbol())).getPriceUsd();
            total += (asset.getQuantity() * priceUsd);

            double percentageAssets = comparePriceAssets(asset.getAveragePrice(), priceUsd);
            if (bestPerformance < percentageAssets) {
                bestAsset = asset.getSymbol();
                bestPerformance = percentageAssets;
            }
            if (worstPerformance > percentageAssets) {
                worstAsset = asset.getSymbol();
                worstPerformance = percentageAssets;
            }

            AveragePrice existingAveragePrice = averagePriceCryptoRepository.findBySymbolAndClientId(asset.getSymbol(), clientId);

            if (existingAveragePrice != null) {
                existingAveragePrice.setAveragePrice(asset.getAveragePrice());
                existingAveragePrice.setCurrentPrice(priceUsd);
                averagePriceCryptoRepository.save(existingAveragePrice);
            } else {
                averagePriceCryptoRepository.save(new AveragePrice(asset.getSymbol(), asset.getAveragePrice(), priceUsd, clientId));
            }
        }
        return new CryptoSummary(round(total), bestAsset, bestPerformance, worstAsset, worstPerformance);
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                         .setScale(2, BigDecimal.ROUND_HALF_UP)
                         .doubleValue();
    }

    public double comparePriceAssets(double assetAvgPrice, double latestPrices) {
        return ((latestPrices - assetAvgPrice) / assetAvgPrice) * 100;
    }
}

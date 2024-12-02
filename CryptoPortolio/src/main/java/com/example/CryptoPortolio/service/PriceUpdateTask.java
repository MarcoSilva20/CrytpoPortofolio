package com.example.CryptoPortolio.service;

import com.example.CryptoPortolio.apiClient.CoinCapService;
import com.example.CryptoPortolio.model.AveragePrice;
import com.example.CryptoPortolio.model.CoinCapModel.CoinCapResponse;
import com.example.CryptoPortolio.repository.AveragePriceCryptoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
public class PriceUpdateTask {

    private static final Logger logger = LoggerFactory.getLogger(PriceUpdateTask.class);

    @Autowired
    private CoinCapService coinCapService;

    @Autowired
    private AveragePriceCryptoRepository averagePriceCryptoRepository;


    public void getCurrentPrices(Long clientId) {
        List<AveragePrice> averagePriceList = averagePriceCryptoRepository.findByClientId(clientId);
        ExecutorService executor = Executors.newFixedThreadPool(3); // Limit to 3 threads
        List<Future<Void>> futures = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String startTime = sdf.format(new Date());
        logger.info("Now is {}", startTime);

        for (AveragePrice averagePrice : averagePriceList) {
            futures.add(executor.submit(() -> {
                String submissionTime = sdf.format(new Date());
                logger.info("Submitted request {} at {}", averagePrice.getSymbol(), submissionTime);

                String cryptoId = coinCapService.getNameBySymbol(averagePrice.getSymbol());
                double latestPrice = getPriceUpdate(coinCapService.getDetailsAsset(cryptoId));

                AveragePrice existingAveragePrice = averagePriceCryptoRepository.findBySymbolAndClientId(averagePrice.getSymbol(), clientId);
                existingAveragePrice.setCurrentPrice(latestPrice);
                averagePriceCryptoRepository.save(existingAveragePrice);
                return null;
            }));
        }

        for (Future<Void> future : futures) {
            try {
                future.get(); // Waits for all task done
            } catch (Exception e) {
                throw new RuntimeException("Fail update prices", e);
            }
        }
        executor.shutdown();
    }

    private double getPriceUpdate(CoinCapResponse detailsAsset) {
        return detailsAsset.getPriceUsd();
    }
}

package com.example.CryptoPortolio.service;

import com.example.CryptoPortolio.apiClient.CoinCapService;
import com.example.CryptoPortolio.model.AveragePriceResult;
import com.example.CryptoPortolio.model.CoinCapModel.CoinCapResponse;
import com.example.CryptoPortolio.model.dto.CoinCapResponseDto;
import com.example.CryptoPortolio.model.CryptoSummary;
import com.example.CryptoPortolio.repository.AveragePriceCryptoRepository;
import com.example.CryptoPortolio.repository.CryptoAssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

@SpringBootTest
public class WalletServiceTest {

    @MockBean
    private CryptoAssetRepository cryptoAssetRepository;

    @MockBean
    private  AveragePriceCryptoRepository averagePriceCryptoRepository;

    @MockBean
    private CoinCapService coinCapService;

    @InjectMocks
    private WalletService walletService;

    @BeforeEach
    public void setup() {
        walletService = new WalletService(cryptoAssetRepository, averagePriceCryptoRepository, coinCapService);
    }

    @Test
    void testCalculateSummayWallet() {
        Long clientID = 1L;

        AveragePriceResult averagePriceResult = new AveragePriceResult("BTC", 25000.0, 2);
        when(cryptoAssetRepository.findAveragePriceBySymbolAndClientId(clientID))
                .thenReturn(Arrays.asList(averagePriceResult));
        when(coinCapService.getNameBySymbol("BTC")).thenReturn("bitcoin");

        CoinCapResponse bitcoinResponse = new CoinCapResponse(
                "bitcoin",
                "1",
                "BTC",
                "Bitcoin",
                "19790393",
                "21000000",
                "1905845760703",
                "18075538936",
                96301.56,
                "-0.988",
                "96466"
        );

        when(coinCapService.getDetailsAsset("bitcoin")).thenReturn(bitcoinResponse);

        CryptoSummary summary = walletService.calculateSummayWallet(clientID);

        assertNotNull(summary, "Summary should not be null");
        assertEquals("BTC", summary.getBestAsset(), "Best asset should be BTC");
        assertEquals(192603.12, summary.getTotal(), 0.01, "Total value is incorrect");
        assertEquals(((96301.56 - 25000) / 25000) * 100, summary.getBestPerformance(), 0.01, "Best performance is incorrect");
        assertEquals("BTC", summary.getWorstAsset(), "Worst asset should be BTC (only one asset)");
        assertEquals(((96301.56 - 25000) / 25000) * 100, summary.getWorstPerformance(), 0.01, "Worst performance is incorrect");

    }
}

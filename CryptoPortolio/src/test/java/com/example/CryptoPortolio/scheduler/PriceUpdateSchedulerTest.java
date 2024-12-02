package com.example.CryptoPortolio.scheduler;

import com.example.CryptoPortolio.apiClient.CoinCapService;
import com.example.CryptoPortolio.model.AveragePrice;
import com.example.CryptoPortolio.model.CoinCapModel.CoinCapResponse;
import com.example.CryptoPortolio.repository.AveragePriceCryptoRepository;
import com.example.CryptoPortolio.service.PriceUpdateTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PriceUpdateSchedulerTest {

    @Mock
    private CoinCapService coinCapService;

    @Mock
    private AveragePriceCryptoRepository averagePriceCryptoRepository;

    @InjectMocks
    private PriceUpdateTask priceUpdateTask;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrentPrices() throws Exception {

    }
}

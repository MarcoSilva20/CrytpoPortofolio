package com.example.CryptoPortolio.scheduler;

import com.example.CryptoPortolio.service.PriceUpdateTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class PriceUpdateSchedulerTest {

    @Mock
    private PriceUpdateTask priceUpdateTask;

    private ScheduledExecutorService executorService;

    private PriceUpdateScheduler scheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        executorService = Executors.newSingleThreadScheduledExecutor();
        scheduler = new PriceUpdateScheduler(priceUpdateTask, executorService);
    }

    @Test
    void testUpdateFrequency() throws InterruptedException {
        long clientId = 1L;
        long frequencyMs = 1000L;

        scheduler.updateFrequency(clientId, frequencyMs);

        TimeUnit.MILLISECONDS.sleep(1500);

        verify(priceUpdateTask, atLeastOnce()).getCurrentPrices(clientId);
    }

    @Test
    void testUpdateFrequency_InvalidFrequency() {
        long clientId = 1L;

        scheduler.updateFrequency(clientId, null);

        verify(priceUpdateTask, never()).getCurrentPrices(anyLong());
    }

    @Test
    void testRescheduleTask() throws InterruptedException {
        long clientId = 1L;

        scheduler.updateFrequency(clientId, 1000L);
        TimeUnit.MILLISECONDS.sleep(1500);
        verify(priceUpdateTask, atLeastOnce()).getCurrentPrices(clientId);

        reset(priceUpdateTask);

        scheduler.updateFrequency(clientId, 500L);
        TimeUnit.MILLISECONDS.sleep(600);

        verify(priceUpdateTask, atLeastOnce()).getCurrentPrices(clientId);
    }

}

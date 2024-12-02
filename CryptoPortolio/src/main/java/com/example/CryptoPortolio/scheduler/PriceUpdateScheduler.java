package com.example.CryptoPortolio.scheduler;

import com.example.CryptoPortolio.service.PriceUpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PriceUpdateScheduler {

    @Autowired
    private PriceUpdateTask priceUpdateTask;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final AtomicLong updateFrequency = new AtomicLong(60000);

    private ScheduledFuture<?> scheduledTask;

    public void updateFrequency(long clientId, Long frequencyMs) {
        updateFrequency.set(frequencyMs);
        rescheduleTask(clientId);
    }

    private void scheduleTask(long clientId) {
        scheduledTask = scheduler.scheduleAtFixedRate(() -> priceUpdateTask.getCurrentPrices(clientId), 0, updateFrequency.get(), TimeUnit.MILLISECONDS);
    }

    private void rescheduleTask(long clientId) {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
        scheduleTask(clientId);
    }

}

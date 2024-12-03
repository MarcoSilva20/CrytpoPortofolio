package com.example.CryptoPortolio.scheduler;

import com.example.CryptoPortolio.service.PriceUpdateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PriceUpdateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(PriceUpdateScheduler.class);

    private final PriceUpdateTask priceUpdateTask;
    private final ScheduledExecutorService scheduler;
    private final AtomicLong updateFrequency;

    private ScheduledFuture<?> scheduledTask;


    @Autowired
    public PriceUpdateScheduler(PriceUpdateTask priceUpdateTask, ScheduledExecutorService scheduler) {
        this.priceUpdateTask = priceUpdateTask;
        this.scheduler = scheduler;
        this.updateFrequency = new AtomicLong(60000); // Default frequency
    }

    public void updateFrequency(long clientId, Long frequencyMs) {
        if (frequencyMs == null) {
            logger.warn("Invalid frequency provided: {}. Using default frequency.", frequencyMs);
            return;
        }
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

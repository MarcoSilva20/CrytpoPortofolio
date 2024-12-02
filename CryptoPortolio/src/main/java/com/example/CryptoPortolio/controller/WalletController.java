package com.example.CryptoPortolio.controller;

import com.example.CryptoPortolio.model.CryptoSummary;
import com.example.CryptoPortolio.model.dto.CrytoAssetDto;
import com.example.CryptoPortolio.scheduler.PriceUpdateScheduler;
import com.example.CryptoPortolio.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/wallet/{clientId}")
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    @Autowired
    private PriceUpdateScheduler priceUpdateScheduler;

    @PostMapping("/walletSummary")
    public ResponseEntity<CryptoSummary> getSummaryWallet(@PathVariable Long clientId,
                                                          @RequestBody List<CrytoAssetDto> wallet) {
        try {
            if (wallet == null || wallet.isEmpty()) {
                logger.warn("Wallet is empty for clientId: {}", clientId);
                return ResponseEntity.ok(new CryptoSummary());
            }
            CryptoSummary summary = walletService.processCryptoWallet(wallet,clientId);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            logger.error("Error updating wallet prices: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/currentPrices")
    public ResponseEntity<CryptoSummary> updateWallet(  @PathVariable Long clientId,
                                                        @RequestParam(defaultValue = "10000") Long frequencyMs) {
        priceUpdateScheduler.updateFrequency(clientId, frequencyMs);
        return ResponseEntity.ok(null);
    }
}

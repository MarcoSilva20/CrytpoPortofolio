package com.example.CryptoPortolio.controller;

import com.example.CryptoPortolio.model.CryptoSummary;
import com.example.CryptoPortolio.model.dto.CrytoAssetDto;
import com.example.CryptoPortolio.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;

    @Mock
    private WalletService walletService;


    @Test
    void testGetSummaryWallet_WhenWalletIsEmpty() {
        lenient().when(walletService.processCryptoWallet(any(), anyLong())).thenReturn(new CryptoSummary());

        ResponseEntity<CryptoSummary> response = walletController.getSummaryWallet(1L, Collections.emptyList());

        assert(response.getStatusCode().is2xxSuccessful());
        assert(response.getBody() != null);

        verify(walletService, never()).processCryptoWallet(any(), anyLong());
    }

    @Test
    void testGetSummaryWallet_WhenWalletIsNotEmpty() {
        CrytoAssetDto asset = new CrytoAssetDto("BTC",50000,1);
        when(walletService.processCryptoWallet(any(), anyLong())).thenReturn(new CryptoSummary());

        ResponseEntity<CryptoSummary> response = walletController.getSummaryWallet(1L, Collections.singletonList(asset));

        assert(response.getStatusCode().is2xxSuccessful());
        assert(response.getBody() != null);
        verify(walletService, times(1)).processCryptoWallet(any(), anyLong());
    }
}

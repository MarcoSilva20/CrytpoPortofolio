package com.example.CryptoPortolio.apiClient;

import com.example.CryptoPortolio.model.CoinCapModel.CoinCapData;
import com.example.CryptoPortolio.model.CoinCapModel.CoinCapResponse;
import com.example.CryptoPortolio.model.CoinCapModel.CoinCapSymbol;
import com.example.CryptoPortolio.model.dto.CoinCapDataDto;

import com.example.CryptoPortolio.model.dto.CoinCapSymbolDto;
import com.example.CryptoPortolio.model.mappers.CoinCapMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class CoinCapService {
    private static final String COINCAP_API_URL = "https://api.coincap.io/v2/assets/";
    private static final String URL_SYMBOL = COINCAP_API_URL + "?search=%s&limit=1";
    private final RestTemplate restTemplate = new RestTemplate();

    public CoinCapResponse getDetailsAsset(String symbol) {

        String url = COINCAP_API_URL + symbol.toLowerCase();
        CoinCapDataDto coinCapDataDto = restTemplate.getForObject(url, CoinCapDataDto.class);

        if (coinCapDataDto != null) {
            CoinCapData coinCapData = CoinCapMapper.mapCoinCapDataDtoToModel(coinCapDataDto);
            return coinCapData.getCoinCapResponse();
        }
        return null;
    }

    public String getNameBySymbol(String symbol) {
        try {
            String url = String.format(URL_SYMBOL, symbol);
            CoinCapSymbolDto coinCapSymbolDto = restTemplate.getForObject(url, CoinCapSymbolDto.class);
            CoinCapSymbol mapToCoinCapSymbol = CoinCapMapper.mapToCoinCapSymbol(coinCapSymbolDto);
            return mapToCoinCapSymbol.getData()[0].getName();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }
}



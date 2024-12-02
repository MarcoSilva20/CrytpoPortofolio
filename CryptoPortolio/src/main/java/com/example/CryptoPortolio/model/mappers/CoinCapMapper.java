package com.example.CryptoPortolio.model.mappers;

import com.example.CryptoPortolio.model.CoinCapModel.CoinCapData;
import com.example.CryptoPortolio.model.CoinCapModel.CoinCapResponse;
import com.example.CryptoPortolio.model.CoinCapModel.CoinCapSymbol;
import com.example.CryptoPortolio.model.dto.CoinCapDataDto;
import com.example.CryptoPortolio.model.dto.CoinCapResponseDto;
import com.example.CryptoPortolio.model.dto.CoinCapSymbolDto;

public class CoinCapMapper {

    public static CoinCapResponse mapCoinCapResponseDtoToModel(CoinCapResponseDto dto) {
        return new CoinCapResponse(
                dto.id(),
                dto.rank(),
                dto.symbol(),
                dto.name(),
                dto.supply(),
                dto.maxSupply(),
                dto.marketCapUsd(),
                dto.volumeUsd24Hr(),
                dto.priceUsd(),
                dto.changePercent24Hr(),
                dto.vwap24Hr()
        );
    }

    public static CoinCapData mapCoinCapDataDtoToModel(CoinCapDataDto dto) {
        CoinCapResponse coinCapResponse = mapCoinCapResponseDtoToModel(dto.data());
        return new CoinCapData(coinCapResponse, dto.timestamp());
    }

    public static CoinCapSymbol mapToCoinCapSymbol(CoinCapSymbolDto coinCapSymbolDto) {
        CoinCapSymbol coinCapSymbol = new CoinCapSymbol();
        coinCapSymbol.setTimestamp(coinCapSymbolDto.timestamp());

        CoinCapResponse[] coinCapResponses = new CoinCapResponse[coinCapSymbolDto.data().length];
        for (int i = 0; i < coinCapSymbolDto.data().length; i++) {
            coinCapResponses[i] = mapCoinCapResponseDtoToModel(coinCapSymbolDto.data()[i]);
        }

        coinCapSymbol.setData(coinCapResponses);
        return coinCapSymbol;
    }

}

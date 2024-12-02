package com.example.CryptoPortolio.model.mappers;

import com.example.CryptoPortolio.model.CryptoAsset;
import com.example.CryptoPortolio.model.dto.CrytoAssetDto;

import java.util.List;
import java.util.stream.Collectors;

public class CryptoAssetDtoToCryptoAssetMapper {

    public static List<CryptoAsset> mapDtoToModel(List<CrytoAssetDto> crytoAssetDtos, Long clientId) {
        return crytoAssetDtos.stream()
                .map(asset -> new CryptoAsset(asset.quantity(), asset.price(), asset.symbol(), clientId))
                .collect(Collectors.toList());
    }
}

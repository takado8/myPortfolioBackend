package com.takado.myportfoliobackend.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoinGeckoResponse {
    private Map<String, Map<String, Double>> responseMap;

}

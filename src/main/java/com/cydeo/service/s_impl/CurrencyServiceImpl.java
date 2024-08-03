package com.cydeo.service.s_impl;

import com.cydeo.client.CurrencyClient;
import com.cydeo.dto.response.CurrencyDto;
import com.cydeo.dto.response.ExchangeRates;
import com.cydeo.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyClient currencyClient;

    @Override
    public ExchangeRates getExchangeRates() {
        CurrencyDto response = currencyClient.getCurrency();
        Map<String, Double> rates = response.getUsd();

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setEuro(rates.get("eur"));
        exchangeRates.setJapaneseYen(rates.get("jpy"));
        exchangeRates.setCanadianDollar(rates.get("cad"));
        exchangeRates.setIndianRupee(rates.get("inr"));
        exchangeRates.setBritishPound(rates.get("gbp"));

        return exchangeRates;


    }
}

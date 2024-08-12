package com.cydeo.service.s_impl;

import com.cydeo.client.CurrencyClient;
import com.cydeo.dto.currency_api.CurrencyRates;
import com.cydeo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

@Service

public class DashboardServiceImpl implements DashboardService {

    private final CurrencyClient currencyClient;

    public DashboardServiceImpl(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Override
    public CurrencyRates getExchangeRates() {
        CurrencyRates currencyRates = currencyClient.getAllCurrencies().getCurrencyRates();
        currencyRates.setEuro(currencyRates.getEuro().setScale(4, RoundingMode.HALF_UP));
        currencyRates.setBritishPound(currencyRates.getBritishPound().setScale(4, RoundingMode.HALF_UP));
        currencyRates.setCanadianDollar(currencyRates.getCanadianDollar().setScale(4, RoundingMode.HALF_UP));
        currencyRates.setJapaneseYen(currencyRates.getJapaneseYen().setScale(4, RoundingMode.HALF_UP));
        currencyRates.setIndianRupee(currencyRates.getIndianRupee().setScale(4, RoundingMode.HALF_UP));

        return currencyRates;
    }
}

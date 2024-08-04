package com.cydeo.service.s_impl;

import com.cydeo.client.CurrencyClient;
import com.cydeo.dto.currency_api.CurrencyRates;
import com.cydeo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public class DashboardServiceImpl implements DashboardService {

    private final CurrencyClient currencyClient;

    public DashboardServiceImpl(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Override
    public CurrencyRates getExchangeRates() {
        return currencyClient.getAllCurrencies().getCurrencyRates();
    }
}

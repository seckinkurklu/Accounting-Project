package com.cydeo.service;

import com.cydeo.dto.currency_api.CurrencyRates;

public interface DashboardService {

    CurrencyRates getExchangeRates();
}

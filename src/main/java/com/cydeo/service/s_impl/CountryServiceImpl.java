package com.cydeo.service.s_impl;

import com.cydeo.client.CountryClient;
import com.cydeo.dto.CountryResponse;
import com.cydeo.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
@Service
public class CountryServiceImpl implements CountryService {
    private String apiToken ="7QJ8wv0CrgbSXvI7UDVoJj5paTbA-5MKzB9mfBJliC5FpndbaaBUwmLOH2PsWtsb6yA";
    private String authToken =
private final CountryClient countryClient;

    public CountryServiceImpl(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    @Override
    public List<String> getCountries() {
        List<CountryResponse> countries =
                countryClient.getCountries("Bearer " + authToken,
                        "application/json");

        countries.sort(Comparator.comparing(CountryResponse::getCountryName));
        CountryResponse usa = countries.stream()
                .filter(c ->
                        c.getCountryName().equals("United States"))
                .findFirst()
                .orElse(null);
        if (usa !=null) {
            countries.remove(usa);
        }
        return countries.stream().map(p->
                p.getCountryName()).toList();

    }
}

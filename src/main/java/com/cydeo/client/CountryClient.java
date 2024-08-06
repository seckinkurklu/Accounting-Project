package com.cydeo.client;

import com.cydeo.dto.CountryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(url = " https://www.universal-tutorial.com/rest-apis/free-rest-api-for-country-state-city" , name = "COUNTRY-CLIENT")
public interface CountryClient {
    @GetMapping("/countries")
    List<CountryResponse> getCountries(@RequestHeader("Authorization") String
                                               token, @RequestHeader("Accept") String accept );


}

package com.cydeo.client;

import com.cydeo.dto.CountryInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(url = "https://freetestapi.com/api/v1" , name = "COUNTRY-CLIENT")
public interface CountryClient {
    @GetMapping("/countries")
    ResponseEntity< List<CountryInfoDto>> getCountries();


}

package com.baedal.dutchpay.controller;

import com.baedal.dutchpay.service.geoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class geocoderController {

    private final geoService geoService;

    //get each longitude and latitude of location
    @GetMapping("/get/location")
    public List<Double> getGeoDetails(@RequestParam String address) {
        return geoService.getGeoDetails(address);

    }

    //get the distance
    @GetMapping("/get/distance")
    public double getDistance(@RequestParam String address1,
                              @RequestParam String address2,
                              @RequestParam String unit){

        return geoService.getDistance(address1, address2, unit);
    }

    //get the midpoint

}

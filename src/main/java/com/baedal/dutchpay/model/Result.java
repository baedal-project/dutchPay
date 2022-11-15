package com.baedal.dutchpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;


//gonna map json datas and java entity

public class Result {

    //address, geometry in json titled 'result'

    //formatted address
    @JsonProperty("formatted_address")
    private String address;

    @JsonProperty("geometry")
    private Geometry geometry;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    //    {
//        "results": [
//        {
//            "address_components": [
//            {
//                "long_name": "Seoul",
//                    "short_name": "Seoul",
//                    "types": [
//                "administrative_area_level_1",
//                        "political"
//                    ]
//            },
//            {
//                "long_name": "South Korea",
//                    "short_name": "KR",
//                    "types": [
//                "country",
//                        "political"
//                    ]
//            }
//            ],
//            "formatted_address": "Seoul, South Korea",
//            "geometry": {
//                  "bounds": {
//                      "northeast": {
//                            "lat": 37.7017495,
//                            "lng": 127.1835899
//                  },
//                       "southwest": {
//                             "lat": 37.4259627,
//                             "lng": 126.7645827
//                  }
//              },
//                  "location": {
//                        "lat": 37.566535,
//                        "lng": 126.9779692
//            },
//            "location_type": "APPROXIMATE",
//                    "viewport": {
//                "northeast": {
//                    "lat": 37.7017495,
//                            "lng": 127.1835899
//                },
//                "southwest": {
//                    "lat": 37.4259627,
//                            "lng": 126.7645827
//                }
//            }
//        },
//            "place_id": "ChIJzzlcLQGifDURm_JbQKHsEX4",
//                "types": [
//            "administrative_area_level_1",
//                    "political"
//            ]
//        }
//    ],
//        "status": "OK"
//    }
}

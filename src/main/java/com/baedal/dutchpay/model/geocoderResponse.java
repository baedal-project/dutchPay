package com.baedal.dutchpay.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class geocoderResponse {

    //gonna map json datas and java entity
    //results array like json
    @JsonProperty("results")    //map the keys [json, entity]
    private Result[] result;

    public Result[] getResult() {
        return result;
    }

    public void setResult(Result[] result) {
        this.result = result;
    }

//        {
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
//                "geometry": {
//            "bounds": {
//                "northeast": {
//                    "lat": 37.7017495,
//                            "lng": 127.1835899
//                },
//                "southwest": {
//                    "lat": 37.4259627,
//                            "lng": 126.7645827
//                }
//            },
//            "location": {
//                "lat": 37.566535,
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

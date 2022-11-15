package com.baedal.dutchpay.service;

import com.baedal.dutchpay.model.geocoderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
public class geoService {

    private final Object API_KEY = "AIzaSyD_1As859J3PUFIf_wqbJK-eChSQJ5ffS8";
//    private final Location location;

    public List<Double> getGeoDetails(String address){
        return getLatLng(address);
    }


    //min distance between 2 points
    public double getDistance(String address1, String address2, String unit) {
        List<Double> LatLng1 = getGeoDetails(address1);
        List<Double> LatLng2 = getGeoDetails(address2);
        System.out.println(LatLng1);
        System.out.println(LatLng2);

        //출발지(origin)
        double lat1 = LatLng1.get(0);
        double lng1 = LatLng1.get(1);
        System.out.println(lat1);
        System.out.println(lng1);

        //목적지(des)
        double lat2 = LatLng2.get(0);
        double lng2 = LatLng2.get(1);
        System.out.println(lat2);
        System.out.println(lng2);

        return distance(lat1, lng1, lat2, lng2, unit);
    }

    //=============================Details==========================================

    //get LatLng
    private List<Double> getLatLng(String address){
        //public geocoderResponse getGeoDetails(String address) {
        //public double getGeoDetails(String address) {

        UriComponents uri = UriComponentsBuilder.newInstance()
                //encoding https://maps.googleapis.com/maps/api/geocode/json?address=Seoul&region=kr&key=AIzaSyD_1As859J3PUFIf_wqbJK-eChSQJ5ffS8
                .scheme("https")
                .host("maps.googleapis.com")
                .path("maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("address", address)
                .build();

        System.out.println(uri.toUriString());
        //converting json into Response Class
        ResponseEntity<geocoderResponse> response =
                new RestTemplate().getForEntity(uri.toUriString(), geocoderResponse.class);

        ArrayList<Double> LatLng = new ArrayList<>();
        LatLng.add(Objects.requireNonNull(response.getBody()).getResult()[0].getGeometry().getLocation().getLat());
        LatLng.add(Objects.requireNonNull(response.getBody()).getResult()[0].getGeometry().getLocation().getLng());
        return LatLng;
    }


    /*두 지점간의 거리 계산
    @param lat1 지점 1위도
    @param lng1 지점 1경도
    @param lat1 지점 1위도
    @param lng1 지점 1경도
    @param unit 거리 표출단위
     */
    //method1) Spherical Law of Cosines(구면 코사인 법칙)
    //아주 작은 거리일 경우 정뱡형 근사가 better
    private static double distance(double lat1, double lng1, double lat2, double lng2, String unit){

        //Spherical Law of Cosines
        //cost = cosc*cosb + sinc*sinb*cosA

        //1) find A
        double theta = lng1 - lng2;
        System.out.println("theta is" + theta);

        //2) find c,b
        double c = Math.abs(90 - lat1);
        double b = Math.abs(90 - lat2);
//        System.out.println("c is" + c);
//        System.out.println("b is" + b);

//        System.out.println(Math.toRadians(c));
//        System.out.println(Math.toRadians(b));
//        System.out.println(Math.cos(Math.toRadians(c)));
//        System.out.println(Math.cos(Math.toRadians(b)));
//        System.out.println(Math.sin(Math.toRadians(b)));
//        System.out.println(Math.sin(Math.toRadians(c)));

        //3) find cost
        double cosT = Math.cos(Math.toRadians(c)) * Math.cos(Math.toRadians(b))
                       + Math.sin(Math.toRadians(c)) * Math.sin(Math.toRadians(b)) * Math.cos(Math.toRadians(theta));
        System.out.println("cosT is" + cosT);

        //4) find angle 'a'
        double a = Math.toDegrees(Math.acos(cosT));
        System.out.println("a is" + a);

        //5) find the distance using ratio
        //Assume Earth circumference(지구 둘레) = 46250km
        //ratio -> 360:a= 24901.4505:length (69.1706959 6dp rounding) mile default unit
        double length = 69.1706959 * a;
        System.out.println("length is" + length);

        if (unit == "meter"){
            length = length * 1609.344;
        } else if (unit == "kilometer"){
            length = length *  1.609344;
        }

        return length;  //result 66.825km(actual length: 107km)

    }

    //converts decimal degrees to radians
    private static double deg2rad(double deg){
        return (deg * Math.PI / 180);
    }

    //converts radians to decimal degrees
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }

    //method2) haversine
    //method3) Equirectangular approximation(정방형 근사)

}

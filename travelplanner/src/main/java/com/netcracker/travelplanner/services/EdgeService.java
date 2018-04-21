package com.netcracker.travelplanner.services;

import com.google.gson.Gson;
import com.netcracker.travelplanner.model.IataCodeAndTimezone;
import com.netcracker.travelplanner.model.entities.Point;
import com.netcracker.travelplanner.model.exceptions.KiwiIATACodeException;
import com.netcracker.travelplanner.model.googleDist.GoogleDistance;
import com.netcracker.travelplanner.model.googleGeocode.GoogleGeocode;
import com.netcracker.travelplanner.model.yandexCode.YandexCode;
import com.netcracker.travelplanner.model.newKiwi.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EdgeService {

    public static IataCodeAndTimezone getIataCodeAndTimezone(double latitude, double longitude, String name) throws KiwiIATACodeException {

        String query = "https://api.skypicker.com/locations/?type=radius&" +
                "lat=" +
                latitude +
                "&lon=" +
                longitude +
                "&radius=100&locale=en-US&location_types=city&sort=rank";

        String iataCityCode = null;
        String timezone = null;
        Gson gson = new Gson();
        KiwiStations kiwiStations = null;
        try {
            kiwiStations = gson.fromJson(getStreamReaderFromUrl(query), KiwiStations.class);
        } catch (Exception ex){
            throw new KiwiIATACodeException(ex);
        }
        if(kiwiStations != null && kiwiStations.getLocations() != null && kiwiStations.getLocations().size() > 0) {
            for (Airport airport : kiwiStations.getLocations()) {
                if (airport.getName().equals(name)){
                    iataCityCode = airport.getCode();
                    timezone = airport.getTimezone();
                }
            }
            if (iataCityCode == null){
                iataCityCode = kiwiStations.getLocations().get(0).getCode();
                timezone = kiwiStations.getLocations().get(0).getTimezone();
            }
        }
        else {
            throw new KiwiIATACodeException();
        }

        return new IataCodeAndTimezone(iataCityCode, timezone);
    }

    public static boolean isGlobalRoute(double latitudeFrom, double longitudeFrom, double latitudeTo, double longitudeTo){

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins="+
                latitudeFrom + "," + longitudeFrom +
                "&destinations=" +
                latitudeTo + "," + longitudeTo +
                "&key=AIzaSyCyX3-2sgnd85ohK0PWEeHAlnOo8Bc0Ako";

        Gson gson = new Gson();

        GoogleDistance googleDistance = gson.fromJson(getStreamReaderFromUrl(url), GoogleDistance.class);

        if(googleDistance!=null){
            if(googleDistance.getRows().get(0).getElements().get(0).getStatus().equals("ZERO_RESULTS")){
                return true;
            }

            int distance =  googleDistance.getRows().get(0).getElements().get(0).getDistance().getValue();
            return distance >= 600000;
        }

        return false;
    }

    @Deprecated
    public static String getYandexCode(double latitude, double longitude){

        String url = "https://api.rasp.yandex.net/v3.0/nearest_settlement/?apikey=64d2c4dc-e05a-4574-b51a-bdc03b2bc8a3&format=json&lat=" +
                latitude + "&lng=" + longitude + "&distance=50";

        String yandexCode = null;

        Gson gson = new Gson();

        YandexCode code = gson.fromJson(getStreamReaderFromUrl(url), YandexCode.class);

        if(code!=null){

            yandexCode =  code.getCode();
        }

        return yandexCode;
    }

    public static String getRussianName(String name){
        String url = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyBpOm2tBurzyefOG_hBFEXQIkLbkZpSvws" +
                "&language=ru&address=" + name.replace(" ", "%20");

        System.out.println(url);

        String russianName = null;
        Gson gson = new Gson();
        GoogleGeocode geocode = gson.fromJson(getStreamReaderFromUrl(url), GoogleGeocode.class);

        if (geocode != null){
            russianName = geocode.getResults().get(0).getAddressComponents().get(0).getLongName().replace("ё", "e");
        }

        return russianName;
    }

    private static InputStreamReader getStreamReaderFromUrl(String url){

        InputStreamReader reader = null;
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:57.0)");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();

            reader = new InputStreamReader(connection.getInputStream());
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return reader;
    }

    public static List<Point> getCities(String iataCode, double latitude, double longitude){

        String url = "https://api.skypicker.com/locations/?type=radius&" +
                "lat=" +
                latitude +
                "&lon=" +
                longitude +
                "&radius=500" +
                "&location_types=airport" +
                "&sort=rank";

        Gson gson = new Gson();

        List<MyPoint> myPointList = new ArrayList<>();

        KiwiStations kiwiStations = gson.fromJson(getStreamReaderFromUrl(url), KiwiStations.class);

        kiwiStations.getLocations()
                .stream()
                .filter(loc->loc.getType().equals("airport"))
                .forEach(location -> myPointList.add(new MyPoint(
                        location.getId()
                        ,location.getCode()
                        ,location.getName()
                        ,location.getTimezone()
                        ,location.getType()
                        ,location.getLocation().getLat()
                        ,location.getLocation().getLon()
                        ,location.getCity().getCountry().getName()
                        ,location.getCity().getCountry().getCode()
                        ,location.getCity().getName()
                        ,location.getCity().getCode())));

        List<MyPoint> list =  myPointList
                .stream()
                .filter(a -> !a.getCityCode().equals(iataCode))
                .filter(distinctByKey(MyPoint::getCityCode))
                .limit(5)
                .collect(Collectors.toList());

        List<Point> points = new ArrayList<>();

        list.forEach(myPoint -> points.add(new Point(myPoint.getCityName()
                , myPoint.getLat()
                , myPoint.getLon()
                , myPoint.getCityCode()
                , ""
                , ""
                , getRussianName(myPoint.getCityName())
                , myPoint.getTimezone())));

        return points;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private EdgeService() {
    }

}

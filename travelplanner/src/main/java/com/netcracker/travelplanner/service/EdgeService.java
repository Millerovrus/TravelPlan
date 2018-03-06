package com.netcracker.travelplanner.service;

import com.google.gson.Gson;
import com.netcracker.travelplanner.entities.Point;
import com.netcracker.travelplanner.entities.googleDist.GoogleDistance;
import com.netcracker.travelplanner.entities.yandexCode.YandexCode;
import com.netcracker.travelplanner.entities.newKiwi.KiwiStations;
import com.netcracker.travelplanner.entities.newKiwi.MyPoint;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EdgeService {

    public static String getIataCode(double latitude, double longitude){

        String query = "https://api.skypicker.com/locations/?type=radius&" +
                "lat=" +
                latitude +
                "&lon=" +
                longitude +
                "&radius=20&locale=en-US&location_types=city&limit=2&sort=rank";

        String iataCityCode = "no Iata Code";

        Gson gson = new Gson();

        KiwiStations kiwiStations = gson.fromJson(getStreamReaderFromUrl(query), KiwiStations.class);

        if(kiwiStations!=null) {
           iataCityCode = kiwiStations.getLocations().get(0).getCode();
        }

        return iataCityCode;

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

         int distance =  googleDistance.getRows().get(0).getElements().get(0).getDistance().getValue();

         return distance >= 600000;
        }

        return false;
    }

    public static String getYandexCode(double latitude, double longitude){

        String url = "https://api.rasp.yandex.net/v3.0/nearest_settlement/?apikey=64d2c4dc-e05a-4574-b51a-bdc03b2bc8a3&format=json&lat=" +
                latitude + "&lng=" + longitude + "&distance=50";

        String yandexCode = "no Yandex Code";

        Gson gson = new Gson();

        YandexCode code = gson.fromJson(getStreamReaderFromUrl(url), YandexCode.class);

        if(code!=null){

            yandexCode =  code.getCode();
        }

        return yandexCode;
    }

    public static InputStreamReader getStreamReaderFromUrl(String url){

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
                "&radius=600" +
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

        list.forEach(myPoint -> points.add(new Point(myPoint.getName(), myPoint.getLat(), myPoint.getLon(),myPoint.getCode(),getYandexCode(myPoint.getLat(),myPoint.getLon()))));

        return points;

    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private EdgeService() {
    }

}

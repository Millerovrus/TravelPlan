package com.netcracker.travelplanner.service;

import com.google.gson.Gson;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.entities.kiwi.KiwiFlights;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.newKiwi.KiwiStations;
import com.netcracker.travelplanner.entities.newKiwi.MyAirport;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KiwiService {

    private KiwiFlights getKiwiFlightsFromUrl(String urlQueryString) {

        KiwiFlights kiwiFlights = null;

        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:57.0)");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());

            Gson gson = new Gson();

            kiwiFlights = gson.fromJson(reader, KiwiFlights.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return kiwiFlights;
    }

    public List<Edge> getEdgesFlights(String from
            , String to
            , LocalDate dateFrom
            , LocalDate dateTo
            , String codeFrom
            , String codeTo){


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder builder = new StringBuilder();
        String url =
        builder.append("https://api.skypicker.com/flights?flyFrom=")
                .append(codeFrom)
                .append("&to=")
                .append(codeTo)
                .append("&dateFrom=")
                .append(dateFrom.format(formatter))
                .append("&dateTo=")
                .append(dateTo.format(formatter))
                .append("&partner=picky&partner_market=us&curr=RUB")
                .toString();

        Date dateNow = new Date();
        KiwiFlights kiwiFlights = getKiwiFlightsFromUrl(url);
        String currency = kiwiFlights.getCurrency();
        List<Edge> listOfEdges = new ArrayList<>();

        kiwiFlights.getData().forEach(l -> listOfEdges.add(new Edge(dateNow
                ,from
                ,to
                , "plane"
                ,(double)l.getDuration().getTotal()
                ,(double)l.getPrice()
                ,l.getDistance()
                ,Date.from(Instant.ofEpochSecond(l.getATime()))
                ,Date.from(Instant.ofEpochSecond(l.getDTime()))
                ,currency
                ,codeFrom
                ,codeTo)));

        return listOfEdges;
    }

    public List<MyAirport> getAirportsByRadius(int radius
                                            ,double latitude
                                            ,double longitude){
        String query = "https://api.skypicker.com/locations/?type=radius&" +
                "lat=" +
                latitude +
                "&lon=" +
                longitude +
                "&radius=" +
                radius +
                "&location_types=airport" +
                "&sort=rank";

        List<MyAirport> myAirportList = new ArrayList<>();

        try {
            URL url = new URL(query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:57.0)");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());

            Gson gson = new Gson();

            KiwiStations kiwiStations = gson.fromJson(reader, KiwiStations.class);

            kiwiStations.getLocations()
                    .stream()
                    .filter(loc->loc.getType().equals("airport"))
                    .forEach(location -> myAirportList.add(new MyAirport(
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


        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return myAirportList;

    }

}

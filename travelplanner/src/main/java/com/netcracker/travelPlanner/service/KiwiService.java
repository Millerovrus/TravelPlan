package com.netcracker.travelPlanner.service;

import com.google.gson.Gson;
import com.netcracker.travelPlanner.entities.kiwi.KiwiFlights;
import com.netcracker.travelPlanner.entities.Edge;
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

    public List<Edge> getEdgesFlights(String flyFrom
                                      ,String flyTo
                                      ,LocalDate dateFrom
                                      ,LocalDate dateTo){


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder builder = new StringBuilder();
        String url =
        builder.append("https://api.skypicker.com/flights?flyFrom=")
                .append(flyFrom)
                .append("&to=")
                .append(flyTo)
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
                ,l.getCityFrom()
                ,l.getCityTo()
                ,"plane"
                ,(double)l.getDuration().getTotal()
                ,(double)l.getPrice()
                ,l.getDistance()
                ,Date.from(Instant.ofEpochSecond(l.getATime()))
                ,Date.from(Instant.ofEpochSecond(l.getDTime()))
                ,currency)));

        return listOfEdges;
    }

}

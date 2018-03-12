package com.netcracker.travelplanner.api;

import com.google.gson.Gson;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Point;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.entities.kiwi.KiwiFlights;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class KiwiApi implements ApiInterface {

    @Override
    public List<Edge> findEdgesFromTo(Point from, Point to, LocalDate date) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String url =
                "https://api.skypicker.com/flights?flyFrom=" +
                        from.getIataCode() +
                        "&to=" +
                        to.getIataCode() +
                        "&dateFrom=" +
                        date.format(formatter) +
                        "&dateTo=" +
                        date.format(formatter) +
                        "&partner=picky&partner_market=us&curr=RUB";

        List<Edge> result = new ArrayList<>();
       // List<Edge> listOfEdges = new ArrayList<>();

        if(! from.getIataCode().equals(to.getIataCode())) {

            Date dateNow = new Date();
            KiwiFlights kiwiFlights = getKiwiFlightsFromUrl(url);
            String currency = kiwiFlights.getCurrency();

            kiwiFlights.getData().forEach(l -> listOfEdges.add(new Edge(dateNow
                    , from.getName()
                    , to.getName()
                    , "plane"
                    , (double) l.getDuration().getTotal()
                    , (double) l.getPrice()
                    , l.getDistance()
                    , LocalDateTime.ofEpochSecond(l.getDTime(), 0, ZoneOffset.UTC)
                    , LocalDateTime.ofEpochSecond(l.getATime(), 0, ZoneOffset.UTC)
                    , currency
                    , from.getIataCode()
                    , to.getIataCode()
                    , from.getLatitude()
                    , from.getLongitude()
                    , to.getLatitude()
                    , to.getLongitude())));

            if (!listOfEdges.isEmpty()) {
                result.add(filterEdgeByTypes(listOfEdges, RouteType.cheap));
                result.add(filterEdgeByTypes(listOfEdges, RouteType.optimal));
                result.add(filterEdgeByTypes(listOfEdges, RouteType.comfort));
                result.add(filterEdgeByTypes(listOfEdges, RouteType.fastest));
                result.add(filterEdgeByTypes(listOfEdges, RouteType.cheapest));
            }
        }
        return result;

    }

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

    private Edge filterEdgeByTypes(List<Edge> edgeList, RouteType type){

        Edge edge = null;
        edgeList.forEach(l->l.setEdgeType(type));
        edgeList.forEach(l->l.setData(type));
        try {
            edge = (Edge) edgeList.stream().min(Comparator.comparingDouble(Edge::getWeight)).get().clone();
        } catch (CloneNotSupportedException e) {

            e.printStackTrace();
        }

        return edge;
    }

}

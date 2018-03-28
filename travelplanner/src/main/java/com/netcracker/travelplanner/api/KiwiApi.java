package com.netcracker.travelplanner.api;

import com.google.gson.Gson;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Point;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class KiwiApi implements ApiInterface {

    @Override
    public List<Edge> findEdgesFromTo(Point from, Point to, LocalDate date, int numberOfAdults, int numberOfChildren) {

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
                        "&adults=" +
                        numberOfAdults +
                        "&children=" +
                        numberOfChildren +
                        "&infants=0&partner=picky&partner_market=us&curr=RUB";
        List<Edge> edgeList = new ArrayList<>();

        if(! from.getIataCode().equals(to.getIataCode())) {

            Date dateNow = new Date();
            KiwiFlights kiwiFlights = getKiwiFlightsFromUrl(url);
            String currency = kiwiFlights.getCurrency();

            kiwiFlights.getData().forEach(l ->
            {
                List<Point> transitPoints = new LinkedList<>();

                if(l.getRoute().size()>1){

                    transitPoints.add(new Point(l.getRoute().get(0).getCityFrom()
                            ,l.getRoute().get(0).getLatFrom()
                            ,l.getRoute().get(0).getLngFrom()
                            ,l.getRoute().get(0).getFlyFrom()));

                    l.getRoute().forEach(route -> {
                        transitPoints.add(new Point(route.getCityTo()
                                ,route.getLatTo()
                                ,route.getLngTo()
                                ,route.getFlyTo()
                                ));
                    });
                }

                Edge edge = new Edge();
                edge.setCreationDate(dateNow);
                edge.setStartPoint(from.getName());
                edge.setDestinationPoint(to.getName());
                edge.setTransportType("plane");
                edge.setDuration((double) l.getDuration().getTotal());
                edge.setCost((double) l.getPrice());
                edge.setStartDate(LocalDateTime.ofEpochSecond(l.getDTime(), 0, ZoneOffset.UTC));
                edge.setEndDate(LocalDateTime.ofEpochSecond(l.getATime(), 0, ZoneOffset.UTC));
                edge.setCurrency(currency);
                edge.setNumberOfTransfers((byte) (l.getRoute().size()));
                edge.setLatitudeFrom(from.getLatitude());
                edge.setLongitudeFrom(from.getLongitude());
                edge.setLatitudeTo(to.getLatitude());
                edge.setLongitudeTo(to.getLongitude());
                edge.setStartPointPoint(new Point(from.getName()
                                ,from.getLatitude()
                                ,from.getLongitude()
                                ,from.getIataCode()
                                ,from.getYandexCode()
                                ,l.getFlyFrom()));
                edge.setEndPointPoint(new Point(to.getName()
                                ,to.getLatitude()
                                ,to.getLongitude()
                                ,to.getIataCode()
                                ,to.getYandexCode()
                                ,l.getFlyTo()));
                edge.setTransitPoints(transitPoints);

                edgeList.add(edge);

            });
        }
        return edgeList;
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
}

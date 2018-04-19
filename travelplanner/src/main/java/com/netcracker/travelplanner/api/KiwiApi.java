package com.netcracker.travelplanner.api;

import com.google.gson.Gson;
import com.netcracker.travelplanner.model.exceptions.KiwiException;
import com.netcracker.travelplanner.model.entities.Edge;
import com.netcracker.travelplanner.model.entities.Point;
import com.netcracker.travelplanner.model.entities.TransitEdge;
import com.netcracker.travelplanner.model.kiwi.KiwiFlights;

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
    public List<Edge> findEdgesFromTo(Point from, Point to, LocalDate date, int numberOfAdults, int numberOfChildren, int numberOfInfants) throws KiwiException {

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
                        "&infants=" +
                        numberOfInfants +
                        "&partner=picky&partner_market=us&curr=RUB";
        List<Edge> edgeList = new ArrayList<>();

        if(! from.getIataCode().equals(to.getIataCode())) {

            Date dateNow = new Date();
            KiwiFlights kiwiFlights = getKiwiFlightsFromUrl(url);
            String currency = kiwiFlights.getCurrency();
            kiwiFlights.getData().forEach(l ->
            {
                List<TransitEdge> transitEdges = new LinkedList<>();

                if(l.getRoute().size() > 1){

                    l.getRoute().forEach(route -> {
                    transitEdges.add(new TransitEdge(new Point(route.getCityFrom()
                                ,route.getLatFrom()
                                ,route.getLngFrom()
                                ,route.getFlyFrom())
                            ,new Point(route.getCityTo()
                                ,route.getLatTo()
                                ,route.getLngTo()
                                ,route.getFlyTo())
                            ,LocalDateTime.ofEpochSecond(route.getDTime(), 0, ZoneOffset.UTC)
                            ,LocalDateTime.ofEpochSecond(route.getATime(), 0, ZoneOffset.UTC)));
                    });

                    transitEdges.get(0).getStartPoint().setLatitude(from.getLatitude());
                    transitEdges.get(0).getStartPoint().setLongitude(from.getLongitude());
                    transitEdges.get(transitEdges.size() - 1).getEndPoint().setLatitude(to.getLatitude());
                    transitEdges.get(transitEdges.size() - 1).getEndPoint().setLongitude(to.getLongitude());
                } else {
                    transitEdges.add(new TransitEdge(
                             new Point(from.getName()
                                    ,from.getLatitude()
                                    ,from.getLongitude()
                                    ,from.getIataCode()
                                    ,from.getYandexCode()
                                    ,l.getFlyFrom()
                                    ,from.getRussianName()
                                    ,from.getTimezone())
                            ,new Point(to.getName()
                                    ,to.getLatitude()
                                    ,to.getLongitude()
                                    ,to.getIataCode()
                                    ,to.getYandexCode()
                                    ,l.getFlyTo()
                                    ,to.getRussianName()
                                    ,to.getTimezone())
                            ,LocalDateTime.ofEpochSecond(l.getDTime(), 0, ZoneOffset.UTC)
                            ,LocalDateTime.ofEpochSecond(l.getATime(), 0, ZoneOffset.UTC)

                    ));
                }

                Edge edge = new Edge();
                edge.setCreationDate(dateNow);
                edge.setTransportType("plane");
                edge.setDuration((double) l.getDuration().getTotal());
                edge.setCost((double) l.getPrice());
                edge.setStartDate(LocalDateTime.ofEpochSecond(l.getDTime(), 0, ZoneOffset.UTC));
                edge.setEndDate(LocalDateTime.ofEpochSecond(l.getATime(), 0, ZoneOffset.UTC));
                edge.setCurrency(currency);
                edge.setNumberOfTransfers(l.getRoute().size());
                edge.setStartPoint(new Point(from.getName()
                                ,from.getLatitude()
                                ,from.getLongitude()
                                ,from.getIataCode()
                                ,from.getYandexCode()
                                ,l.getFlyFrom()
                                ,from.getRussianName()
                                ,from.getTimezone()));
                edge.setEndPoint(new Point(to.getName()
                                ,to.getLatitude()
                                ,to.getLongitude()
                                ,to.getIataCode()
                                ,to.getYandexCode()
                                ,l.getFlyTo()
                                ,to.getRussianName()
                                ,to.getTimezone()));

                edge.setTransitEdgeList(transitEdges);
                edge.setPurchaseLink(l.getDeepLink());

                edgeList.add(edge);

            });
        }
        return edgeList;
    }

    private KiwiFlights getKiwiFlightsFromUrl(String urlQueryString) throws KiwiException {

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
            throw new KiwiException(ex);
        }

        return kiwiFlights;
    }
}

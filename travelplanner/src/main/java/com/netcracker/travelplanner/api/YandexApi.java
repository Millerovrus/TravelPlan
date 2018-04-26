package com.netcracker.travelplanner.api;

import com.google.gson.Gson;
import com.netcracker.travelplanner.model.entities.Edge;
import com.netcracker.travelplanner.model.entities.Point;
import com.netcracker.travelplanner.model.entities.TransitEdge;
import com.netcracker.travelplanner.model.yandex.YandexRasp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class YandexApi implements ApiInterface {

    @Override
    public List<Edge> findEdgesFromTo(Point from, Point to, LocalDate date, int numberOfAdults, int numberOfChildren, int numberOfInfants) {

        Date dateNow = new Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String url = "https://api.rasp.yandex.net/v3.0/search/?apikey=" +
                "64d2c4dc-e05a-4574-b51a-bdc03b2bc8a3" +
                "&format=json&from=" +
                from.getIataCode() +
                "&to=" +
                to.getIataCode() +
                "&lang=ru_RU&date=" +
                date.format(formatter) +
                "&system=iata";

        List<Edge> edgeList = new ArrayList<>();
        if(! from.getIataCode().equals(to.getIataCode())) {
            YandexRasp yandexRasp = getYandexRaspFromUrl(url);
            yandexRasp.getSegments()
                    .stream()
                    .filter(l -> l.getTicketsInfo().getPlaces().size() != 0)
                    .forEach(l -> {

                        List<TransitEdge> transitEdges = new LinkedList<>();
                        transitEdges.add(new TransitEdge(
                                new Point(from.getName()
                                        ,from.getLatitude()
                                        ,from.getLongitude()
                                        ,from.getIataCode()
                                        ,l.getFrom().getCode()
                                        ,from.getRussianName()
                                        ,from.getTimezone())
                                ,new Point(to.getName()
                                        ,to.getLatitude()
                                        ,to.getLongitude()
                                        ,to.getIataCode()
                                        ,l.getTo().getCode()
                                        ,to.getRussianName()
                                        ,to.getTimezone())
                                ,LocalDateTime.parse(l.getDeparture(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                                ,LocalDateTime.parse(l.getArrival(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                        ));

                        Edge edge = new Edge();
                        edge.setCreationDate(dateNow);
                        edge.setTransportType(l.getThread().getTransportType());
                        edge.setDuration(l.getDuration());
                        edge.setCost(((double) l.getTicketsInfo().getPlaces().get(0).getPrice().getWhole()) * (numberOfAdults + numberOfChildren));
                        edge.setStartDate(LocalDateTime.parse(l.getDeparture(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                        edge.setEndDate(LocalDateTime.parse(l.getArrival(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                        edge.setCurrency("RUB");
                        edge.setNumberOfTransfers(1);
                        edge.setStartPoint(new Point(from.getName()
                                ,from.getLatitude()
                                ,from.getLongitude()
                                ,from.getIataCode()
                                ,l.getFrom().getCode()
                                ,from.getRussianName()
                                ,from.getTimezone()));
                        edge.setEndPoint(new Point(to.getName()
                                ,to.getLatitude()
                                ,to.getLongitude()
                                ,to.getIataCode()
                                ,l.getTo().getCode()
                                ,to.getRussianName()
                                ,to.getTimezone()));

                        edge.setTransitEdgeList(transitEdges);
                        edge.setPurchaseLink("https://rasp.yandex.ru/search/?fromId=" +
                                l.getFrom().getCode() +
                                "&toId=" +
                                l.getTo().getCode() +
                                "&transportType=" +
                                l.getThread().getTransportType() +
                                "&when=" +
                                date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        );

                        edgeList.add(edge);
                    });
        }
        return edgeList;
    }

    private YandexRasp getYandexRaspFromUrl(String urlQueryString){

        YandexRasp yandexRasp = null;

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

            yandexRasp = gson.fromJson(reader, YandexRasp.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return yandexRasp;
    }
}
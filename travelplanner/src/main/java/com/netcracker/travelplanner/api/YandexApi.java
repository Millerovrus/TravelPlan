package com.netcracker.travelplanner.api;

import com.google.gson.Gson;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.entities.yandex.YandexRasp;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class YandexApi implements ApiInterface{

    @Override
    public List<Edge> findEdgesFromTo(Point from, Point to, LocalDate date) {

        Date dateNow = new Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder();
        String url = builder.append("https://api.rasp.yandex.net/v3.0/search/?apikey=")
                .append("64d2c4dc-e05a-4574-b51a-bdc03b2bc8a3")
                .append("&format=json&from=")
                .append(from.getIataCode())
                .append("&to=")
                .append(to.getIataCode())
                .append("&lang=ru_RU&date=")
                .append(date.format(formatter))
                .append("&system=iata")
                .toString();

        YandexRasp yandexRasp = getYandexRaspFromUrl(url);

        List<Edge> result = new ArrayList<>();
        List<Edge> edgeList = new ArrayList<>();

        yandexRasp.getSegments()
                .stream()
                .filter(l -> l.getTicketsInfo().getPlaces().size()!=0)
                .forEach(l -> edgeList.add(new Edge(dateNow
                        ,from.getName()
                        ,to.getName()
                        ,l.getThread().getTransportType()
                        ,l.getDuration()
                        ,(double)l.getTicketsInfo().getPlaces().get(0).getPrice().getWhole()
                        ,null
                        , LocalDateTime.parse(l.getDeparture(),DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        , LocalDateTime.parse(l.getArrival(),DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        ,"RUB"
                        , from.getIataCode()
                        , from.getIataCode())));

        if (!edgeList.isEmpty()) {
            result.add(filterEdgeByTypes(edgeList, RouteType.cheap));
            result.add(filterEdgeByTypes(edgeList, RouteType.optimal));
            result.add(filterEdgeByTypes(edgeList, RouteType.comfort));
            result.add(filterEdgeByTypes(edgeList, RouteType.fastest));
            result.add(filterEdgeByTypes(edgeList, RouteType.cheapest));
        }
        return result;

    }

    @Override
    public List<Edge> findEdgesAllToOne(List<Point> pointListFrom, Point to, LocalDate date) {

        List<Edge> edgeList = new ArrayList<>();

        pointListFrom.forEach(point -> edgeList.addAll(findEdgesFromTo(point, to, date)));

        return edgeList;
    }

    @Override
    public List<Edge> findEdgesOneToAll(Point from, List<Point> pointListTo, LocalDate date) {

        List<Edge> edgeList = new ArrayList<>();

        pointListTo.forEach(point -> edgeList.addAll(findEdgesFromTo(from, point, date)));

        return edgeList;

    }

    @Override
    public List<Edge> findEdgesAllToAll(List<Point> pointListFrom, List<Point> pointListTo, LocalDate date) {
        return null;
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

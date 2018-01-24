package com.netcracker.travelPlanner.service;

import com.google.gson.Gson;
import com.netcracker.travelPlanner.entities.yandex.YandexRasp;
import com.netcracker.travelPlanner.entities.Edge;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YandexService {

    private String apiKey;

    public YandexService(String apiKey) {
        this.apiKey = apiKey;
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

    public List<Edge> getEdgesFromYandex( String from
                                         ,String to
                                         ,LocalDate date)
    {

        Date dateNow = new Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder();
        String url = builder.append("https://api.rasp.yandex.net/v3.0/search/?apikey=")
                            .append(this.apiKey)
                            .append("&format=json&from=")
                            .append(from)
                            .append("&to=")
                            .append(to)
                            .append("&lang=ru_RU&date=")
                            .append(date.format(formatter))
                            .toString();

        YandexRasp yandexRasp = getYandexRaspFromUrl(url);

        String startPoint = yandexRasp.getSearch().getFrom().getTitle();
        String endPoint = yandexRasp.getSearch().getTo().getTitle();

        List<Edge> edgeList = new ArrayList<>();

        yandexRasp.getSegments()
                    .stream()
                    .filter(l -> l.getTicketsInfo().getPlaces().size()!=0)
                    .forEach(l -> edgeList.add(new Edge(dateNow
                                                        ,startPoint
                                                        ,endPoint
                                                        ,l.getThread().getTransportType()
                                                        ,l.getDuration()
                                                        ,(double)l.getTicketsInfo().getPlaces().get(0).getPrice().getWhole()
                                                        ,0.0
                                                        ,java.sql.Date.valueOf(LocalDate.parse(l.getDeparture(),DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                                                        ,java.sql.Date.valueOf(LocalDate.parse(l.getArrival(),DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                                                        ,"RUB")));



        return edgeList;
    }

}

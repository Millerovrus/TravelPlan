package com.netcracker.travelplanner.service;

import com.google.gson.Gson;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.yandex.YandexRasp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class YandexService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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

    public List<Edge> getEdgesFromYandex(String from,
                                         String to,
                                         LocalDate date,
                                         String codeFrom,
                                         String codeTo){

        if(from.equals(to)){return null;}
        Date dateNow = new Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder();
        String url = builder.append("https://api.rasp.yandex.net/v3.0/search/?apikey=")
                            .append("64d2c4dc-e05a-4574-b51a-bdc03b2bc8a3")
                            .append("&format=json&from=")
                            .append(codeFrom)
                            .append("&to=")
                            .append(codeTo)
                            .append("&lang=ru_RU&date=")
                            .append(date.format(formatter))
                            .append("&system=iata")
                            .toString();

        YandexRasp yandexRasp = getYandexRaspFromUrl(url);

        List<Edge> edgeList = new ArrayList<>();

        yandexRasp.getSegments()
                    .stream()
                    .filter(l -> l.getTicketsInfo().getPlaces().size()!=0)
                    .forEach(l -> edgeList.add(new Edge(dateNow
                                                        ,from
                                                        ,to
                                                        ,l.getThread().getTransportType()
                                                        ,l.getDuration()
                                                        ,(double)l.getTicketsInfo().getPlaces().get(0).getPrice().getWhole()
                                                        ,null
                                                        , LocalDateTime.parse(l.getDeparture(),DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                                                        , LocalDateTime.parse(l.getArrival(),DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                                                        ,"RUB"
                                                        , codeFrom
                                                        , codeTo)));



        return edgeList;
    }

}

package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.models.entities.Edge;
import com.netcracker.travelplanner.models.entities.Point;
import com.netcracker.travelplanner.models.entities.TransitEdge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class UFSParser implements ApiInterface {

    private static final Logger logger = LoggerFactory.getLogger(UFSParser.class);

    @Override
    public List<Edge> findEdgesFromTo(Point from, Point to, LocalDate date, int numberOfPassengers) {
        String url = "https://www.ufs-online.ru/en/kupit-zhd-bilety/" +
                from.getRussianName() +
                "/" +
                to.getRussianName() +
                "?date=" +
                date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        List<Edge> edgeList = new ArrayList<>();

        if (!from.getRussianName().equals(to.getRussianName())) {
            Document doc = null;
            Elements records = null;
            try {
                doc = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36")
                        .get();
            } catch (IOException e) {
                logger.error("ошибка получения по запросу {}", url);
            }
            if (doc != null) {
                records = doc.select("div.wg-train-container");
            }
            if (records != null && records.size() > 0) {
                for (Element record : records) {
                    Edge edge = new Edge();
                    edge.setCreationDate(new Date());
                    edge.setTransportType("train");
                    edge.setCost(Double.parseDouble(record.selectFirst("span.wg-wagon-type__price").selectFirst("a").ownText().replace(" ", "").replace(",", ".")) * (numberOfPassengers));
                    edge.setStartDate(convertTimeAndDate(record.select("span.wg-track-info__time").first().ownText(), record.select("span.wg-track-info__date").first().text(), date));
                    edge.setEndDate(convertTimeAndDate(record.select("span.wg-track-info__time").last().ownText(), record.select("span.wg-track-info__date").last().text(), date));
                    edge.setDuration((double) ChronoUnit.SECONDS.between(edge.getStartDate(), edge.getEndDate()));
                    edge.setCurrency("RUB");
                    edge.setNumberOfTransfers(1);
                    edge.setStartPoint(new Point(from.getName()
                            , from.getLatitude()
                            , from.getLongitude()
                            , from.getIataCode()
                            , from.getYandexCode()
                            , ""
                            , from.getRussianName()));
                    edge.setEndPoint(new Point(to.getName()
                            , to.getLatitude()
                            , to.getLongitude()
                            , to.getIataCode()
                            , to.getYandexCode()
                            , ""
                            , to.getRussianName()));

                    List<TransitEdge> transitEdges = new LinkedList<>();
                    transitEdges.add(new TransitEdge(
                            new Point(from.getName()
                                    , from.getLatitude()
                                    , from.getLongitude()
                                    , from.getIataCode()
                                    , from.getYandexCode()
                                    , ""
                                    , from.getRussianName()
                            )
                            , new Point(to.getName()
                                    , to.getLatitude()
                                    , to.getLongitude()
                                    , to.getIataCode()
                                    , to.getYandexCode()
                                    , ""
                                    , to.getRussianName()
                             )
                            , convertTimeAndDate(record.select("span.wg-track-info__time").first().ownText(), record.select("span.wg-track-info__date").first().text(), date)
                            , convertTimeAndDate(record.select("span.wg-track-info__time").last().ownText(), record.select("span.wg-track-info__date").last().text(), date)

                    ));
                    edge.setTransitEdgeList(transitEdges);
                    edge.setPurchaseLink(url);
                    edgeList.add(edge);
                }
            } else logger.error("нет данных по запросу {}", url);
        }
        return edgeList;
    }

    private LocalDateTime convertTimeAndDate(String time, String date, LocalDate dateOfRequest) {
        date = date.substring(5).replace(".", "").concat(" " + String.valueOf(dateOfRequest.getYear()));
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd MMM yyyy")
                .toFormatter(Locale.ENGLISH);
        LocalDate formatDate = LocalDate.parse(date, formatter);
        return LocalDateTime.of(formatDate, LocalTime.parse(time));
    }
}

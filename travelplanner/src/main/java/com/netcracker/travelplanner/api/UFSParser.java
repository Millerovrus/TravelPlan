package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.models.entities.Edge;
import com.netcracker.travelplanner.models.entities.Point;
import com.netcracker.travelplanner.models.entities.TrainTicketsInfo;
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
import java.util.*;


public class UFSParser implements ApiInterface {

    private static final Logger logger = LoggerFactory.getLogger(UFSParser.class);

    @Override
    public List<Edge> findEdgesFromTo(Point from, Point to, LocalDate date, int numberOfAdults, int numberOfChildren, int numberOfInfants) {
        String url = "https://www.ufs-online.ru/en/kupit-zhd-bilety/" +
                from.getRussianName() +
                "/" +
                to.getRussianName() +
                "?date=" +
                date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        List<Edge> edgeList = new ArrayList<>();

        if (!from.getRussianName().equals(to.getRussianName())) {
            Document doc;
            try {
                doc = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36")
                        .get();
            } catch (IOException e) {
                logger.error("ошибка получения по запросу {}", url);
                return edgeList;
            }

            Elements records = doc.select("div.wg-train-container");

            if (records != null && records.size() > 0) {
                for (Element record : records) {
                    Edge edge = new Edge();
                    edge.setCreationDate(new Date());
                    edge.setTransportType("train");
                    edge.setCost(0.0);
                    edge.setStartDate(convertTimeAndDate(record.select("span.wg-track-info__time").first().ownText(), record.select("span.wg-track-info__date").first().text(), date));
                    edge.setEndDate(convertTimeAndDate(record.select("span.wg-track-info__time").last().ownText(), record.select("span.wg-track-info__date").last().text(), date));
                    edge.setDuration(travelTimeToDuration(record.selectFirst("span.wg-track-info__travel-time").text()));
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

                    //полная инфа о ценах, типе вагона и кол-ве мест
                    List<String> types = new ArrayList<>();
                    for (Element typeEl : record.select("div.wg-wagon-type__title")) {
                        types.add(typeEl.text());
                    }
                    List<Double> prices = new ArrayList<>();
                    for (Element priceEl : record.select("span.wg-wagon-type__price").select("a")) {
                        prices.add(Double.parseDouble(priceEl.ownText().replace(" ", "").replace(",", ".")));
                    }
                    List<Integer> availableSeats = new ArrayList<>();
                    for (Element seats : record.select("span.wg-wagon-type__available-seats")) {
                        availableSeats.add(Integer.parseInt(seats.text().replaceAll("[^0-9]+", "")));
                    }
                    List<TrainTicketsInfo> fullInfo = new ArrayList<>();
                    for (int i = 0; i < types.size(); i++) {
                        fullInfo.add(new TrainTicketsInfo(types.get(i), prices.get(i), availableSeats.get(i)));
                    }
                    edge.setTrainTicketsInfoList(fullInfo);

                    //цена в зависимости от наличия мест
                    int numOfPassengers = numberOfAdults + numberOfChildren;
                    for (TrainTicketsInfo TrainTicketsInfo : fullInfo) {
                        if (numOfPassengers > TrainTicketsInfo.getAvailableSeats()) {
                            edge.setCost(edge.getCost() + TrainTicketsInfo.getCost() * TrainTicketsInfo.getAvailableSeats());
                            numOfPassengers -= TrainTicketsInfo.getAvailableSeats();
                        } else {
                            edge.setCost(edge.getCost() + TrainTicketsInfo.getCost() * numOfPassengers);
                            numOfPassengers = 0;
                        }
                        if (numOfPassengers == 0){
                            edge.setCost(Math.rint(10.0 * edge.getCost()) / 10.0);
                            //добавление эджа происходит только если мест в поезде хватает
                            edgeList.add(edge);
                            break;
                        }
                    }

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
        //парсер не возвращает год. Поэтому если запрос сделан на декабрь, а возвратился январь, то плюсуем 1 год
        if (dateOfRequest.getMonthValue() == 12 && formatDate.getMonthValue() == 1){
            formatDate = formatDate.plusYears(1);
        }
        return LocalDateTime.of(formatDate, LocalTime.parse(time));
    }

    private Double travelTimeToDuration(String travelTime){
        List<Integer> durationList = new ArrayList<>();
        for (String stringPart : travelTime.split(" ")) {
            if (stringPart.matches("\\d*")){
                durationList.add(Integer.parseInt(stringPart));
            }
        }
        int duration = 0;
        switch (durationList.size()) {
            case 3:
                duration = (durationList.get(0) * 86400) + (durationList.get(1) * 3600) + (durationList.get(2) * 60);
                break;
            case 2:
                duration = (durationList.get(0) * 3600) + (durationList.get(1) * 60);
                break;
            case 1:
                duration = (durationList.get(0) * 60);
                break;
            default:
                break;
        }
        return (double)duration;
    }
}

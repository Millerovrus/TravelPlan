package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class YandexParser implements ApiInterface {

    private InitializatorApi initializatorApi;

    public List<Edge> findEdgesFromTo() {
        String url = "https://rasp.yandex.ru/search/?fromId=" +
                initializatorApi.getYandexCodeFrom() +
                "&toId=" +
                initializatorApi.getYandexCodeTo() +
                "&transportType=all&when=" +
                initializatorApi.getDeparture().format(DateTimeFormatter.ISO_LOCAL_DATE);

        List<Edge> edgeList = new ArrayList<>();

        initializatorApi.getWebDriver().get(url);
        initializatorApi.getWebDriver().navigate().refresh();
        WebParser.waitForLoad(initializatorApi.getWebDriver());

        String sourceHtml = initializatorApi.getWebDriver().getPageSource();

        Document doc = Jsoup.parse(sourceHtml);

        Elements el = doc.getElementsByClass("SearchSegment");

        el.stream()
                .filter(element -> !element.getElementsByClass("SegmentPrices").first().text().equals(""))
                .forEach(element -> edgeList.add(new Edge(new Date()
                        , initializatorApi.getFrom()
                        , initializatorApi.getTo()
                        , convertTypes(element.getElementsByClass("TransportIcon").first().attr("aria-label"))
                        , (double) splStr(element.getElementsByClass("SearchSegment__duration").first().text())
                        , splCost(element.getElementsByClass("Price").first().text())
                        , 0.0
                        , LocalDateTime.of(LocalDate.now(), convertTime(element.selectFirst("div.SearchSegment__dateTime.Time_important").getElementsByClass("SearchSegment__time").first().text()))
                        , LocalDateTime.of(LocalDate.now(), convertTime(element.selectFirst("div.SearchSegment__dateTime.Time_important").getElementsByClass("SearchSegment__time").first().text())).plusSeconds(splStr(element.getElementsByClass("SearchSegment__duration").first().text()))
                        , "RUB"
                        , initializatorApi.getIataCodeFrom()
                        , initializatorApi.getIataCodeTo()
                        , initializatorApi.getLatitudeFrom()
                        , initializatorApi.getLongitudeFrom()
                        , initializatorApi.getLatitudeTo()
                        , initializatorApi.getLongitudeTo())));

        return edgeList;
    }

    public List<Edge> findEdgesOneToAll() {

       List<String> codesOfCities = new ArrayList<>();

       List<Edge> edgeList = new ArrayList<>();

       initializatorApi.getCitiesTo().forEach(myPoint -> codesOfCities.add(EdgeService.getYandexCode(myPoint.getLat(),myPoint.getLon())));


       return edgeList;
    }

    public List<Edge> findEdgesAllToOne() {
        return null;
    }

    public List<Edge> findEdgesAllToAll() {
        return null;
    }

    private  String convertTypes(String type){

        String result = "";
        switch (type){
            case "поезд" :
                result= "train";
                break;
            case "автобус" :
                result= "bus";
                break;
            case "самолёт" :
                result= "plane";
                break;
        }
        return result;
    }

    private  int splStr(String inString) {

        double d1 = 0.0;

        String[] strings = inString.split(" ");
        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();

        for (int j = 0; j < strings.length ; j++) {
            if (strings[j].matches("\\d*")) {
                stringBuilder1.append(strings[j]);
                strings[j] = "x";
                break;
            }
        }

        for (int i = 0; i < strings.length ; i++) {
            if (strings[i].matches("\\d*")) {
                stringBuilder2.append(strings[i]);
                strings[i] = "y";
                break;
            }
        }

        if(stringBuilder1.toString().isEmpty()){
            return (int) d1;
        }

        if(stringBuilder2.toString().isEmpty()){
            return (int) Double.parseDouble(stringBuilder1.toString())*3600;
        }

        else{
            d1 = (Double.parseDouble(stringBuilder1.toString()))*3600 + (Double.parseDouble(stringBuilder2.toString()))*60;
        }

        return (int) d1;
    }

    private  Double splCost(String string){

        if(string==null){
            return 0.0;
        }

        String [] strings = string.split("");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strings.length ; i++) {

            if(strings[i].matches("\\d*")){
                stringBuilder.append(strings[i]);
            }

        }
        if(!stringBuilder.toString().isEmpty()) {
            return Double.parseDouble(stringBuilder.toString());
        }
        else return 0.0;

    }

    private  LocalTime convertTime(String time){

        String res = time + ":00";

        return LocalTime.parse(res, DateTimeFormatter.ISO_LOCAL_TIME);

    }

    public YandexParser(InitializatorApi initializatorApi) {
        this.initializatorApi = initializatorApi;
    }
}

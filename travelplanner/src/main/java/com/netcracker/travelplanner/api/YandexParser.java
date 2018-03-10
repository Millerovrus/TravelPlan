package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Point;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.webParsers.WebParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class YandexParser implements ApiInterface {

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private WebDriver webDriver;

    public YandexParser(){
    }

    @Override
    public List<Edge> findEdgesFromTo(Point from, Point to, LocalDate date){


            String url = "https://rasp.yandex.ru/search/?fromId=" +
                from.getYandexCode() +
                "&toId=" +
                to.getYandexCode() +
                "&transportType=all&when=" +
                date.format(DateTimeFormatter.ISO_LOCAL_DATE);


        List<Edge> result = new ArrayList<>();
        List<Edge> edgeList = new ArrayList<>();

        webDriver.get(url);
        webDriver.navigate().refresh();
        WebParser.waitForLoad(webDriver);

        String sourceHtml = webDriver.getPageSource();

        Document doc = Jsoup.parse(sourceHtml);

//      Убрать NPE при пустой странице
        Elements el = doc.getElementsByClass("SearchSegment");

//        if( el.is("div.SegmentPrices")){
        el.stream()
                .filter(element -> !element.getElementsByClass("SegmentPrices").first().text().equals(""))
                .forEach(element -> edgeList.add(new Edge(new Date()
                        , from.getName()
                        , to.getName()
                        , convertTypes(element.getElementsByClass("TransportIcon").first().attr("aria-label"))
                        , (double) splStr(element.getElementsByClass("SearchSegment__duration").first().text())
                        , splCost(element.getElementsByClass("Price").first().text())
                        , 0.0
                        , LocalDateTime.of(LocalDate.now(), convertTime(element.selectFirst("div.SearchSegment__dateTime.Time_important").getElementsByClass("SearchSegment__time").first().text()))
                        , LocalDateTime.of(LocalDate.now(), convertTime(element.selectFirst("div.SearchSegment__dateTime.Time_important").getElementsByClass("SearchSegment__time").first().text())).plusSeconds(splStr(element.getElementsByClass("SearchSegment__duration").first().text()))
                        , "RUB"
                        , from.getIataCode()
                        , to.getIataCode()
                        , from.getLatitude()
                        , from.getLongitude()
                        , to.getLatitude()
                        , to.getLongitude())));

        if (!edgeList.isEmpty()) {
            result.add(filterEdgeByTypes(edgeList, RouteType.cheap));
            result.add(filterEdgeByTypes(edgeList, RouteType.optimal));
            result.add(filterEdgeByTypes(edgeList, RouteType.comfort));
            result.add(filterEdgeByTypes(edgeList, RouteType.fastest));
            result.add(filterEdgeByTypes(edgeList, RouteType.cheapest));
        }



        return result;

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

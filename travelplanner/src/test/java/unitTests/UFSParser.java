package unitTests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UFSParser {
    @Test
    public void test() throws Exception {
        String url = "https://www.ufs-online.ru/en/kupit-zhd-bilety/" +
                "Voronezh" +
                "/" +
                "Moscow" +
                "?date=" +
                LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.println(url);
        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36")
                .get();
        Elements records = doc.select("div.wg-train-container");
        if (records != null && records.size() > 0) {
            for (Element record : records) {
                //ссылка на маршрут выбранного поезда, но без привязки к дате и к откуда/куда...
                String link = record.selectFirst("a.wg-ref").attr("href");
                System.out.println(link);

                String timeFrom = record.select("span.wg-track-info__time").first().ownText();
                System.out.println(LocalTime.parse(timeFrom));

                String timeTo = record.select("span.wg-track-info__time").last().ownText();
                System.out.println(LocalTime.parse(timeTo));

                String dateFrom = record.select("span.wg-track-info__date").first().text();
                System.out.println(dateFrom.substring(5).replace(".", ""));

                String dateTo = record.select("span.wg-track-info__date").last().text();
                System.out.println(dateTo.substring(5).replace(".", ""));

                Elements typesEl = record.select("div.wg-wagon-type__title");
                List<String> types = new ArrayList<>();
                for (Element typeEl : typesEl) {
                    types.add(typeEl.text());
                }
                System.out.println(types);

                Elements pricesEl = record.select("span.wg-wagon-type__price").select("a");
                List<Double> prices = new ArrayList<>();
                for (Element priceEl : pricesEl) {
                    prices.add(Double.parseDouble(priceEl.ownText().replace(" ", "").replace(",", ".")));
                }
                System.out.println(prices);

                Elements availableSeatsEl = record.select("span.wg-wagon-type__available-seats");
                List<Integer> availableSeats = new ArrayList<>();
                for (Element element : availableSeatsEl) {
                    availableSeats.add(Integer.parseInt(element.text().replaceAll("[^0-9]+", "")));
                }
                System.out.println("Кол-во мест " + availableSeats);

                System.out.println(record.selectFirst("span.wg-track-info__travel-time").text());
            }
        } else System.out.println("нет результатов");
    }
}

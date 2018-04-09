package unitTests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UFSParser {
    @Test
    public void test() throws Exception {
        String url = "https://www.ufs-online.ru/kupit-zhd-bilety/" +
                "Voronezh" +
                "/" +
                "Moscow" +
                "?date=" +
                LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
                System.out.println(timeFrom);

                String timeTo = record.select("span.wg-track-info__time").last().ownText();
                System.out.println(timeTo);

                String dateFrom = record.select("span.wg-track-info__date").first().text();
                System.out.println(dateFrom);

                String dateTo = record.select("span.wg-track-info__date").last().text();
                System.out.println(dateTo);

                Elements typesEl = record.select("div.wg-wagon-type__title");
                List<String> types = new ArrayList<>();
                for (Element typeEl : typesEl) {
                    types.add(typeEl.text());
                }
                System.out.println(types);

                Elements pricesEl = record.select("span.wg-wagon-type__price").select("a");
                List<String> prices = new ArrayList<>();
                for (Element priceEl : pricesEl) {
                    prices.add(priceEl.ownText());
                }
                System.out.println(prices);

                System.out.println();
            }
        } else System.out.println("нет результатов");
    }
}

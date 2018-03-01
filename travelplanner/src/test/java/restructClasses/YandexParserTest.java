package restructClasses;

import com.netcracker.travelplanner.api.InitializatorApi;
import com.netcracker.travelplanner.api.PreparingDataService;
import com.netcracker.travelplanner.api.WebParser;
import com.netcracker.travelplanner.api.YandexParser;
import com.netcracker.travelplanner.entities.Edge;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class YandexParserTest {
  /*  @Test
    public void findEdgesOneToAll() throws Exception {

        PreparingDataService preparingDataService = new PreparingDataService();


        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-07");


        WebDriver driver = WebParser.getDriver();

        initializatorApi.setWebDriver(driver);


        YandexParser yandexParser = new YandexParser(initializatorApi);

        List<Edge> list = yandexParser.findEdgesOneToAll();

        list.forEach(edge -> System.out.println(edge.toString()));

        System.out.println(list.size());
    }*/

    @Test
    public void findEdgesFromTo() throws Exception {
    }


    @Test
    public void tetet(){
        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-07");

        WebDriver driver = WebParser.getDriver();


        YandexParser yandexParser = new YandexParser();
        yandexParser.setWebDriver(WebParser.getDriver());
        List<Edge> list = yandexParser.findEdgesFromTo(initializatorApi.getFrom(),initializatorApi.getTo(),initializatorApi.getDeparture());
        list.forEach(edge -> System.out.println(edge.toString()));

        driver.close();
    }

  /*  @Test
    public void testSingletonOutput(){
        PreparingDataService preparingDataService = new PreparingDataService();
        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-07");

        WebDriver driver = WebParser.getDriver();


        YandexParser yandexParser = new YandexParser(driver);
        List<Edge> list = yandexParser.findEdgesFromTo();

        System.out.println("singleton");
        System.out.println(initializatorApi.toString());

        driver.close();


    }*/


}
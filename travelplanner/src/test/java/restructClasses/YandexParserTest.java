package restructClasses;

import com.netcracker.travelplanner.models.SearchInputParameters;
import com.netcracker.travelplanner.services.PreparingDataService;
import com.netcracker.travelplanner.webParsers.WebParser;
import com.netcracker.travelplanner.api.YandexParser;
import com.netcracker.travelplanner.models.entities.Edge;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class YandexParserTest {

    @Test
    public void tetet(){
        PreparingDataService preparingDataService = new PreparingDataService();

        SearchInputParameters searchInputParameters = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-19", 1, 0, 0);

        WebDriver driver = WebParser.getDriver();

        YandexParser yandexParser = new YandexParser(WebParser.getDriver());
        List<Edge> list = yandexParser.findEdgesFromTo(searchInputParameters.getFrom(), searchInputParameters.getTo(), searchInputParameters.getDeparture(), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren(), searchInputParameters.getNumberOfInfants());
        list.forEach(edge -> System.out.println(edge.toString()));

        driver.quit();
    }



}
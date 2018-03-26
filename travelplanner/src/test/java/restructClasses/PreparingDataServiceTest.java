package restructClasses;

import com.netcracker.travelplanner.entities.SearchInputParameters;
import com.netcracker.travelplanner.service.PreparingDataService;
import org.junit.Test;

public class PreparingDataServiceTest {
    @Test
    public void prepareData() throws Exception {

        PreparingDataService preparingDataService = new PreparingDataService();


        SearchInputParameters searchInputParameters = preparingDataService.prepareData("Voronezh", "Sochi", "(51.6754966, 39.20888230000003)","(43.602446, 39.730276)","2018-03-15", 1, 0);


//        SearchInputParameters searchInputParameters = preparingDataService.prepareData("Voronezh", "Kursk", "(51.6754966, 39.20888230000003)","(51.7091957, 36.15622410000003)","2018-03-15");

        System.out.println(searchInputParameters.toString());

        System.out.println("_________________________");

        searchInputParameters.getCitiesFrom().forEach(point -> System.out.println(point.toString()));

        System.out.println("_________________________");

        searchInputParameters.getCitiesTo().forEach(point -> System.out.println(point.toString()));

    }

}
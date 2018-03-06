package restructClasses;

import com.netcracker.travelplanner.service.InitializatorApi;
import com.netcracker.travelplanner.service.PreparingDataService;
import org.junit.Test;

public class PreparingDataServiceTest {
    @Test
    public void prepareData() throws Exception {

        PreparingDataService preparingDataService = new PreparingDataService();


        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-15");


//        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Kursk", "(51.6754966, 39.20888230000003)","(51.7091957, 36.15622410000003)","2018-03-15");

        System.out.println(initializatorApi.toString());

        System.out.println("_________________________");

        initializatorApi.getCitiesFrom().forEach(point -> System.out.println(point.toString()));

        System.out.println("_________________________");

        initializatorApi.getCitiesTo().forEach(point -> System.out.println(point.toString()));

    }

}
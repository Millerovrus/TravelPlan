package restructClasses;

import com.netcracker.travelplanner.model.exceptions.KiwiIATACodeException;
import com.netcracker.travelplanner.services.EdgeService;
import org.junit.Test;

public class EdgeServiceTest {
//    @Test
    public void getCities() throws Exception {

        EdgeService
                .getCities("VOZ", 51.6754, 39.2088)
                .forEach(myPoint -> System.out.println(myPoint.toString()));
    }

//    @Test
    public void isGlobalRoute() throws Exception {

        //лондон - берлин
        System.out.println(EdgeService.isGlobalRoute(51.5073,-0.1277,52.52007,13.404954));

        //воронеж - москва
        System.out.println(EdgeService.isGlobalRoute(51.6754,39.2088,55.755826,37.617299));
    }

//    @Test
    public void getIataCodeAndTimezone() throws Exception {
        try {
            System.out.println(EdgeService.getIataCodeAndTimezone(55.972399,37.411645, ""));
        } catch (KiwiIATACodeException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void ggg(){

        String s = "(12.3434,111.5454)";

       String[] ss=  s.replaceAll("\\)","").replaceAll("\\(","").split(",");
//        Pattern pattern = Pattern.compile("^[\\+\\-]{0,1}[0-9]+[\\.][0-9]+$");
//
//        Matcher matcher = pattern.matcher(s);
//
//        while (matcher.find()){
//            System.out.println(matcher.group());
//        }

        for (String s1 : ss) {
            Double d = Double.parseDouble(s1);
            System.out.println(d + " "+ d.getClass().getName());
        }

    }

}
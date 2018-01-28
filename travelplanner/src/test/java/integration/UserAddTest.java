package integration;

import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.service.UserRepositoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAddTest {
    @Autowired
    private UserRepositoryService userRepositoryService;
    private List<User> userList;
    private User user;

    @Before
    public void setValues() {
        userList = new ArrayList<User>();

        user = new User("mask@email.com", "Mask", "Ilon", new Date(),
                false, new Date(), "123");

        userList.add(new User("hocking3@email.com", "Stephen", "Hocking", new Date(),
                false, new Date(), "1234"));
        userList.add(new User("korolev@yandex.com", "Sergey", "Korolev", new Date(),
                false, new Date(), "12345"));
    }

    @Test
    public void addUserTest() {
        userRepositoryService.addUser(user);
    }

    @Test
    public void addUsersTest() {
        userRepositoryService.addUsers(userList);
    }

    @After
    public void clear() {
        userList.clear();
        user = null;
    }
}

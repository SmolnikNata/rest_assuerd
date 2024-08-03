package tests;


import dto.UserDataFull;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static tests.BaseTest.getRequest;

public class GetUserListTest {

    @Test

    public void getUserList() {

        Response response = getRequest("/user", 200);// распарсим, сделаем из этого список листов users
        List<UserDataFull> users = response// это то , что вернется
                .body().jsonPath().getList("data", UserDataFull.class);
        System.out.println(users.get(0).getId());

        //Check that 20 users are in list -- 20 пользователей в списке
        assertEquals(20, users.size());

        //количество юзеров совпадает с количеством внизу отввета
        Integer limitValue = response
                .body().jsonPath().getInt("limit");
        Integer pageValue = response
                .body().jsonPath().getInt("page");
        System.out.println(limitValue + " , " + pageValue);
        assertEquals(limitValue, users.size());

        //Check that id of each user is not empty
        for (UserDataFull user : users) {
            assertFalse(user.getId().isEmpty());
            //Check that all pictures in jpg format
            assertTrue(user.getPicture().endsWith(".jpg"));
        }

    }
    @Test
    public void getUserListWithSpecLimit(){//Check 5 JSON in users list
        Integer limit = 5;
        Response response = getRequest("/user?limit=" + limit, 200);// распарсим, сделаем из этого список листов users
        List<UserDataFull> users = response// это то , что вернется
                .body().jsonPath().getList("data", UserDataFull.class);
        assertEquals(limit,users.size());
    }
    @Test
    public void getUserListLimitInvalidLess(){//Check that limitResponse ==5
        Integer limit=4;
        Integer limitResponse =5;
        Response response = getRequest("/user?limit=" + limit, 200);// распарсим, сделаем из этого список листов users
        List<UserDataFull> users = response// это то , что вернется
                .body().jsonPath().getList("data", UserDataFull.class);
        assertEquals(limitResponse,users.size());

    }
    @Test
    public void getUserListLimitInvalidMore(){
        //limit > 50
        //Check that limit ==50
        Integer limit=54;
        Integer limitResponse =50;
        Response response = getRequest("/user?limit=" + limit, 200);// распарсим, сделаем из этого список листов users
        List<UserDataFull> users = response// это то , что вернется
                .body().jsonPath().getList("data", UserDataFull.class);
        assertEquals(limitResponse,users.size());
    }
    //5->5, 23->23, 2->5, 78->50, -1->5
    //ParametrizedTest

    @ParameterizedTest
    @MethodSource("validData")
    public void getUserListLimitInvalidLess(int limit, int expectedLimit) {
        //5->5, 23->23, 2->5, 78->50, -1->5
        //ParametrizedTest
        //assertEquals(expectedLimit, limit);
        Response response = getRequest("/user?limit=" + limit, 200);
        List<UserDataFull> users = response
                .body().jsonPath().getList("data", UserDataFull.class);
        assertEquals(expectedLimit, users.size());
    }
    static Stream<Arguments> validData() {
        return Stream.of(
                arguments(5, 5),
                arguments(23, 23),
                arguments(2, 5),
                arguments(78, 50),
                arguments(-1, 5)
        );
    }



}


package tests;

import dto.InvalidUser;
import dto.UserData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.getRequest;

public class GetUserById {
    @Test
    public void validUserDataTest(){
       String requestedId = "668bb0c58a7616f2b4caab35"; //  id запроса(из постман: create User

        //get user by id, log all from response// метод,аналогичный get user by id в постман. получим на экран ответ
//        UserData user =
//                given().baseUri("https://dummyapi.io/data/v1")
//                        .header("app-id","65faa99005388ea765125437") // название в хэдаре и номер арр id
//                        .when().log().all()
//                        .get("/user/" + requestedId)
//                        .then().log().all().statusCode(200).extract().body()
//                        .jsonPath().getObject("", UserData.class);// "" забираем все
//        System.out.println(user.getId());

        //Rewrite with getRequest()
        Response response = getRequest("user/" + requestedId, 200);
        UserData user  = response.body().jsonPath().getObject("",UserData.class);
        System.out.println(user.getId());

        // все поля не пустые//Check that all fields are NOT empty

        assertFalse(user.getId().isEmpty());
        assertFalse(user.getEmail().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getRegisterDate().isEmpty());
        assertFalse(user.getUpdatedDate().isEmpty());

        //Check that id value from response is matching to id from endpoint//проверяем,что id в методах совпадает с id в ответе
       assertEquals(requestedId,user.getId());

        //Check that registerDate is the same with updatedDate..дата регистрации совпадает с датой обновления(ничего не меняли)
        assertEquals(user.getRegisterDate(),user.getUpdatedDate());
    }
    @Test
    //Если ввести неправильный id текст ошибки....
    public void invalidUserTest(){
            //get user by id, log all from response
        //Check that error message with text "PARAMS_NOT_VALID" is displayed
        String requestedId = "sfghjk";
//        InvalidUser error =  given().baseUri("https://dummyapi.io/data/v1/")
//                .header("app-id", "6380c63b2e6f5682c64dd368")
//                .when().log().all()
//                .get("/user/" + requestedId)
//                .then().log().all().statusCode(400)
//                .extract().body().jsonPath().getObject("",InvalidUser.class);

        //Rewrite with getRequest()
        Response response = getRequest("user/" + requestedId, 400);
        InvalidUser error  =  response.body().jsonPath().getObject("",InvalidUser.class);

        assertEquals("PARAMS_NOT_VALID", error.getError());
    }

}

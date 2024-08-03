package tests;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.BaseTest.deleteRequest;
import static tests.BaseTest.postRequest;

public class DeleteUserTest {
    Faker faker = new Faker();

    //Create user
    // Get user ID
    // Delene ID

    @Test
    public void deleteUser() {
        CreateUserRequest requestBody = new CreateUserRequest("Jack", "Black", "Jack93@yahoo.de", "male", "mr", "123456789");
        Response response = postRequest("/user/create", requestBody, 200);

        String userId = response.body().jsonPath().getString("id");
        Response deleteResponse = deleteRequest("/user/" + userId, 200);

        assertEquals(deleteResponse.body().jsonPath().getString("id"), userId);

    }

    // Check delete alredy deleted user
    //1. 404 status code
    //1. 404 status code
    //2. "error": "RESOURCE_NOT_FOUND
    @Test
    public void deleteDeletedUser() {
        CreateUserRequest requestBody = new CreateUserRequest("Jack", "Black", "Jack89@yahoo.de", "male", "mr", "154678132");
        Response response = postRequest("/user/create", requestBody, 200);

        String userId = response.body().jsonPath().getString("id");

        Response deleteResponse = deleteRequest("/user/" + userId, 200);

        Response deleteResponse2 = deleteRequest("/user/" + userId,  404);

        String errorMessage = deleteResponse2.body().jsonPath().getString("error");

        assertEquals("RESOURCE_NOT_FOUND", errorMessage);
    }

    // удаление невалидного пользователя
    //Delete invalid user
    @Test
    public void deleteInvalidUser(){

        Response deleteResponse = deleteRequest("/user/" + "5", 400);
        String errorMessage = deleteResponse.body().jsonPath().getString("error");
        assertEquals("PARAMS_NOT_VALID", errorMessage);
    }

}





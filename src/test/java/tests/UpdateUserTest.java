package tests;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;
import dto.UpdateUserRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tests.BaseTest.postRequest;
import static tests.BaseTest.putRequest;

public class UpdateUserTest {
    Faker faker = new Faker();

    @Test
    public void updateLastName() {
        //1. создать нового пользователя:  Create new user
        //2.поменять ласт наме:Change LastName
        //3. убедимся, что изменился

        //1. Create new user

        String userEmail = faker.internet().emailAddress();
        String userFirstName = faker.name().firstName();
        String userLastName = faker.name().lastName();
        String gender = "male";
        String tittle = "mr";
        String phone = "+49123456";

        //CreateUserRequest requestBody = new CreateUserRequest(userFirstName, userLastName, userEmail, gender, tittle, phone);
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .lastName(userLastName)
                .firstName(userFirstName)
                .email(userEmail)
                .gender(gender)
                .tittle(tittle)
                .phone(phone)
                .build();
        //вместо этого используем билдер,
        Response response = postRequest("/user/create",  requestBody,200);
        String userId = response.body().jsonPath().getString("id");

        //Check that first name form response equals to name from request
        String expectedLastName = "Black";
        UpdateUserRequest requestUpdate = UpdateUserRequest.builder()
                .lastName(expectedLastName)
                .build();
        Response updateResponse = putRequest("/user/" + userId,  requestUpdate, 200);

        //2. Change LastName
        //UpdateUserRequest updateRequestBody = new UpdateUserRequest(expectedLastName);
        //3. Check that lastName has changes
        String actualLastName= updateResponse.body().jsonPath().getString("lastName");
        assertEquals(expectedLastName, actualLastName);


    }
    @Test
    public void updateFirstName() { ///? не работает
        //1. создать нового пользователя:  Create new user
        //2.поменять ласт наме:Change LastName
        //3. убедимся, что изменился

        //1. Create new user

//        // String userEmail = faker.internet().emailAddress();
//        //CreateUserRequest requestBody = new CreateUserRequest("Jack", "Black", userEmail, "male", "mr","123456");
//       // Response response = postRequest("/user/create",  requestBody,200);
//       // String userId = response.body().jsonPath().getString("id");
//       // String firstName = response.body().jsonPath().getString("firstName");

        String userEmail = faker.internet().emailAddress();
        String userFirstName = faker.name().firstName();
        String userLastName = faker.name().lastName();
        String gender = "male";
        String tittle = "mr";
        String phone = "+49123456";

        //CreateUserRequest requestBody = new CreateUserRequest(userFirstName, userLastName, userEmail, gender, tittle, phone);
        // используем билдер,
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .lastName(userLastName)
                .firstName(userFirstName)
                .email(userEmail)
                .gender(gender)
                .tittle(tittle)
                .phone(phone)
                .build();

        Response response = postRequest("/user/create",  requestBody,200);
        String userId = response.body().jsonPath().getString("id");

        //Check that first name form response equals to name from request
        String expectedFirstName = "Tom";
        UpdateUserRequest requestUpdate = UpdateUserRequest.builder()
                .firstName(expectedFirstName)
                .build();
        Response updateResponse = putRequest("/user/" + userId,  requestUpdate, 200);

        //2. Change LastName
        //UpdateUserRequest updateRequestBody = new UpdateUserRequest(expectedLastName);
        //3. Check that lastName has changes
        String actualFirstName= updateResponse.body().jsonPath().getString("firstName");
        assertEquals(expectedFirstName, actualFirstName);

    }


    @Test
    public void updateEmail() {
        //4. Change Email

        //1. Create new user

        String userEmail = faker.internet().emailAddress();
        String userFirstName = faker.name().firstName();
        String userLastName = faker.name().lastName();
        String gender = "male";
        String tittle = "mr";
        String phone = "+49123456";

        CreateUserRequest requestBody = CreateUserRequest.builder()
                .firstName(userFirstName)
                .lastName(userLastName)
                .email(userEmail)
                .gender(gender)
                .tittle(tittle)
                .phone(phone)
                .build();
        Response response = postRequest("/user/create",  requestBody, 200);
        String oldEmail = response.body().jsonPath().getString("email");
        String userId = response.body().jsonPath().getString("id");

        String newEmail = "John@gmail.com";

        UpdateUserRequest updateRequestBodyEmail = UpdateUserRequest.builder()
                .email(newEmail)
                .build();

        Response updateResponse = putRequest("/user/" + userId,  updateRequestBodyEmail, 200);
        String actualEmail = updateResponse.body().jsonPath().getString("email");
        assertEquals(oldEmail, actualEmail);
    }



}


package tests;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;
import dto.InvalidUser;
import dto.UserDataFull;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.BaseTest.getRequest;
import static tests.BaseTest.postRequest;

public class CreateUserTest {
    Faker faker = new Faker();
    @Test
    public void successCreateUserRequiredFields(){
        String userEmail = faker.internet().emailAddress();
        String firstName =  faker.name().firstName();
        String lastName =faker.name().lastName();
        String userGender = "male";
        String userTitle = "mr";
        String userPhone = "12345646";

        CreateUserRequest requestBody = new CreateUserRequest(firstName, lastName, userEmail,userGender,userTitle,userPhone);
        Response response = postRequest("/user/create",requestBody ,200);
        //Check that first name form response equals to name from request
        String firstNameFromResponse = response.body().jsonPath().getString("firstName");
        assertEquals(firstName,firstNameFromResponse);

        String lastNameFromResponse = response.body().jsonPath().getString("lastName");
        assertEquals(lastName,lastNameFromResponse);

        String userEmailFromResponse = response.body().jsonPath().getString("email");// название поля из класса CreaneUserRequest
        assertEquals(userEmail,userEmailFromResponse);


    }
    // хотим заполнять другие необязательные поля
    @Test
    public void successCreateUserAdditionalFields(){
        //gender
//tittle//fn//ln//email
        String userEmail = faker.internet().emailAddress();
        String firstName =  faker.name().firstName();
        String lastName =faker.name().lastName();
        String userGender = "male";
        String userTitle = "mr";
        String userPhone = "12345646";

        CreateUserRequest requestBody = new CreateUserRequest(firstName, lastName, userEmail,userGender,userTitle,userPhone);
        Response response = postRequest("/user/create",requestBody ,200);

        String userGenderResponse = response.body().jsonPath().getString("gender");
        assertEquals(userGender,userGenderResponse);

        String userTitleResponse = response.body().jsonPath().getString("title");
        assertEquals(userTitle,userTitleResponse);

        String userPhoneResponse = response.body().jsonPath().getString("phone");
        assertEquals(userPhone,userPhoneResponse);
        String lastNameFromResponse = response.body().jsonPath().getString("lastName");
        assertEquals(lastName,lastNameFromResponse);
        String firstNameFromResponse = response.body().jsonPath().getString("firstName");
        assertEquals(firstName,firstNameFromResponse);

        //Parse json data to UserDataFull class
        UserDataFull userData = response.body().jsonPath().getObject("", UserDataFull.class); //gender//tittle//fn//ln//email
        assertEquals(firstName, firstNameFromResponse);
        assertEquals(firstName, lastNameFromResponse);
        assertEquals(userEmail, userEmail);
        assertEquals(userGender, userGenderResponse);

    }
    @Test
    public void withoutEmail(){
        String firstName =  faker.name().firstName();
        String lastName =faker.name().lastName();
        //first name, last name
        //status code is 400
        //Path `email` is required.
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .gender("male")
                .build();
        Response response = postRequest("/user/create", requestBody,400);
        //status is 400
        //Path 'email' is required/
        String errorMessage = response.body().jsonPath().getString("data.email");// проверка, что при отсутствии эмайл код 400.Щписание ошибки
        //Path `email` is required.
        assertEquals("Path `email` is required.", errorMessage);
    }
    //oтсутствие firstName
    @Test
    public void withoutFirstName(){
        String userEmail = faker.internet().emailAddress();
        String lastName =faker.name().lastName();
        //first name, last name
        //status code is 400
        //Path `email` is required.
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .email(userEmail)
                .lastName(lastName)
                .gender("male")
                .build();
        Response response = postRequest("/user/create", requestBody,400);
        //status is 400
        //Path 'email' is required/
        String errorMessage = response.body().jsonPath().getString("data.firstName");// проверка, что при отсутствии firstName код 400.Щписание ошибки
        //Path `firstMame` is required.
        assertEquals("Path `firstName` is required.", errorMessage);
    }
    @Test
    public void withoutLastName(){
        String userEmail = faker.internet().emailAddress();
        String firstName =  faker.name().firstName();
        //first name, last name
        //status code is 400
        //Path `email` is required.
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .email(userEmail)
                .firstName(firstName)
                .gender("male")
                .build();
        Response response = postRequest("/user/create", requestBody,400);
        //status is 400
        //Path 'lastMame' is required/
        String errorMessage = response.body().jsonPath().getString("data.lastName");// проверка, что при отсутствии lastName код 400.Щписание ошибки
        //Path `email` is required.
        assertEquals("Path `lastName` is required.", errorMessage);
    }
    //
    @Test
    public void withoutAllFields(){
        //status is 400
        //Path 'allFields' is required/
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .gender("male")
                .build();
        Response response = postRequest("/user/create", requestBody,400);
        String errorMessageLastName = response.body().jsonPath().getString("data.lastName");
        String errorMessageFirstName = response.body().jsonPath().getString("data.firstName");
        String errorMessageEmail = response.body().jsonPath().getString("data.email");

        //Path `firstName` is required.
        assertEquals("Path `lastName` is required.", errorMessageLastName);
        assertEquals("Path `firstName` is required.", errorMessageFirstName);
        assertEquals("Path `email` is required.", errorMessageEmail);

        String errorMessage = response.body().jsonPath().getString("error");// проверка, что при отсутствии всех обязательных полей
        assertEquals("BODY_NOT_VALID", errorMessage);
    }

}

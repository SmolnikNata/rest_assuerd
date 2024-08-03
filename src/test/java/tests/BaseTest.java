package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.http.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.*;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    // выносим базовый URI ссылку
    final static String BASE_URI = "https://dummyapi.io/data/v1"; //static чтобы можно отовсюду обращаться

    final static String APP_ID_VALUE = "65faa99005388ea765125437";// мой APP_ID ключ
    // спецификация, для всех запросов одна/ хотим передавать в наших запросах апп йди
   static RequestSpecification specification = new RequestSpecBuilder()//такой класс,позволяет общие настройки держать вместе
            // актуальны для всех запросов, везде добавлять эти настройки в
            // запросах  / повторяющиеся части
            .setBaseUri(BASE_URI) //
            .addHeader("app-id", APP_ID_VALUE)// в постман апп айди стоит в header(ключ, значение)
            .setContentType(ContentType.JSON)  // чтобы передавать в формате JSON
            .build();  // завершаем метод билд

   // метод, валиден для всех get запросов
   public static Response getRequest(String endpoint, Integer expectedStatusCode){
       Response response = given()
               .spec(specification)
               .when()
               .log().all()
               .get(endpoint)
               .then()
               .log().all()
               .statusCode(expectedStatusCode)
               .extract().response();
       return response;

    }
    //универсальный метод для всех POST
    public static Response postRequest(String endpoint,Object body, Integer expectedStatusCode){
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .post(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }
    //универсальный метод для всех PUT
    public static Response putRequest(String endpoint,Object body, Integer expectedStatusCode){
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .put(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }
    public static Response deleteRequest(String endpoint, Integer expectedStatusCode){
        Response response = given()
                .spec(specification)
                .when()
                .log().all()
                .delete(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }






}

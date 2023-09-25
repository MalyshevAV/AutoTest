package Specifications;

import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.basic;

public class Specifications {
    public static RequestSpecification requestSpecification(){
        return new RequestSpecBuilder()
                .setAuth(basic("Administrator", "1234567809"))
                .setBaseUri("http://i1c.ddns.net:60380/TEST_KIT_MDM/hs/klass/")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }




    public static ResponseSpecification responseSpecification() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification responseSpecification400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification responseSpecification404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }
    public static ResponseSpecification responseSpecification500() {
        return new ResponseSpecBuilder()
                .expectStatusCode(500)
                .build();
    }

    public static void installSpec(RequestSpecification request, ResponseSpecification response){
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
    public static void deleteSpec(){

    }
}

package AutoTest;


import MDM.POJO.OkpdPojo;
import MDM.POJO.TnvdPojo;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.testng.AssertJUnit.assertTrue;

public class ClassifireIsAllRussian {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://i1c.ddns.net:60380/TEST_KIT_MDM/hs/klass/";
        //  RestAssured.port = 443;
    }
    @Test
    @Description("Получение списка Okpd2 ")
    public void getOkpdList() {
        List<OkpdPojo> response  =
                given()
                        .auth().basic("Administrator", "1234567809")
                        .contentType(ContentType.JSON)
                        .when()
                        .queryParam("step", 5)
                        .get("/okpd2")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", OkpdPojo.class);
        Assertions.assertNotNull(response);
    }
    @Test
    @Description("Получение ОКПД2 по Гуид, валидация при помощи схемы Json")
    public void getOkpdGuid() {
       given()
                .auth().basic("Administrator", "1234567809")
                .when()
                .get("/okpd2/15841c5e-1973-11ee-b5ac-005056013b0c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedGuid.json"))
                // поля в ответе Оквед и ОКПД одинаковые
                .statusCode(200);
    }

    @Test
    @Description("Получение ТНВД по Гуид, валидация при помощи схемы Json")
    public void getTnvdGuid() {
        given()
                .auth().basic("Administrator", "1234567809")
                .when()
                .get("/tnved/f8299582-32a7-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getTvendGuid.json"))
                .statusCode(200);
    }

    @Test
    @Description("Получение списка ТНВД ")
    public void getTnvdList() {
        List<TnvdPojo> response  =
                given()
                .auth().basic("Administrator", "1234567809")
                        .contentType(ContentType.JSON)
                .when()
                .queryParam("step", 5)
                .get("/tnved")
                .then().log().all()
                        .extract().body().jsonPath().getList(".", TnvdPojo.class);
        Assertions.assertNotNull(response);
    }

    @Test
    @Description("Получение Okved по Гуид, валидация при помощи схемы Json")
    public void getOkvedGuid() {
        given()
                .auth().basic("Administrator", "1234567809")
                .when()
                //.queryParam("step", 5)
                .get("/okved2/377593d9-168f-11ee-b5ab-a0dc07f9a67b")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedGuid.json"))
                .statusCode(200);
    }
    @Test
    @Description("Получение списка OKVED ")
    public void getOkvedList() {
        Response response  =
                given()
                        .auth().basic("Administrator", "1234567809")
                        .contentType(ContentType.JSON)
                        .when()
                        .queryParam("step", 5)
                        .get("/okved2")
                        .then().log().all().statusCode(200)
                        .body("size()", is(5))
                        .body("guid", notNullValue())
                        .body("name", notNullValue())
                        .body("code", not(emptyOrNullString()))
                        .extract().response();

        //JsonPath jsonPath = response.jsonPath();
        //  response.forEach(x-> Assert.assertTrue(x.getCode().isEmpty()));
       // response.forEach(x->Assert.assertTrue(x.getGuid().contains());
       // response.forEach(x->Assert.assertTrue(x.getGuid().isBlank()));
    }
}


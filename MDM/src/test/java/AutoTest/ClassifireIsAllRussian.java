package AutoTest;


import MDM.POJO.OkpdPojo;
import MDM.POJO.TnvdPojo;
import Specifications.Specifications;
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

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.testng.AssertJUnit.assertTrue;

public class ClassifireIsAllRussian {
    @Test
    @Description("Получение списка Okpd2 ")
    public void getOkpdList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<OkpdPojo> response  =
                given()
                        .when()
                        .queryParam("step", 5)
                        .get("/okpd2")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", OkpdPojo.class);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 12));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 150));
        Assertions.assertNotNull(response);
        deleteSpec();
    }
    @Test
    @Description("Получение ОКПД2 по Гуид, валидация при помощи схемы Json")
    public void getOkpdGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
       given()
                .when()
                .get("/okpd2/15841c5e-1973-11ee-b5ac-005056013b0c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedGuid.json"));
                // поля в ответе Оквед и ОКПД одинаковые;
        deleteSpec();
    }

    @Test
    @Description("Получение ТНВД по Гуид, валидация при помощи схемы Json")
    public void getTnvdGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("/tnved/f8299582-32a7-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getTvendGuid.json"));
        deleteSpec();
    }

    @Test
    @Description("Получение списка ТНВД ")
    public void getTnvdList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<TnvdPojo> response  =
                given()
                .when()
                .queryParam("step", 5)
                .get("/tnved")
                .then().log().all()
                        .extract().body().jsonPath().getList(".", TnvdPojo.class);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 10));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 150));
        response.forEach(x -> Assert.assertEquals(x.getUnit().length(), 36));
        Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test
    @Description("Получение Okved по Гуид, валидация при помощи схемы Json")
    public void getOkvedGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5,100,200)
                .get("/okved2/377593d9-168f-11ee-b5ab-a0dc07f9a67b")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedGuid.json"));
        deleteSpec();
    }
    @Test
    @Description("Получение списка OKVED ")
    public void getOkvedList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        Response response  =
                given()
                        .when()
                        .queryParam("step", 5)
                        .get("/okved2")
                        .then().log().all()
                        .body("size()", is(5))
                        .body("guid", notNullValue())
                        .body("name", notNullValue())
                        .body("code", not(emptyOrNullString()))
                        .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<String> guid = jsonPath.get("guid");
        List<String> name = jsonPath.get("name");
        List<String> code = jsonPath.get("code");

        for (String s : guid) {
            Assert.assertFalse(s.isEmpty());
            Assert.assertEquals(s.length(), 36);
        }
        Assert.assertTrue(name.stream().allMatch(x->x.length() <= 150));
        Assert.assertTrue(code.stream().allMatch(x->x.length() <= 8));
        deleteSpec();
    }
}


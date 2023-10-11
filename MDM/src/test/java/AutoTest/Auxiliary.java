package AutoTest;

import MDM.POJO.PartnerPojo;
import MDM.POJO.getBeListPojo;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Auxiliary {

    @DataProvider
    @Description("Позитивные тесты с использованием DataProvider")
    public static Object[][] data() {
        return new Object[][]{
                {"nomenclature", 1},
                {"basic-services", 1},
                {"unified-classifier", 1},
                {"eop", 1},
                {"units", 1},
                {"okpd2", 1},
                {"okved2", 1},
                {"tnved", 1}
        };
    }
        @DataProvider
        @Description("Негативные тесты с использованием DataProvider")
        public static Object[][] dataNegative() {
            return new Object[][]{
                    {"", 1},
                    {"basic-services", 201},
                    {"unified-classifier", 0},
                    {"eop", -1},
                    {"units", Double.MAX_VALUE},
                    {"okpd2", Integer.MAX_VALUE},
                    {"okved2", "123string"},
                    {"tnved", "<script>alert( 'Hello world' );</script>"}
            };
    }

    @Test
    @Description("Получение ОКПД2 по Гуид, валидация при помощи схемы Json")
    public void getSetNewHash() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .when()
                .get("/set-new-hash")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Получение списка бизнес единиц")
    public void getBeList() {
        installSpec(requestSpecification(), responseSpecification());
        List<getBeListPojo> response =
                given()
                        .get("/be")
                        .then().log().all().
                        extract().body().jsonPath().getList(".", getBeListPojo.class);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getNameFull().length() <= 150));
        response.forEach(x -> Assert.assertTrue(x.getName().length() <= 255));
        response.forEach(x -> Assert.assertTrue(x.getInn().length() <= 12));
        response.forEach(x -> Assert.assertEquals(x.getKpp().length(), 9));
        Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test
    @Description("Проверка доступа и авторизации")
    public void getPing() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("/ping")
                .then().log().all();
        deleteSpec();
    }

    @Test(dataProvider = "data")
    @Description("Проверка списка изменений")
    public void getListOfChanges(String type, int step) {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .pathParam("type", type)
                .pathParam("step", step)
                .get("list-of-changes/{type} {step}")
                .then().log().all()
                .assertThat()
                .body("size()", is(step))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getListOfChanges.json"));
        deleteSpec();

    }
    @Test(dataProvider = "dataNegative")
    @Description("Проверка списка изменений")
    public void getListOfChangesNegative(String type, Object step) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("type", type)
                .pathParam("step", step)
                .get("list-of-changes/{type} {step}")
                .then().log().all();
        deleteSpec();

    }
}
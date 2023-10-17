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
                {"nomenclature", 5},
                {"basic-services", 1},
                {"unified-classifier", 1},
                {"eop", 1},
                {"units", 1},
                {"okpd2", 1},
                {"okved2", 1},
                {"tnved", 5}
        };
    }
        @DataProvider
        @Description("Негативные тесты с использованием DataProvider")
        public static Object[][] dataNegative() {
            return new Object[][]{
                    {" ", 5},
                    {"nomenclature", 0},
                    {"basic-services", 201},
                    {"unified-classifier", 1.2},
                    {"eop", Integer.MAX_VALUE},
                    {"units", Double.MAX_VALUE},
                    {"okpd2", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta"},
                    {"eop", "!@#$%^&*(){}[]\"':;/<>\\|№\n"},
                    {"eop", "select*from users"},
                    {"units", -100},
                    {"tnved", "<script>alert( 'Hello world' );</script>"}
            };
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
    public void getListOfChanges(Object type, Object step) {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .pathParam("type", type)
                .queryParam("step", step)
                .get("list-of-changes/{type}")
                .then().log().all()
                .body("size()", is(step))
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getListOfChanges.json"));
        deleteSpec();

    }
    @Test(dataProvider = "dataNegative")
    @Description("Невалидные значения Степ Проверка списка изменений")
    public void getListOfChangesNegative(Object type, Object step) {
        installSpec(requestSpecification(), responseSpecification400());
        given().log().uri()
                .when()
                .pathParam("type", type)
                .queryParam("step", step)
                .get("list-of-changes/{type}")
                .then().log().all();
        deleteSpec();

    }
}
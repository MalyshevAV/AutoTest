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
import org.testng.annotations.Test;

import java.util.List;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;

public class Auxiliary {
    @Test
    @Description("Получение ОКПД2 по Гуид, валидация при помощи схемы Json")
    public void getSetNewHash() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .header("x-se-hash",  "cfcd208495d565ef66e7dff9f98764da")
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
        response.forEach(x-> Assert.assertTrue(x.getNameFull().length() <= 150));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 255));
        response.forEach(x-> Assert.assertTrue(x.getInn().length() <= 12));
        response.forEach(x-> Assert.assertEquals(x.getKpp().length(), 9));
        Assertions.assertNotNull(response);
        deleteSpec();
    }
    
    @Test
    @Description ("Проверка доступа и авторизации")
    public void getPing(){
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("/ping")
                .then().log().all();
        deleteSpec();
    }
}

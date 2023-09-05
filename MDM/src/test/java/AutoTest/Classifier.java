package AutoTest;

import MDM.POJO.UnifiedClassifirePojo;
import MDM.POJO.UnitsPojo;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.assertj.core.error.ShouldNotBeNull;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;

public class Classifier {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://i1c.ddns.net:60380/TEST_KIT_MDM/hs/klass/";
    }

    @Test
    @Description("Получение массива всех категорий Единый классификатор")
    public void getUnifiedClassifierList() {
        List<UnifiedClassifirePojo> response  =
                given()
                        .auth().basic("Administrator", "1234567809")
                       .contentType(ContentType.JSON)
                        .when()
                        .queryParam("step", 5)
                        .get("/unified-classifier")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", UnifiedClassifirePojo.class);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 14));
        response.forEach(x -> Assert.assertEquals(x.getParent().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 100));
        response.forEach(x -> Assert.assertEquals(x.getOwner().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getOkp().length() <= 25));
        response.forEach(x-> Assert.assertTrue(x.getTnved().length() <= 10));
        response.forEach(x-> Assert.assertTrue(x.getOkved().length() <= 7));
        response.forEach(x-> Assert.assertTrue(x.getOkpd2().length() <= 12));
        Assertions.assertNotNull(response);
    }

    @Test
    @Description ("Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuid(){
        given()
                .auth().basic("Administrator", "1234567809").when()
                .get("unified-classifier/8eb9bf84-3507-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnifiedClassifierGuid.json"))
                .statusCode(200);
    }
    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень, валидация Json схема" )
    public void getEopList() {
      given()
                        .auth().basic("Administrator", "1234567809")
                        .contentType(ContentType.JSON)
                        .when()
                        .queryParam("step", 100)
                        .get("/eop")
                        .then().log().all()
                        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopList.json"));

    }

    @Test
    @Description ("Получение единого ограничительный перечень номенклатуры по Гуид")
    public void getEopGuid(){
        given()
                .auth().basic("Administrator", "1234567809")
                .contentType(ContentType.JSON)
                .when()
                .get("eop/362000bb-2f69-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopGuid.json"))
                .statusCode(200);
    }

    @Test
    @Description("Получение массива единиц измерения")
    public void getUnitsList() {
        List<UnitsPojo> response =
                given()
                        .auth().basic("Administrator", "1234567809")
                        .contentType(ContentType.JSON)
                        .when()
                        .queryParam("step", 5,100,200)
                        .get("/units")
                        .then().log().all().statusCode(200)
                        .extract().body().jsonPath().getList(".", UnitsPojo.class).stream().toList();
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 4)); // уточнить
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 25));
        response.forEach(x-> Assert.assertTrue(x.getNameFull().length() <= 100));
        response.forEach(x-> Assert.assertTrue(x.getInternationalReduction().length() <= 3));
        Assertions.assertNotNull(response);
    }
    @Test
    @Description ("Получение единиц измерения по Гуид")
    public void getUnitsGuid(){
        given()
                .auth().basic("Administrator", "1234567809")
                .contentType(ContentType.JSON).when()
                .get("units/85303f5a-e3aa-11e2-91f0-c80aa9301ced")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnitsGuid.json"))
                .statusCode(200);
    }
}



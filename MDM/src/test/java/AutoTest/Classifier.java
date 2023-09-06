package AutoTest;

import MDM.POJO.UnifiedClassifirePojo;
import MDM.POJO.UnitsPojo;
import Specifications.Specifications;
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

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;

public class Classifier {
    @Test
    @Description("Получение массива всех категорий Единый классификатор")
    public void getUnifiedClassifierList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<UnifiedClassifirePojo> response  =
                given()
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
        deleteSpec();
    }

    @Test
    @Description ("Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuid(){
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("unified-classifier/8eb9bf84-3507-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnifiedClassifierGuid.json"));
    }
    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень, валидация Json схема" )
    public void getEopList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
      given()
                        .when()
                        .queryParam("step", 200)
                        .get("/eop")
                        .then().log().all()
                        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopList.json"));
        deleteSpec();
    }

    @Test
    @Description ("Получение единого ограничительный перечень номенклатуры по Гуид")
    public void getEopGuid(){
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("eop/362000bb-2f69-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopGuid.json"));
        deleteSpec();
    }

    @Test
    @Description("Получение массива единиц измерения")
    public void getUnitsList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<UnitsPojo> response =
                given()
                        .when()
                        .queryParam("step", 5,100,200)
                        .get("/units")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", UnitsPojo.class).stream().toList();
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 4)); // уточнить
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 25));
        response.forEach(x-> Assert.assertTrue(x.getNameFull().length() <= 100));
        response.forEach(x-> Assert.assertTrue(x.getInternationalReduction().length() <= 3));
        Assertions.assertNotNull(response);
        deleteSpec();
    }
    @Test
    @Description ("Получение единиц измерения по Гуид")
    public void getUnitsGuid(){
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
               .when()
                .get("units/85303f5a-e3aa-11e2-91f0-c80aa9301ced")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnitsGuid.json"));
        deleteSpec();
    }
}



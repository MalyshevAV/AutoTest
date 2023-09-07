package AutoTest;

import MDM.POJO.UnifiedClassifirePojo;
import MDM.POJO.UnitsPojo;
import Specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

public class Classifier {
    @Test
    @Description("Получение массива всех категорий Единый классификатор")
    public void getUnifiedClassifierList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<UnifiedClassifirePojo> response  =
                given()
                        .when()
                        .queryParam("step", 200)
                        .get("/unified-classifier")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", UnifiedClassifirePojo.class);
        Assertions.assertEquals(response.size(), 200);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 14));
        response.forEach(x -> Assert.assertEquals(x.getParent().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 150));
        response.forEach(x -> Assert.assertEquals(x.getOwner().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getOkp().length() <= 25));
        response.forEach(x-> Assert.assertTrue(x.getTnved().length() <= 10));
        response.forEach(x-> Assert.assertTrue(x.getOkved().length() <= 7));
        response.forEach(x-> Assert.assertTrue(x.getOkpd2().length() <= 12));
        Assertions.assertNotNull(response);
        deleteSpec();
    }
    @Test
    @Description("Получение массива Единый классификатор из 5 объектов")
    public void getUnifiedClassifierListStepEqual5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
                given()
                        .when()
                        .queryParam("step", 5)
                        .get("/unified-classifier")
                        .then().log().all()
                        .body("size()", is(5));
        deleteSpec();
    }
    @Test
    @Description("Получение массива Единый классификатор из 6 объектов")
    public void getUnifiedClassifierListStepEqual6() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 6)
                .get("/unified-classifier")
                .then().log().all()
                .body("size()", is(6));
        deleteSpec();
    }
    @Test
    @Description("Получение массива Единый классификатор из 199 объектов")
    public void getUnifiedClassifierListStepEqual199() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 199)
                .get("/unified-classifier")
                .then().log().all()
                .body("size()", is(199));
        deleteSpec();
    }
    @Test
    @Description("Получение массива Единый классификатор из 100 объектов")
    public void getUnifiedClassifierListStepEqual100() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .get("/unified-classifier")
                .then().log().all()
                .body("size()", is(100));
        deleteSpec();
    }
    @Test
    @Description("Получение массива Единый классификатор, поле Step пустое")
    public void getUnifiedClassifierListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("unified-classifier")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)));
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step (Min-1)")
    public void getUnifiedClassifierListStepMaxMinus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step (Max+1)")
    public void getUnifiedClassifierListStepMaxPlus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step (Max Integer")
    public void getUnifiedClassifierListStepMaxInteger() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2147483647)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step 3 пробела")
    public void getUnifiedClassifierListStepSpaces() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "   ")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step пробел перед числом и после")
    public void getUnifiedClassifierListStepTwoSpacesAndDigit() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", " 200 ")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step Дробное число")
    public void getUnifiedClassifierListStepDoubleType() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2.1)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step пробел в середине числа")
    public void getUnifiedClassifierListStepDigitAndSpace() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "2 1")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step 1024 символа")
    public void getUnifiedClassifierListStep1024() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step равен 0")
    public void getUnifiedClassifierListStepZero() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 0)
                .get("unified-classifier")
                .then().log().all();
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
    @Description ("Негативный тест (Max+1) Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuidMaxPlus(){
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/8eb9bf84-3507-11ee-918f-7824af8ab7212")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnifiedClassifierGuid.json"));
    }
    @Test
    @Description ("Негативный тест (Max-1) Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuidMaxMinus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/8eb9bf84-3507-11ee-918f-7824af8ab72")
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
              .queryParam("step", 5)
              .get("eop")
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
                        .queryParam("step", 5)
                        .get("units")
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



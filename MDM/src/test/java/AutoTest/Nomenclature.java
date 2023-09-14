package AutoTest;

import Specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Nomenclature {

    @Test
    @Description("Получение базовой услуги по Гуид, валидация по схеме Json")
    public void getBasicServicesGuid(){
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("basic-services/937a6068-3d9b-11ee-918f-7824af8ab721/09bdd436-3da3-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBasicServicesGuid.json"));
        deleteSpec();
    }
    @Test
    @Description("НЕ ДОПИЛЕН Получение номенклатуры по Гуид, валидация по схеме Json")
    public void getNomenclatureGuid(){
        installSpec(requestSpecification(), responseSpecification());
        given()
                .auth().basic("Administrator", "1234567809")
                .when()
                .get("nomenclature/937a6068-3d9b-11ee-918f-7824af8ab721/6cec812a-35d6-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureGuid.json"));
        deleteSpec();
    }
//    @Test
//    @Description("Создаем заявку на добавление, изменение, удаление номенклатуры")
//    // Using HashMap
//    public void postNomenclatureBe() throws FileNotFoundException {
//        Map<String, Object> jsonAsMap = new HashMap<>();
//        jsonAsMap.put("firstName", "John");
//        jsonAsMap.put("lastName", "Doe");
//        given()
//                .auth().basic("Administrator", "1234567809").and()
//                .header("x-se-hash",  "cfcd208495d565ef66e7dff9f98764da")
//                .body(jsonAsMap)
//                .when()
//                .post("nomenclature/937a6068-3d9b-11ee-918f-7824af8ab721")
//                .then().log().all()
//                .assertThat()
//               // .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBasicServicesGuid.json"))
//                .statusCode(200);
//   }

    @Test
    @Description("Создаем заявку на добавление, изменение, удаление номенклатуры")
    // Using Json File
    public void postNomenclatureBeUsingJsonFile()  {
        File file = new File("C:\\Users\\Sasha\\TEST\\Auto_test\\MDM\\src\\test\\resources\\postNomenclatureUsingJsonFil.json");

        given()
                .auth().basic("Administrator", "1234567809").and()
                .header("x-se-hash",  "cfcd208495d565ef66e7dff9f98764da")
               // .multiPart(new File(".\\src\\test\\resources\\postNomenclatureUsingJsonFil.json"))
                .body(file)
                .when()
                .post("/nomenclature/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                // .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBasicServicesGuid.json"))
                .statusCode(200);
    }
//////////////////////////////////Поиск номенклатуры/////////////////////////////////////////////////////
    @Test
    @Description("Поиск номенклатуры, валидация Json схема")
    public void getNomenclatureSearch() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 176)
                .queryParam("type", 5)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(176))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, type = 1")
    public void getNomenclatureSearchType1() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", "")
                .queryParam("type", 1)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all()
               .body("size()", is(176))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, type = 2")
    public void getNomenclatureSearchType2() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .queryParam("type", 2)
                .queryParam("data", "01сб")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(100))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, type = 3, data = f3ec794a-35d5-11ee-918f-7824af8ab721 возвращает 2 объекта")
    public void getNomenclatureSearchType3_TwoObjects() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 3)
                .queryParam("data", "f3ec794a-35d5-11ee-918f-7824af8ab721")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(2))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, type = 3, data = f3ec794a-35d5-11ee-918f-7824af8ab720 возвращает 1 объект")
    public void getNomenclatureSearchType3_OneObject() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 200)
                .queryParam("type", 3)
                .queryParam("data", "f3ec794a-35d5-11ee-918f-7824af8ab720")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(1))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }





    @Test
    @Description("Поиск номенклатуры, type = 4")
    public void getNomenclatureSearchType4() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 6)
                .queryParam("type", 4)
                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(6))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, type = 5")
    public void getNomenclatureSearchType5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .queryParam("type", 5)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(100))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, получение пустого тела, data = Bolt ")
    public void getNomenclatureSearchBodyIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 0)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, получение пустого тела, data = Bolt type=1 ")
    public void getNomenclatureSearchBodyIsEmpty1() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 1)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, получение пустого тела, data = Bolt type= 2 ")
    public void getNomenclatureSearchBodyIsEmpty2() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 2)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, получение пустого тела, data = Невалидный Гуид type= 3 ")
    public void getNomenclatureSearchBodyIsEmpty3() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 3)
                .queryParam("data", "f3ec794a-35d5-11ee-918f-7824af8ab7")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, получение пустого тела, data = Bolt type= 4 ")
    public void getNomenclatureSearchBodyIsEmpty4() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 4)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, получение пустого тела data = Bolt type= 5 ")
    public void getNomenclatureSearchBodyIsEmpty5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 5)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0));
        deleteSpec();
    }


    ////////////////////////Поиск номенклатуры, негативные тесты////////////////////////////////
    @Test
    @Description("Поиск номенклатуры, поле data пустое")
    public void getNomenclatureSearchDataIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 100)
                .queryParam("type", 5)
                .queryParam("data", "")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Typу = -1")
    public void getNomenclatureSearchType() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", -1)
                .queryParam("data", "1")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Type= - 0.1")
    public void getNomenclatureSearchTypeNegativeDouble() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", -0.1)
                .queryParam("data", "1")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Type = 5.1")
    public void getNomenclatureSearchTypePositiveDouble() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 5.1)
                .queryParam("data", "1")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Type = 6")
    public void getNomenclatureSearchTypePositiveMaxPlus1() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 6)
                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Type = Инъекция")
    public void getNomenclatureSearchTypeInjection() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", "\"select*from users\"")

                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Type = 2147483647")
    public void getNomenclatureSearchTypeMaxInteger() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 2147483647)
                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Type = Пробелы")
    public void getNomenclatureSearchSpacies() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", " 9 ")
                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Type = Спецсимволы")
    public void getNomenclatureSearchSpecialSymbols() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", "!@#$%^&*(){}[]\"':;/<>\\|№\n")
                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, поле Type = латиница+кириллица+числа+спецсиволы")
    public void getNomenclatureSearchSpecialSymbolвs() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", "sыgf123&* ")
                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Поиск номенклатуры, step = 4, type = 0")
    public void getNomenclatureSearchTypeMinMinus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .queryParam("type", 0)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all();
               // .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }

    @Test
    @Description("Поиск номенклатуры, step = 4, type = 1")
    public void getNomenclatureSearchTypeIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .queryParam("type", 1)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all();
        // .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, step = 4, type = 2")
    public void getNomenclatureSearchType2Step4() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .queryParam("type", 2)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all();
        // .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, step = 4, type = 3")
    public void getNomenclatureSearchType3Step4() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .queryParam("type", 3)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, step = 4, type = 4")
    public void getNomenclatureSearchType4Step4() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .queryParam("type", 4)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, step = 4, type = 5")
    public void getNomenclatureSearchType5Step4() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .queryParam("type", 5)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Поиск номенклатуры, step = 201, type = 3")
    public void getNomenclatureSearchStep201() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201)
                .queryParam("type", 3)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, step = 201.1, type = 1")
    public void getNomenclatureSearchStep201Double() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201.1)
                .queryParam("type", 1)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, step = 0, type = 1")
    public void getNomenclatureSearchStepZero() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 0)
                .queryParam("type", 1)
                .queryParam("data", "01сб")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
    @Test
    @Description("Поиск номенклатуры, step = -1, type = 4")
    public void getNomenclatureSearchStepNegative() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", -1)
                .queryParam("type", 4)
                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Поиск номенклатуры, step = -0.1, type = 5")
    public void getNomenclatureSearchStepNegativeDouble() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", -0.1)
                .queryParam("type", 5)
                .queryParam("data", "00")
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();
    }
}

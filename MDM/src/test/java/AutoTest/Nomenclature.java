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
import static org.hamcrest.Matchers.is;

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

    @Test
    @Description("Поиск номенклатуры, валидация Json схема")
    public void getNomenclatureSearch() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 200)
                .queryParam("type", 1)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(200))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
}

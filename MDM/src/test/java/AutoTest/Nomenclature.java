package AutoTest;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Nomenclature {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://i1c.ddns.net:60380/TEST_KIT_MDM/hs/klass/";
    }

    @Test
    @Description("Получение базовой услуги по Гуид, валидация по схеме Json")
    public void getBasicServicesGuid(){
        given()
                .auth().basic("Administrator", "1234567809").when()
                .get("basic-services/937a6068-3d9b-11ee-918f-7824af8ab721/09bdd436-3da3-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBasicServicesGuid.json"))
                .statusCode(200);
    }
    @Test
    @Description("НЕ ДОПИЛЕН Получение номенклатуры по Гуид, валидация по схеме Json")
    public void getNomenclatureGuid(){
        given()
                .auth().basic("Administrator", "1234567809").when()
                .get("nomenclature/937a6068-3d9b-11ee-918f-7824af8ab721/6cec812a-35d6-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureGuid.json"))
                .statusCode(200);
    }


}

package AutoTest;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Auxiliary {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://i1c.ddns.net:60380/TEST_KIT_MDM/hs/klass/";
    }

    @Test
    @Description("Получение ОКПД2 по Гуид, валидация при помощи схемы Json")
    public void getSetNewHash() {
        given()
                .auth().basic("Administrator", "1234567809")
                .header("x-se-hash",  "cfcd208495d565ef66e7dff9f98764da")
                .when()
                .get("/set-new-hash")
                .then().log().all().statusCode(200);
    }
}

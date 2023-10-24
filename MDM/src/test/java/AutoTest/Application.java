package AutoTest;

import Models.PojoPost;
import Models.Responsible;
import Specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Application {


    @Test
    @Description("Получение заявки по Гуид на изменение Type = 5 , валидация по схеме Json")
    public void getChangeRequestGuid() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("change-request/31c9ca2a-b120-41f8-aa86-8cffb2627492")
                .then().log().all()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getChangeRequest.json"));
        deleteSpec();
    }
    @Test
    @Description("Получение заявки по Гуид на изменение Type = 5 , валидация по схеме Json")
    public void getChangeRequestGuid5() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("change-request/dd91b3fc-36d5-11ee-b5b0-005056013b0c")
                .then().log().all()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getChangeRequest.json"));
        deleteSpec();
    }

    @Test (dataProvider = "guidNegative", dataProviderClass = Nomenclature.class)
    @Description("Негативные тесты Получение изменеий по заявке по Гуид ")
    public void getChangeRequestGuidNegative(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("change-request/{guid}")
                .then().log().all();
        deleteSpec();
    }
}
package AutoTest;

import MDM.POJO.PartnerPojo;
import io.qameta.allure.Description;
import io.restassured.RestAssured;


import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
//import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

import static groovy.xml.dom.DOMCategory.isEmpty;
import static io.restassured.RestAssured.given;
import static org.junit.platform.commons.util.StringUtils.isBlank;

public class Partner {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://i1c.ddns.net:60380/TEST_KIT_MDM/hs/klass/";
        //  RestAssured.port = 443;
    }
    @Test
    @Description("Получение списка партнеров")
    public void getPartnerList(){
       List<PartnerPojo> response =
               given()
                .auth().basic("Administrator", "1234567809")
                       .contentType(ContentType.JSON).when()
                .get("/partner")
                .then().log().all().statusCode(200).
                extract().body().jsonPath().getList(".", PartnerPojo.class);
        response.forEach(x-> Assert.assertFalse(x.getInn().isEmpty())); //(проверка, что нет пустых значений)
        response.forEach(x-> Assert.assertFalse(x.getGuid().isEmpty()));
        response.forEach(x-> Assert.assertFalse(x.getName().isEmpty()));
        response.forEach(x-> Assert.assertFalse(x.getNameFull().isEmpty()));
      //  response.forEach(x-> Assert.assertTrue(x.getKpp().isEmpty()));
    }
    @Test
    @Description("Получение списка партнеров по Гуид")
    public void getPartnerGuid(){
        given()
                .auth().basic("Administrator", "1234567809").when()
                .get("/partner/876f5083-3d9b-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerGuid.json"))
                .statusCode(200);

    }

}


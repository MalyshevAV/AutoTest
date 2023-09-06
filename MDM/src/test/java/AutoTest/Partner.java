package AutoTest;

import MDM.POJO.PartnerPojo;
import Specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.RestAssured;


import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
//import static org.hamcrest.MatcherAssert.assertThat;
import java.net.URL;
import java.util.List;

import static Specifications.Specifications.*;
import static groovy.xml.dom.DOMCategory.isEmpty;
import static io.restassured.RestAssured.*;
import static org.junit.platform.commons.util.StringUtils.isBlank;

public class Partner {
//    @BeforeClass
    @Test
    @Description("Получение списка партнеров")
    public void getPartnerList() {
     installSpec(requestSpecification(), responseSpecification());

        List<PartnerPojo> response =
                given()
                        .queryParam("step", 5)
                        .when()
                        .get("/partner")
                        .then()
                        .log().all()
                        .extract().body().jsonPath().getList(".", PartnerPojo.class);
        response.forEach(x -> Assert.assertFalse(x.getInn().isEmpty())); //(проверка, что нет пустых значений)
        response.forEach(x -> Assert.assertFalse(x.getName().isEmpty()));
        response.forEach(x -> Assert.assertTrue(x.getNameFull().isEmpty()));

        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 100));
        response.forEach(x-> Assert.assertTrue(x.getNameFull().length() <= 150));
        response.forEach(x-> Assert.assertTrue(x.getInn().length() <= 12));
        response.forEach(x-> Assert.assertEquals(x.getKpp().length(), 9));
        deleteSpec();
    }


    @Test
    @Description("Получение списка партнеров по Гуид")
    public void getPartnerGuid(){
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("/partner/876f5083-3d9b-11ee-918f-7824af8ab721")
                .then()
                .log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerGuid.json"));
        deleteSpec();
    }

}


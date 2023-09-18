package AutoTest;

import MDM.POJO.PartnerPojo;
import Specifications.GetPositivedataprovider;
import io.qameta.allure.Description;


import io.restassured.module.jsv.JsonSchemaValidator;

import org.testng.Assert;
import org.testng.annotations.Test;
//import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Partner  {
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
        // Временно меньше 9, поле пустое
       // response.forEach(x-> Assert.assertTrue(x.getKpp().length() <= 9));
        response.forEach(x-> Assert.assertEquals(x.getKpp().length(), 9));
        deleteSpec();
    }
    @Test(dataProvider = "positiveData", dataProviderClass = GetPositivedataprovider.class)
    @Description("Получение списка партнеров")
    public void getPartnerListUsingProvader(int value) {
        installSpec(requestSpecification(), responseSpecification());

        List<PartnerPojo> response =
                given()
                        .queryParam("step", value)
                        .when()
                        .get("/partner")
                        .then()
                        .log().all()
                        .extract().body().jsonPath().getList(".", PartnerPojo.class);
    }


//    @Test
//    @Description("Негативный тест Получение списка партнеров")
//    public void getPartnerListExpected400(S) {
//        List<PartnerPojo> response =
//                given().spec(requestSpecification())
//                        .queryParam("step", )
//                        .when()
//                        .get("/partner")
//                        .then()
//                        .spec(responseSpecification400())
//                        .log().all()
//                        .extract().body().jsonPath().getList(".", PartnerPojo.class);
//    }
//
//







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

    @Test
    @Description("Негативный тест, получение списка партнеров по Гуид")
    public void getPartnerGuidExpected400(){
        given()
                .when()
                .spec(requestSpecification())
                .get("/partner/876f5083-3d9b-11ee-918f-7824af8ab721")
                .then()
                .spec(responseSpecification400())
                .log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerGuid.json"));

    }


}


package AutoTest;

import Specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Nomenclature {


    @DataProvider
    public static Object[][] test() {
        return new Object[][]{
                {5, 0, "Болт"},
                {"", 1, "Болт"},
                {100, 2, "01сб"},
                {6, 4, "00"},
                {176, 5, "Болт"}

        };
    }
        @DataProvider
        public static Object[][] guidNegative() {
            return new Object[][]{

                    {"13513a3e-36d6-11ee-b5b0-005026013b0c1"},
                    {"13513a3e-36d6-11ee-b5b0-005056013b0"},
                    {"43dabce-3c42-11ee-b5b0-005096013b1c1"},
                  //  {"         "}, 403 ошибка
                    {" 13513a3e-36d6-11ee-b5b0-005056013b0c "},
                    {"13513a3e-3 6d6-11ee-b5 b0-005056013b0c"},
                    {"1кк13a3e-36d6-11ee-b5b0-00)&056 013b0c"},
                    //{"!$%^&*(){}[]':;/<>|№*******$$$!@@##"},
                    {-100},
                    {Integer.MAX_VALUE},
                    {Double.MAX_VALUE},
                   // {"<script>alert( 'Hello world' );</script>"},
                    {"Q91MXkSBG2w4bDK9Z9nprYeT4Pd69TGUdDOqWKDrlSKkIZ3JHqi0rA1G5LAfCZ54yEJ3adXLSmgtm4Z5hXMNT3ZqxkqMyqQhE9fze353egOMYAf0tESKpQtqdOzmrqiyvTjC6tCVc6Iqxgyq3TkICV3Hhk7ffbIYkIYXqk6Inktqt9xKmNqCPsemWzKVaXCiQ299HurLBuVTvZeFWYrqnyjl46h1AKLjfkZOMb0vRari1MFJz48qkpFR6RLTTBS2EtLY1rAj7OIw6zACkXgsJkUkMShenn19tEeZKsl3nAwnt4Qk1P1nzHlnSw6Kdl1jvGflS6aLfxrRoqIM0W1TDlUfCfXehzCemTTui7BddecX6aUTcvYHj3eQSYb4tiErgIdN6PMpizjNO4iZjJLTdBh6xtQC9DQKCj1gM8QKUtDYP5sO1SlEcKcjPIC0Q3jQ4yY27NCuLwAiCqdqdiMVjGYsOd90xcdRBtX5tREE7ATqk21riVMXtAIHmBAGZ2jYQ6ZDO86ohend0RPlqMbjg1G3oliIwx5gNX1solpXlUnu1hmA1TgI3mB2qF1d7zgLw9yXykzScvCtOVsvqOAShLQ7GmR9cFJ7jfHN8APVBFMkXUKEVl6NkQhAQ4ApA7ehLXapgDI4JLuaNAWwlos9gEF2eS9VJ4j8F44fksKySH1IdSkcKR0fk9KX5pIxUQ7KWfWL6aALwY9hXvTtlHWBS62rAPT2VliYrbt9rCz8UVYGyxF9Dm43WvR6xrht8fFrOCVzhRvBreXHsyqwAE4Mzg7NMG48OXKLbo7ENp2bN7L1ppoLfF75wEDx5ecbTuFEg3YS4yDtKNdreHOei2bh1moaos3Zzum6WXZWHhrzFHtris4t8QZygCaNUTeaaONxRuFZtpz91ynjBF0gNFo5G0avIZHo0L5m5SYjXi41iVh8UOHAw2LxqpsVBaXDZ22nM2CWw1fmCgGsK1Jq6QDjEzul2GGZse3qwLxIokcqlVzKuGLrLJ2DDwhoWBovx2du"}



            };
        }

    @Test
    @Description("Получение базовой услуги по Гуид, валидация по схеме Json")
    public void getBasicServicesGuid() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("basic-services/843dabce-3c42-11ee-b5b0-005056013b0c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBasicServicesGuid.json"));
        deleteSpec();
    }
    @Test(dataProvider = "guidNegative")
    @Description("Получение базовой услуги по Гуид, негативные тесты")
    public void getBasicServicesGuidDataProvider(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("basic-services/{guid}")
                .then().log().all();
        deleteSpec();
        System.out.println("Значение Гуид: " + guid);
    }

    @Test
    @Description("Получение базовой услуги по Гуид, негативные тесты проверкаДата провайдер")
    public void getBasicServicesGuidDataProviderMaxMinus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("basic-services/2.147483647")
                .then().log().all();
        deleteSpec();
    }



////////////////////////////////////////////"Получение номенклатуры по Гуид////////////////////////////////////////
    @Test
    @Description("Получение номенклатуры по Гуид, валидация по схеме Json")
    public void getNomenclatureGuid() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("nomenclature/13513a3e-36d6-11ee-b5b0-005056013b0c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative")
    @Description("Получение нономенклатуры по Гуид, негативные тесты")
    public void getNomenclatureGuidDataProvider(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("nomenclature/{guid}")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Получение номенклатуры по Гуид, валидация по схеме Json")
    public void getNomenclatureGuidTest() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("nomenclature/-100")
                .then().log().all();
        deleteSpec();
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
    @Description("Поиск номенклатуры, type = 3, data = 8e7275eb-3049-11ee-b5ae-005056013b0c возвращает 2 объекта")
    public void getNomenclatureSearchType3_TwoObjects() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 3)
                .queryParam("data", "6bfc4888-5dd9-11ee-b451-7824af8ab721")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(2))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
 //   @Test
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
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
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
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
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
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
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
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));

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
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
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
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
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
                .queryParam("type",Integer.MAX_VALUE)
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
    public void getNomenclatureSearchSpecialSymbolsString() {
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

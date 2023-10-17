package AutoTest;


import MDM.POJO.OkpdPojo;
import MDM.POJO.TnvdPojo;
import Specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.testng.AssertJUnit.assertTrue;

public class ClassifireIsAllRussian {
    @Test
    @Description("Получение списка Okpd2 ")
    public void getOkpdList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<OkpdPojo> response  =
                given()
                        .when()
                        .queryParam("step", 200)
                        .get("/okpd2")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", OkpdPojo.class);
        Assertions.assertEquals(response.size(), 200);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 12));
        response.forEach(x-> Assert.assertTrue(x.getNameFull().length() <= 100));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 150));
        response.forEach(x -> Assert.assertTrue(x.getDateOutputArchive().length() <=20));
        Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test
    @Description("Получение списка ОКПД из 5 объектов")
    public void getOkpdListStepEqual5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 1)
                .get("/okpd2")
                .then().log().all()
                .body("size()", is(1));
        deleteSpec();
    }

    @Test
    @Description("Получение массива ОКПД из 6 объектов")
    public void getOkpdListStepEqual6() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 6)
                .get("okpd2")
                .then().log().all()
                .body("size()", is(6));
        deleteSpec();
    }

    @Test
    @Description("Получение массива ОКПД из 199 объектов")
    public void getOkpdListStepEqual199() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 199)
                .get("okpd2")
                .then().log().all()
                .body("size()", is(199));
        deleteSpec();
    }

    @Test
    @Description("Получение массива ОКПД из 100 объектов")
    public void getOkpdListStepEqual100() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .get("okpd2")
                .then().log().all()
                .body("size()", is(100));
        deleteSpec();
    }

    @Test
    @Description("Получение массива ОКПД, поле Step пустое")
    public void getOkpdListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("okpd2")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)));
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step 1")
    public void getOkpdListStepMinMinus() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 1)
                .get("okpd2")
                .then().log().all()
                .body("size()", is(1));;
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step (Max+1)")
    public void getOkpdListStepMaxPlus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201)
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step Max Integer")
    public void getOkpdListStepMaxInteger() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2147483647)
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step 3 пробела")
    public void getOkpdListStepSpaces() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "   ")
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step пробел перед числом и после")
    public void getOkpdListStepTwoSpacesAndDigit() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", " 200 ")
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step Дробное число")
    public void getOkpdListStepDoubleType() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2.1)
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step пробел в середине числа")
    public void getOkpdListStepDigitAndSpace() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "2 1")
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step 1024 символа")
    public void getOkpdListStep1024() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step равен 0")
    public void getOkpdListStepZero() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 0)
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step строка цифры+латинница")
    public void getOkpdListStepDigitLatin() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "123DWQ")
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step строка Спецсимволы")
    public void getOkpdListStepSpecialSymbol() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "!@#$%^&*(){}[]\"':;/<>\\|№\n")
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step строка select")
    public void getOkpdListStepSelect() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "select*From users")
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step отрицательное число")
    public void getOkpdListStepNegativeNumber() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", -100)
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ОКПД, поле Step Инъекция")
    public void getOkpdListStepInjection() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "<script>alert( 'Hello world' );</script>")
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    ///////////////////////////Получение ОКПД2 по Гуид//////////////////////////////////////

    @Test
    @Description("Получение ОКПД2 по Гуид, валидация при помощи схемы Json")
    public void getOkpdGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
       given()
                .when()
                .get("/okpd2/15841c5e-1973-11ee-b5ac-005056013b0c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedGuid.json"));
                // поля в ответе Оквед и ОКПД одинаковые;
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид, несуществующий Гуид 36 символов")
    public void getOkpdGuidNotExist() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/09bdd436-3da3-11ee-918f-7824af8ab720")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест (Max+1) Получение ОКПД2 по Гуид")
    public void getOkpdGuidMaxPlus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/8eb9bf84-3507-11ee-918f-7824af8ab7211")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест (Max-1) Получение ОКПД2 по Гуид")
    public void getOkpdGuidMaxMinus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/8eb9bf84-3507-11ee-918f-7824af8ab72")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид, только пробелы")
    public void getOkpdGuidSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/      ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид, пробелы в начале и в конце")
    public void getOkpdGuidTwoSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/ 8eb9bf84-3507-11ee-918f-7824af8ab721 ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид, пробелы в середине строки")
    public void getOkpdSpaciesIn() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/8eb9bf84-35  07-11ee-918f-7824af8ab721")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид, комбинация латинница, спецсимволы, кириллица числа")
    public void getOkpdGuidCombination() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/8кb()ав84-3!07-11зз-918f-7824af8ab?*1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид, Select")
    public void getOkpdGuidSelect() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/select")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид, отрицательное число")
    public void getOkpdGuidNegativeNumber() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/-1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид,Инъекция")
    public void getOkpdGuidInjection() {
        installSpec(requestSpecification(), responseSpecification404());
        given()
                .when()
                .get("okpd2/<script>alert( 'Hello world' );</script>")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ОКПД2 по Гуид, 1024 буквы")
    public void getOkpdGuid1024letters() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okpd2/gxswhxtknhsdoploxxjapfjmpkpczxmdlfjdkgrthqzzvlqikubahotafuuhdijsgupmqqtaayjhqnvutpsrbnqcgypgavjchnwryowuryjoavwmedyzemvfcwgxaddhpzmenrhryfotslitzzvpnwpbqhevugimknufhmqokkojtrnebnppbcvdfqzfpfaznrpdiqcjlmcshvhaygannurnuwqarnuqesaghlzhsbzzjtnnmnppsxhfjgxbgyskjifyvprwzlbdstrzpwczkkijscuumeutjxkghhujpycvizixjvuimgllrlxrcffxkaywnxuedexowpaovdhzxjomnmicqlmqsfyzdmicuvbsviwxmgqggiabffsczvqblvdjvfclpelnwmvaiuzfgrkhvbrxezehgnqtnnylvoixmydujlhiqttgwqmuoapxewvzwlmhfufzewpiqalfwddblvisdebxuqvitcbrmdesaneekmeoldibydhgpwmdgyhkggndvnqdngtqyacqffhkqsmmumjqxfghockypfyfvdslkmaeegakuakdwucpridzjnqsluqhezjorgoonzfvhvmulysknczvtewsadocupjbxkyeqyqekamtsvcsaitjuthpsbllxhemshnxsocunelzfglqqbvzobuouaictkwezixyoghfujiiykhtdjyxatgeihskjcweynknedplkbddtudmnrvhfvozalpgjlhmkdfnxzuwowtmbsxcsqnxbpiegkufpixpcnyeeekfubvspwaietagusqngnfbjozfmillxruvplvtvxnawkxppmmesqawzcezinmllmomgbpgompvkyyxjvuyolrskxrhgzjqkuaaxdonslbsgukrpbtbpmtcswaqsvdrwxlqhxccnwwegsuntuzxxzhbvleoogqlzbnmmuqenbfkhzcpkevmcxxwptgapageocdtsmfvnbtzljlywxydkpzjojfyjyihfinmwjengjor\n")
                .then().log().all();
    }

    ///////////////////////// Получение списка Okved  /////////////////////////////////////
    @Test
    @Description("Получение списка OKVED ")
    public void getOkvedList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
       // Response response  =
                given()
                        .when()
                        .queryParam("step", 200)
                        .get("/okved2")
                        .then().log().all()
                        .body("size()", is(200))
                        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedList.json"));

//        JsonPath jsonPath = response.jsonPath();
//        List<String> guid = jsonPath.get("guid");
//        List<String> name = jsonPath.get("name");
//        List<String> nameFull = jsonPath.get("nameFull");
//        List<String> code = jsonPath.get("code");
//        List<String> archive = jsonPath.get("archive");
//        List<String> dateOutputArchive = jsonPath.get("dateOutputArchive");

//        for (String s : guid) {
//            Assert.assertFalse(s.isEmpty());
//            Assert.assertEquals(s.length(), 36);
//        }
//        Assert.assertTrue(name.stream().allMatch(x->x.length() <= 150));
//        Assert.assertTrue(code.stream().allMatch(x->x.length() <= 8));
//        Assert.assertTrue(nameFull.stream().allMatch(x->x.length() <= 150));
//        Assert.assertNotNull(archive);
//        Assert.assertTrue(dateOutputArchive.stream().allMatch(x->x.length() <=19));
        deleteSpec();
    }

    @Test
    @Description("Получение списка OKVED из 5 объектов")
    public void getOkvedListStepEqual5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .get("okved2")
                .then().log().all()
                .body("size()", is(5));
        deleteSpec();
    }

    @Test
    @Description("Получение массива OKVED из 6 объектов")
    public void getOkvedListStepEqual6() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 6)
                .get("okved2")
                .then().log().all()
                .body("size()", is(6))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedList.json"));
        deleteSpec();
    }

    @Test
    @Description("Получение массива OKVED из 199 объектов")
    public void getOkvedListStepEqual199() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 199)
                .get("okved2")
                .then().log().all()
                .body("size()", is(199))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedList.json"));
        deleteSpec();
    }

    @Test
    @Description("Получение массива OKVED из 100 объектов")
    public void getOkvedListStepEqual100() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .get("okved2")
                .then().log().all()
                .body("size()", is(100))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedList.json"));
        deleteSpec();
    }

    @Test
    @Description("Получение массива OKVED, поле Step пустое")
    public void getOkvedListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("okved2")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedList.json"));
        deleteSpec();
    }

    @Test
    @Description("Получение массива OKVED, поле Step 1")
    public void getOkvedListStepMinMinus() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 1)
                .get("okved2")
                .then().log().all()
                .body("size()", is(1));;
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step (Max+1)")
    public void getOkvedListStepMaxPlus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201)
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step Max Integer")
    public void getOkvedListStepMaxInteger() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2147483647)
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step 3 пробела")
    public void getOkvedListStepSpaces() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "   ")
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step пробел перед числом и после")
    public void getOkvedListStepTwoSpacesAndDigit() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", " 200 ")
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step Дробное число")
    public void getOkvedListStepDoubleType() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2.1)
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step пробел в середине числа")
    public void getOkvedListStepDigitAndSpace() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "2 1")
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step 1024 символа")
    public void getOkvedListStep1024() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step равен 0")
    public void getOkvedListStepZero() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 0)
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step строка цифры+латинница")
    public void getOkvedListStepDigitLatin() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "123DWQ")
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step строка Спецсимволы")
    public void getOkvedListStepSpecialSymbol() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "!@#$%^&*(){}[]\"':;/<>\\|№\n")
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step строка select")
    public void getOkvedListStepSelect() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "select*From users")
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step отрицательное число")
    public void getOkvedListStepNegativeNumber() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", -100)
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива OKVED, поле Step Инъекция")
    public void getOkvedListStepInjection() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "<script>alert( 'Hello world' );</script>")
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }



///////////////////////// Получение Okved по Гуид, /////////////////////////////////////
    @Test
    @Description("Получение Okved по Гуид, валидация при помощи схемы Json")
    public void getOkvedGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("/okved2/377593d9-168f-11ee-b5ab-a0dc07f9a67b")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedGuid.json"));
        deleteSpec();
    }
    @Test
    @Description("Негативный тест Получение Okved по Гуид, несуществующий Гуид 36 символов")
    public void getOkvedGuidNotExist() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/09bdd436-3da3-11ee-918f-7824af8ab720")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест (Max+1) Получение Okved по Гуид")
    public void getOkvedGuidMaxPlus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/8eb9bf84-3507-11ee-918f-7824af8ab7211")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест (Max-1) Получение Okved по Гуид")
    public void getOkvedGuidMaxMinus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/8eb9bf84-3507-11ee-918f-7824af8ab72")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение Okved по Гуид, только пробелы")
    public void getOkvedGuidSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/      ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение Okved по Гуид, пробелы в начале и в конце")
    public void getOkvedGuidTwoSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/ 8eb9bf84-3507-11ee-918f-7824af8ab721 ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение Okved по Гуид, пробелы в середине строки")
    public void getOkvedSpaciesIn() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/8eb9bf84-35  07-11ee-918f-7824af8ab721")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение Okved по Гуид, комбинация латинница, спецсимволы, кириллица числа")
    public void getOkvedGuidCombination() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/8кb()ав84-3!07-11зз-918f-7824af8ab?*1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение Okved по Гуид, Select")
    public void getOkvedGuidSelect() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/select")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение Okved по Гуид, отрицательное число")
    public void getOkvedGuidNegativeNumber() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/-1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение Okved по Гуид,Инъекция")
    public void getOkvedGuidInjection() {
        installSpec(requestSpecification(), responseSpecification404());
        given()
                .when()
                .get("okved2/<script>alert( 'Hello world' );</script>")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение Okved по Гуид, 1024 буквы")
    public void getOkvedGuid1024letters() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("okved2/gxswhxtknhsdoploxxjapfjmpkpczxmdlfjdkgrthqzzvlqikubahotafuuhdijsgupmqqtaayjhqnvutpsrbnqcgypgavjchnwryowuryjoavwmedyzemvfcwgxaddhpzmenrhryfotslitzzvpnwpbqhevugimknufhmqokkojtrnebnppbcvdfqzfpfaznrpdiqcjlmcshvhaygannurnuwqarnuqesaghlzhsbzzjtnnmnppsxhfjgxbgyskjifyvprwzlbdstrzpwczkkijscuumeutjxkghhujpycvizixjvuimgllrlxrcffxkaywnxuedexowpaovdhzxjomnmicqlmqsfyzdmicuvbsviwxmgqggiabffsczvqblvdjvfclpelnwmvaiuzfgrkhvbrxezehgnqtnnylvoixmydujlhiqttgwqmuoapxewvzwlmhfufzewpiqalfwddblvisdebxuqvitcbrmdesaneekmeoldibydhgpwmdgyhkggndvnqdngtqyacqffhkqsmmumjqxfghockypfyfvdslkmaeegakuakdwucpridzjnqsluqhezjorgoonzfvhvmulysknczvtewsadocupjbxkyeqyqekamtsvcsaitjuthpsbllxhemshnxsocunelzfglqqbvzobuouaictkwezixyoghfujiiykhtdjyxatgeihskjcweynknedplkbddtudmnrvhfvozalpgjlhmkdfnxzuwowtmbsxcsqnxbpiegkufpixpcnyeeekfubvspwaietagusqngnfbjozfmillxruvplvtvxnawkxppmmesqawzcezinmllmomgbpgompvkyyxjvuyolrskxrhgzjqkuaaxdonslbsgukrpbtbpmtcswaqsvdrwxlqhxccnwwegsuntuzxxzhbvleoogqlzbnmmuqenbfkhzcpkevmcxxwptgapageocdtsmfvnbtzljlywxydkpzjojfyjyihfinmwjengjor\n")
                .then().log().all();
    }



//////////////////////////////////Получение списка ТНВЕД ///////////////////////////////////////////


    @Test
    @Description("Получение списка ТНВEД")
    public void getTnvedList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<TnvdPojo> response  =
                given()
                .when()
                .queryParam("step", 200)
                .get("/tnved")
                .then().log().all()
                        .extract().body().jsonPath().getList(".", TnvdPojo.class);
        Assertions.assertEquals(response.size(), 200);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 10));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 150));
        response.forEach(x-> Assert.assertTrue(x.getNameFull().length() <= 100));
        response.forEach(x -> Assert.assertEquals(x.getUnit().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getDateOutputArchive().length() <=20));
        Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test
    @Description("Получение списка ТНВEД из 5 объектов")
    public void getTnvedListStepEqual5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .get("tnved")
                .then().log().all()
                .body("size()", is(5))
                .extract().body().jsonPath().getList(".", TnvdPojo.class);
        deleteSpec();
    }

    @Test
    @Description("Получение массива ТНВEД из 6 объектов")
    public void getTnvedListStepEqual6() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 6)
                .get("tnved")
                .then().log().all()
                .body("size()", is(6))
                .extract().body().jsonPath().getList(".", TnvdPojo.class);
        deleteSpec();
    }

    @Test
    @Description("Получение массива ТНВEД из 199 объектов")
    public void getTnvedListStepEqual199() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 199)
                .get("tnved")
                .then().log().all()
                .body("size()", is(199))
                .extract().body().jsonPath().getList(".", TnvdPojo.class);
        deleteSpec();
    }

    @Test
    @Description("Получение массива ТНВEД из 100 объектов")
    public void getTnvedListStepEqual100() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .get("tnved")
                .then().log().all()
                .body("size()", is(100))
                .extract().body().jsonPath().getList(".", TnvdPojo.class);
        deleteSpec();
    }

    @Test
    @Description("Получение массива ТНВEД, поле Step пустое")
    public void getTnvedListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("tnved")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .extract().body().jsonPath().getList(".", TnvdPojo.class);
        deleteSpec();
    }

    @Test
    @Description("Получение массива ТНВEД, поле Step 1")
    public void getTnvedListStepMin() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 1)
                .get("tnved")
                .then().log().all()
                .body("size()", is(1));
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step (Max+1)")
    public void getTnvedListStepMaxPlus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201)
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step Max Integer")
    public void getTnvedListStepMaxInteger() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", Integer.MAX_VALUE)
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

  //  @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step 3 пробела")
    public void getTnvedListStepSpaces() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "   ")
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step пробел перед числом и после")
    public void getTnvedListStepTwoSpacesAndDigit() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", " 200 ")
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step Дробное число")
    public void getTnvedListStepDoubleType() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2.1)
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step пробел в середине числа")
    public void getTnvedListStepDigitAndSpace() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "2 1")
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step 1024 символа")
    public void getTnvedListStep1024() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step равен 0")
    public void getTnvedListStepZero() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 0)
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step строка цифры+латинница")
    public void getTnvedListStepDigitLatin() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "123DWQ")
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step строка Спецсимволы")
    public void getTnvedListStepSpecialSymbol() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "!@#$%^&*(){}[]\"':;/<>\\|№\n")
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step строка select")
    public void getTnvedListStepSelect() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "select*From users")
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step отрицательное число")
    public void getTnvedListStepNegativeNumber() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", -100)
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива ТНВEД, поле Step Инъекция")
    public void getTnvedListStepInjection() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "<script>alert( 'Hello world' );</script>")
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение ТНВЕД по Гуид/////////////////////////////////////////

    @Test
    @Description("Получение ТНВЕД по Гуид, валидация при помощи схемы Json")
    public void getTnvedGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("/tnved/8c496b15-23e3-11ee-b5ac-005056013b0c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getTvendGuid.json"));
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид, несуществующий Гуид 36 символов")
    public void getTnvedGuidNotExist() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/09bdd436-3da3-11ee-918f-7824af8ab720")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест (Max+1) Получение ТНВЕД по Гуид")
    public void getTnvedGuidMaxPlus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/f8299582-32a7-11ee-918f-7824af8ab7211")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест (Max-1) Получение ТНВЕД по Гуид")
    public void getTnvedGuidMaxMinus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/f8299582-32a7-11ee-918f-7824af8ab72")
                .then().log().all();
    }

 //   @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид, только пробелы")
    public void getTnvedGuidSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/      ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид, пробелы в начале и в конце")
    public void getTnvedGuidTwoSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/ f8299582-32a7-11ee-918f-7824af8ab721 ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид, пробелы в середине строки")
    public void getTnvedSpaciesIn() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/f8299582-32a7-11ee-9  18f-7824af8ab721")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид, комбинация латинница, спецсимволы, кириллица числа")
    public void getTnvedGuidCombination() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/f829??82-32a7-11ee-9ййf-7824af8ab721")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид, Select")
    public void getTnvedGuidSelect() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/select*from users")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид, отрицательное число")
    public void getTnvedGuidNegativeNumber() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/-1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид,Инъекция")
    public void getTnvedGuidInjection() {
        installSpec(requestSpecification(), responseSpecification404());
        given()
                .when()
                .get("tnved/<script>alert( 'Hello world' );</script>")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение ТНВЕД по Гуид, 1024 буквы")
    public void getTnvedGuid1024letters() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("tnved/gxswhxtknhsdoploxxjapfjmpkpczxmdlfjdkgrthqzzvlqikubahotafuuhdijsgupmqqtaayjhqnvutpsrbnqcgypgavjchnwryowuryjoavwmedyzemvfcwgxaddhpzmenrhryfotslitzzvpnwpbqhevugimknufhmqokkojtrnebnppbcvdfqzfpfaznrpdiqcjlmcshvhaygannurnuwqarnuqesaghlzhsbzzjtnnmnppsxhfjgxbgyskjifyvprwzlbdstrzpwczkkijscuumeutjxkghhujpycvizixjvuimgllrlxrcffxkaywnxuedexowpaovdhzxjomnmicqlmqsfyzdmicuvbsviwxmgqggiabffsczvqblvdjvfclpelnwmvaiuzfgrkhvbrxezehgnqtnnylvoixmydujlhiqttgwqmuoapxewvzwlmhfufzewpiqalfwddblvisdebxuqvitcbrmdesaneekmeoldibydhgpwmdgyhkggndvnqdngtqyacqffhkqsmmumjqxfghockypfyfvdslkmaeegakuakdwucpridzjnqsluqhezjorgoonzfvhvmulysknczvtewsadocupjbxkyeqyqekamtsvcsaitjuthpsbllxhemshnxsocunelzfglqqbvzobuouaictkwezixyoghfujiiykhtdjyxatgeihskjcweynknedplkbddtudmnrvhfvozalpgjlhmkdfnxzuwowtmbsxcsqnxbpiegkufpixpcnyeeekfubvspwaietagusqngnfbjozfmillxruvplvtvxnawkxppmmesqawzcezinmllmomgbpgompvkyyxjvuyolrskxrhgzjqkuaaxdonslbsgukrpbtbpmtcswaqsvdrwxlqhxccnwwegsuntuzxxzhbvleoogqlzbnmmuqenbfkhzcpkevmcxxwptgapageocdtsmfvnbtzljlywxydkpzjojfyjyihfinmwjengjor\n")
                .then().log().all();
    }
}


package AutoTest;

import MDM.POJO.UnifiedClassifirePojo;
import MDM.POJO.UnitsPojo;
import Specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

public class Classifier {
    @Test
    @Description("Получение массива всех категорий Единый классификатор")
    public void getUnifiedClassifierList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<UnifiedClassifirePojo> response =
                given()
                        .when()
                        .queryParam("step", 200)
                        .get("/unified-classifier")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", UnifiedClassifirePojo.class);
        Assertions.assertEquals(response.size(), 200);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        //Схему до 20 увеличили
        response.forEach(x -> Assert.assertTrue(x.getCode().length() <= 14));
        response.forEach(x -> Assert.assertEquals(x.getParent().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getName().length() <= 150));
        response.forEach(x -> Assert.assertEquals(x.getOwner().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getOkp().length() <= 25));
        response.forEach(x -> Assert.assertTrue(x.getTnved().length() <= 10));
        response.forEach(x -> Assert.assertTrue(x.getOkved().length() <= 7));
        response.forEach(x -> Assert.assertTrue(x.getOkpd2().length() <= 12));
        Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test
    @Description("Получение массива Единый классификатор из 5 объектов")
    public void getUnifiedClassifierListStepEqual5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .get("/unified-classifier")
                .then().log().all()
                .body("size()", is(5));
        deleteSpec();
    }

    @Test
    @Description("Получение массива Единый классификатор из 6 объектов")
    public void getUnifiedClassifierListStepEqual6() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 6)
                .get("/unified-classifier")
                .then().log().all()
                .body("size()", is(6));
        deleteSpec();
    }

    @Test
    @Description("Получение массива Единый классификатор из 199 объектов")
    public void getUnifiedClassifierListStepEqual199() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 199)
                .get("/unified-classifier")
                .then().log().all()
                .body("size()", is(199));
        deleteSpec();
    }

    @Test
    @Description("Получение массива Единый классификатор из 100 объектов")
    public void getUnifiedClassifierListStepEqual100() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .get("/unified-classifier")
                .then().log().all()
                .body("size()", is(100));
        deleteSpec();
    }

    @Test
    @Description("Получение массива Единый классификатор, поле Step пустое")
    public void getUnifiedClassifierListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("unified-classifier")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)));
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step (Min-1)")
    public void getUnifiedClassifierListStepMinMinus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step (Max+1)")
    public void getUnifiedClassifierListStepMaxPlus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step Max Integer")
    public void getUnifiedClassifierListStepMaxInteger() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2147483647)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step 3 пробела")
    public void getUnifiedClassifierListStepSpaces() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "   ")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step пробел перед числом и после")
    public void getUnifiedClassifierListStepTwoSpacesAndDigit() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", " 200 ")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step Дробное число")
    public void getUnifiedClassifierListStepDoubleType() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2.1)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step пробел в середине числа")
    public void getUnifiedClassifierListStepDigitAndSpace() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "2 1")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step 1024 символа")
    public void getUnifiedClassifierListStep1024() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step равен 0")
    public void getUnifiedClassifierListStepZero() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 0)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step строка цифры+латинница")
    public void getUnifiedClassifierListStepDigitLatin() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "123DWQ")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step строка Спецсимволы")
    public void getUnifiedClassifierListStepSpecialSymbol() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "!@#$%^&*(){}[]\"':;/<>\\|№\n")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step строка select")
    public void getUnifiedClassifierListStepSelect() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "select*From users")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step отрицательное число")
    public void getUnifiedClassifierListStepNegativeNumber() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", -100)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step Инъекция")
    public void getUnifiedClassifierListStepInjection() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "<script>alert( 'Hello world' );</script>")
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }


    @Test
    @Description("Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("unified-classifier/8eb9bf84-3507-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnifiedClassifierGuid.json"));
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид, несуществующий Гуид 36 символов")
    public void getUnifiedClassifierGuidNotExist() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/09bdd436-3da3-11ee-918f-7824af8ab720")
                .then().log().all();

    }

    @Test
    @Description("Негативный тест (Max+1) Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuidMaxPlus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/8eb9bf84-3507-11ee-918f-7824af8ab7211")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnifiedClassifierGuid.json"));
    }

    @Test
    @Description("Негативный тест (Max-1) Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuidMaxMinus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/8eb9bf84-3507-11ee-918f-7824af8ab72")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид, только пробелы")
    public void getUnifiedClassifierGuidSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/      ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид, пробелы в начале и в конце")
    public void getUnifiedClassifierGuidTwoSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/ 8eb9bf84-3507-11ee-918f-7824af8ab721 ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид, пробелы в середине строки")
    public void getUnifiedClassifierGuidSpaciesIn() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/8eb9bf84-35  07-11ee-918f-7824af8ab721")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид, комбинация латинница, спецсимволы, кириллица числа")
    public void getUnifiedClassifierGuidCombination() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/8кb()ав84-3!07-11зз-918f-7824af8ab?*1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид, Select")
    public void getUnifiedClassifierGuidSelect() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/select")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид, отрицательное число")
    public void getUnifiedClassifierGuidNegativeNumber() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/-1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид,Инъекция")
    public void getUnifiedClassifierGuidInjection() {
        installSpec(requestSpecification(), responseSpecification404());
        given()
                .when()
                .get("unified-classifier/<script>alert( 'Hello world' );</script>")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого классификатора по Гуид, 1024 буквы")
    public void getUnifiedClassifierGuid1024letters() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("unified-classifier/gxswhxtknhsdoploxxjapfjmpkpczxmdlfjdkgrthqzzvlqikubahotafuuhdijsgupmqqtaayjhqnvutpsrbnqcgypgavjchnwryowuryjoavwmedyzemvfcwgxaddhpzmenrhryfotslitzzvpnwpbqhevugimknufhmqokkojtrnebnppbcvdfqzfpfaznrpdiqcjlmcshvhaygannurnuwqarnuqesaghlzhsbzzjtnnmnppsxhfjgxbgyskjifyvprwzlbdstrzpwczkkijscuumeutjxkghhujpycvizixjvuimgllrlxrcffxkaywnxuedexowpaovdhzxjomnmicqlmqsfyzdmicuvbsviwxmgqggiabffsczvqblvdjvfclpelnwmvaiuzfgrkhvbrxezehgnqtnnylvoixmydujlhiqttgwqmuoapxewvzwlmhfufzewpiqalfwddblvisdebxuqvitcbrmdesaneekmeoldibydhgpwmdgyhkggndvnqdngtqyacqffhkqsmmumjqxfghockypfyfvdslkmaeegakuakdwucpridzjnqsluqhezjorgoonzfvhvmulysknczvtewsadocupjbxkyeqyqekamtsvcsaitjuthpsbllxhemshnxsocunelzfglqqbvzobuouaictkwezixyoghfujiiykhtdjyxatgeihskjcweynknedplkbddtudmnrvhfvozalpgjlhmkdfnxzuwowtmbsxcsqnxbpiegkufpixpcnyeeekfubvspwaietagusqngnfbjozfmillxruvplvtvxnawkxppmmesqawzcezinmllmomgbpgompvkyyxjvuyolrskxrhgzjqkuaaxdonslbsgukrpbtbpmtcswaqsvdrwxlqhxccnwwegsuntuzxxzhbvleoogqlzbnmmuqenbfkhzcpkevmcxxwptgapageocdtsmfvnbtzljlywxydkpzjojfyjyihfinmwjengjor\n")
                .then().log().all();
    }
///////////////////////////////////////////getEopList//////////////////////////////////////////////////////

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень, валидация Json схема")
    public void getEopList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 200)
                .get("eop")
                .then().log().all()
                .body("size()", is(200))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopList.json"));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень из 5 объектов")
    public void getEopListStepEqual5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .get("/eop")
                .then().log().all()
                .body("size()", is(5));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень из 6 объектов")
    public void getEopListStepEqual6() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 6)
                .get("/eop")
                .then().log().all()
                .body("size()", is(6));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень из 199 объектов")
    public void getEopListStepEqual199() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 199)
                .get("/eop")
                .then().log().all()
                .body("size()", is(199));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень из 100 объектов")
    public void getEopListStepEqual100() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .get("/eop")
                .then().log().all()
                .body("size()", is(100));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень, поле Step пустое")
    public void getEopListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("eop")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)));
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step (Min-1)")
    public void getEopListStepMinMinus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step (Max+1)")
    public void getEopListStepMaxPlus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201)
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step Max Integer")
    public void getEopListStepMaxInteger() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2147483647)
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step 3 пробела")
    public void getEopListStepSpaces() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "   ")
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step пробел перед числом и после")
    public void getEopListStepTwoSpacesAndDigit() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", " 200 ")
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step Дробное число")
    public void getEopListStepDoubleType() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2.1)
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step пробел в середине числа")
    public void getEopListStepDigitAndSpace() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "2 1")
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step 1024 символа")
    public void getEopListStep1024() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step равен 0")
    public void getEopListStepZero() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 0)
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step строка цифры+латинница")
    public void getEopListStepDigitLatin() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "123DWQ")
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый классификатор, поле Step строка Спецсимволы")
    public void getEopListStepSpecialSymbol() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "!@#$%^&*(){}[]\"':;/<>\\|№\n")
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step строка select")
    public void getEopListStepSelect() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "select*From users")
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step отрицательное число")
    public void getEopListStepNegativeNumber() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", -100)
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива Единый ограничительный перечень, поле Step Инъекция")
    public void getEopListStepInjection() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "<script>alert( 'Hello world' );</script>")
                .get("eop")
                .then().log().all();
        deleteSpec();
    }


    ///////////getEopGuid///////////

    @Test
    @Description("Получение единого ограничительного переченя номенклатуры по Гуид")
    public void getEopGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("eop/362000bb-2f69-11ee-918f-7824af8ab721")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopGuid.json"));
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение единого ограничительного перечня по Гуид, несуществующий Гуид 36 символов")
    public void getEopGuidNotExist() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/09bdd436-3da3-11ee-918f-5824af8ab723")
                .then().log().all();

    }

    @Test
    @Description("Негативный тест (Max+1) Получение единого ограничительного перечня по Гуид")
    public void getEopGuidMaxPlus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/8eb9bf84-3507-11ee-918f-7824af8ab7211")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест (Max-1) Получение единого ограничительного перечня по Гуид")
    public void getEopGuidMaxMinus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/8eb9bf84-3507-11ee-918f-7824af8ab72")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого оганичительного перечня по Гуид, только пробелы")
    public void getEopGuidSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/      ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого оганичительного перечня  по Гуид, пробелы в начале и в конце")
    public void getEopGuidTwoSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/ 8eb9bf84-3507-11ee-918f-7824af8ab721 ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение еединого оганичительного перечня по Гуид, пробелы в середине строки")
    public void getEopGuidSpaciesIn() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/8eb9bf84-35  07-11ee-918f-7824af8ab721")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого оганичительного перечня по Гуид, комбинация латинница, спецсимволы, кириллица числа")
    public void getEopGuidCombination() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/8кb()ав84-3!07-11зз-918f-7824af8ab?*1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого оганичительного перечня по Гуид, Select")
    public void getEopGuidSelect() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/select")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого единого оганичительного перечня по Гуид, отрицательное число")
    public void getEopGuidNegativeNumber() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/-1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого оганичительного перечня по Гуид,Инъекция")
    public void getEopGuidInjection() {
        installSpec(requestSpecification(), responseSpecification404());
        given()
                .when()
                .get("eop/<script>alert( 'Hello world' );</script>")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единого оганичительного перечня по Гуид,Инъекция")
    public void getEopGuid1024letters() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("eop/Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .then().log().all();
    }


    ////////////////////getUnitsList///////////////////////////
    @Test
    @Description("Получение массива единиц измерения")
    public void getUnitsList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<UnitsPojo> response =
                given()
                        .when()
                        .queryParam("step", 13)
                        .get("units")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", UnitsPojo.class).stream().toList();
        Assertions.assertEquals(response.size(), 13);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getCode().length() <= 4)); // уточнить
        response.forEach(x -> Assert.assertTrue(x.getName().length() <= 25));
        response.forEach(x -> Assert.assertTrue(x.getNameFull().length() <= 100));
        response.forEach(x -> Assert.assertTrue(x.getInternationalReduction().length() <= 3));
        Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test
    @Description("Получение массива Единиц измерения из 5 объектов")
    public void getUnitsListStepEqual5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .get("units")
                .then().log().all()
                .body("size()", is(5));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень из 6 объектов")
    public void getUnitsListStepEqual6() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 6)
                .get("units")
                .then().log().all()
                .body("size()", is(6));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень из 199 объектов")
    public void getUnitsListStepEqual199() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 199)
                .get("units")
                .then().log().all()
                .body("size()", is(13));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех категорий Единый ограничительный перечень из 100 объектов")
    public void getUnitsListStepEqual100() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .get("units")
                .then().log().all()
                .body("size()", is(13));
        deleteSpec();
    }

    @Test
    @Description("Получение массива всех Единиц измерения, поле Step пустое")
    public void getUnitsListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("units")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(13)));
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step (Min-1)")
    public void getUnitsListStepMinMinus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 4)
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step (Max+1)")
    public void getUnitsListStepMaxPlus() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 201)
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step Max Integer")
    public void geUnitsListStepMaxInteger() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2147483647)
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step 3 пробела")
    public void getUnitsListStepSpaces() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "   ")
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step пробел перед числом и после")
    public void getUnitsListStepTwoSpacesAndDigit() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", " 200 ")
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step Дробное число")
    public void getUnitsListStepDoubleType() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 2.1)
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step пробел в середине числа")
    public void getUnitsListStepDigitAndSpace() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "2 1")
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step 1024 символа")
    public void getUnitsListStep1024() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step равен 0")
    public void getUnitsListStepZero() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", 0)
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step строка цифры+латинница")
    public void getUnitsListStepDigitLatin() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "123DWQ")
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step строка Спецсимволы")
    public void getUnitsListStepSpecialSymbol() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "!@#$%^&*(){}[]\"':;/<>\\|№\n")
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step строка select")
    public void getUnitsListStepSelect() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "select*From users")
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step отрицательное число")
    public void getUnitsListStepNegativeNumber() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("units", -100)
                .get("units")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение массива всех Единиц измерения, поле Step Инъекция")
    public void getUnitsListStepInjection() {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", "<script>alert( 'Hello world' );</script>")
                .get("units")
                .then().log().all();
        deleteSpec();
    }


    ////////////////////////////////getUnitsGuid//////////////////////////////////////////////////////////
    @Test
    @Description("Получение единиц измерения по Гуид")
    public void getUnitsGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("units/3107dadb-fd6a-11df-ad89-001cc40d947c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnitsGuid.json"));
        deleteSpec();
    }

    @Test
    @Description("Негативный тест Получение единиц измерения по Гуид, несуществующий Гуид 36 символов")
    public void getUnitsGuidNotExist() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/00bdd436-3da3-11ee-900f-5824af8ab723")
                .then().log().all();

    }

    @Test
    @Description("Негативный тест (Max+1) Получение единиц измерения по Гуид")
    public void getUnitsGuidMaxPlus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/8eb9bf84-3507-11ee-918f-7824af8ab7211")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест (Max-1) Получение единиц измерения по Гуид")
    public void getUnitsGuidMaxMinus() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/3107dadb-fd6a-11df-ad89-001cc40d947")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единиц измерения по Гуид, только пробелы")
    public void getUnitsGuidSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/      ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единиц измерения по Гуид, пробелы в начале и в конце")
    public void getUnitsGuidTwoSpacies() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/ 8eb9bf84-3507-11ee-918f-7824af8ab721 ")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единиц измерения по Гуид, пробелы в середине строки")
    public void getUnitsGuidSpaciesIn() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/8eb9bf84-35  07-11ee-918f-7824af8ab721")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единиц измерения по Гуид, комбинация латинница, спецсимволы, кириллица числа")
    public void getUnitsGuidCombination() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/8кb()ав84-3!07-11зз-918f-7824af8ab?*1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Пединиц измерения по Гуид, Select")
    public void getUnitsGuidSelect() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/select")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единиц измерения по Гуид, отрицательное число")
    public void getUnitsGuidNegativeNumber() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/-1")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единиц измерения по Гуид,Инъекция")
    public void getUnitsGuidInjection() {
        installSpec(requestSpecification(), responseSpecification404());
        given()
                .when()
                .get("units/<script>alert( 'Hello world' );</script>")
                .then().log().all();
    }

    @Test
    @Description("Негативный тест Получение единиц измерения по Гуид,Инъекция")
    public void getUnitsGuid1024letters() {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .get("units/Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta")
                .then().log().all();
    }
}


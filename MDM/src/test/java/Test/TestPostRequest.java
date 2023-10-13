package Test;

import Models.PojoPost;
import Models.Responsible;
import Specifications.GetPositivedataprovider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qameta.allure.Description;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static Specifications.Specifications.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class TestPostRequest {


    @Test
    @Description("using POJO")
    public void postNomenclatureChangeRequestPOJO() {
        PojoPost data = PojoPost.builder().build();
        Responsible rs = Responsible.builder().build();
        data.setComment("Комментарий к заявке");
        data.setResponsibl(rs);
        //String userArray[] = {"fio", "Иванов Иван Иванович","email", "ivanov@yandex.ru"};
        //data.setResponsibl(userArray);
        rs.setEmail("ivanov@yandex.ru");
        rs.setFio("Иванов Иван");
        given()
                .auth().basic("Administrator", "1234567809").and()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("nomenclature/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all()
                .statusCode(200)
                //.assertThat()
                .body("comment", notNullValue(), hasSize(lessThan(1024)))
                .body("responsible.fio", notNullValue(), hasSize(lessThan(100)))
                .body("responsible.email", notNullValue(), hasSize(lessThan(100)));
    }

    @Test
    @Description("using JsonFile ")
    public void postNomenclatureChangeJsonFile() throws FileNotFoundException {
        File file = new File("C:\\Users\\Sasha\\TEST\\Auto_test\\MDM\\src\\test\\resources\\postNomenclatureUsingJsonFil.json");
        FileReader fileReader = new FileReader(file);
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject data = new JSONObject(jsonTokener);

        given()
                .auth().basic("Administrator", "1234567809")
                //.and()
                //.header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("nomenclature/")
                .then().log().all()
                .statusCode(200)
                //.assertThat()
                .body("comment", notNullValue(), hasSize(lessThan(1024)))
                .body("responsible.fio", notNullValue(), hasSize(lessThan(100)))
                .body("responsible.email", notNullValue(), hasSize(lessThan(100)));
    }

    UUID uuid = UUID.randomUUID();
    @Test(dataProvider = "type", dataProviderClass = GetPositivedataprovider.class)
    @Description("Создаем заявку запрос существующей записи в МДМ и изменение   Тип = 0, 2")
    public void postNomenclatureChangeRequestMapRequired(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("drawingDenotation", "24379.1-2012");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 12.330);
        data.put("width", 1.450);
        data.put("length", 9.985);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1.500);
        data.put("weightUnit", uuid);
        given()
               // .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @Test//(dataProvider = "type", dataProviderClass = GetPositivedataprovider.class)
    @Description("Создаем заявку запрос существующей записи в МДМ   Тип = 1")
    public void postNomenclatureChangeRequestMapType1() {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 1); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("drawingDenotation", "24379.1-2012");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 2);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 12.330);
        data.put("width", 1.450);
        data.put("length", 9.985);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1.500);
        data.put("weightUnit", uuid);
        given()
                // .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @Test
    @Description("Создаем заявку на вывод из обращения  Тип = 3")
    public void postNomenclatureChangeRequestMapType3() {
        installSpec(requestSpecification(), responseSpecification());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 3); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("dateOutputArchive", "0001-01-01T00:00:00");
        given()
                // .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @Test(dataProvider = "type", dataProviderClass = GetPositivedataprovider.class)
    @Description("Создаем заявку Запрос и изменение с обязательними полями Тип = 0, 2")
    public void postNomenclatureChangeRequestMapType0_2_RequiredFields(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @Test
    @Description("Создаем заявку с обязательнами полями на вывод из обращения Тип = 3")
    public void postNomenclatureChangeRequestMapType3RequiredFields() {
        installSpec(requestSpecification(), responseSpecification());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 3); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        given()
                // .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }


    @Test(dataProvider = "type", dataProviderClass = GetPositivedataprovider.class)
    @Description("Создаем заявку минимальное значение в в атрибутах Тип = 0 2")
    public void postNomenclatureChangeRequestМinValue(int value) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", value); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "К");
        postNomenclature.put("guid", "8e7275eb-3049-11ee-b5ae-005056013b09");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "И");
        autor.put("email", "i");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Б");
        data.put("nameFull", "");
        data.put("drawingDenotation", "24379.1-2012");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 1);
        data.put("width", 1);
        data.put("length", 1);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1);
        data.put("weightUnit", uuid);
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .assertThat()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @Test//(dataProvider = "type", dataProviderClass = GetPositivedataprovider.class)
    @Description("Создаем заявку запрос существующей записи в МДМ с минимальным количеством Тип = 1")
    public void postNomenclatureChangeRequestMinMapType1() {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 1); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "К");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "И");
        autor.put("email", "");

        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Б");
        data.put("nameFull", "Б");
        data.put("drawingDenotation", "2");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 2);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 12.330);
        data.put("width", 1.450);
        data.put("length", 9.985);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1.500);
        data.put("weightUnit", uuid);
        given()
                // .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @Test
    @Description("Создаем заявку на вывод из обращения минимальное количество  Тип = 3")
    public void postNomenclatureChangeRequestMapMinType3() {
        installSpec(requestSpecification(), responseSpecification());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 3); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "К");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "И");
        autor.put("email", "i");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("dateOutputArchive", "0001-01-01T00:00:00");
        given()
                // .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }


        @Test(dataProvider = "type", dataProviderClass = GetPositivedataprovider.class)
        @Description("Создаем заявку c Максимальным значением в атрибутах")
        public void postNomenclatureChangeRequestMax(int value) {
            installSpec(requestSpecification(), responseSpecification());
            HashMap<String, Object> postNomenclature = new HashMap<>();
            HashMap<String, Object> autor = new HashMap<>();
            HashMap<String, Object> data = new HashMap<>();

            postNomenclature.put("type", value); // Параметризация значений с помощью Data Provider
            postNomenclature.put("comment", "цу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про л");
            postNomenclature.put("guid", "8e7275eb-3049-11ee-b5ae-005056013b0c");
            postNomenclature.put("autor", autor);
            postNomenclature.put("data", data);

            autor.put("fio", "йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()");
            autor.put("email", "йцу кен гшщ зхъ фва про лдж эя");

            data.put("guid", "F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4");
            data.put("guidBE", "H9168C5E-CEB2-4faa-B6BF-329BF39FA1E4");
            data.put("be", "8e7275eb-3049-11ee-b5ae-005056013b0c");
            data.put("unifiedClassifier", "8e7275eb-3049-11ee-b5ae-005056013b0c");
            data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
            data.put("fullName", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
            data.put("drawingDenotation", "24379.1-2012");
            data.put("ownershipSign", 0);
            data.put("seriality", 1);
            data.put("supplier", true);
            data.put("baseUnit", "8e7275eb-3049-11ee-b5ae-005056013b0c");
            data.put("height", "12,330");
            data.put("width", "1,450");
            data.put("length", "9,985");
            data.put("dimensionsUnit", "8e7275eb-3049-11ee-b5ae-005056013b0c");
            data.put("weight", "1,500");
            data.put("weightUnit", "8e7275eb-3049-11ee-b5ae-005056013b0c");
            given()
                    .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                    .body(postNomenclature)
                    .when()
                    .post("nomenclature/")
                    .then().log().all()
                    .assertThat()
                    .body("guid", hasLength((36)))
                    .body("result", equalTo("ok"));
        }










    @Test
    @Description("Создаем заявку значения max+1")
    public void postNomenclatureChangeRequestMaxPlus() throws IOException {
        ObjectNode jsonNodes = new ObjectMapper().readValue(new File("src/test/resources/postNomenclatureUsingJsonFil.json"), ObjectNode.class);
        ((ObjectNode)jsonNodes.get("guid")).put("guid", "8e7275eb-3049-11ee-b5ae-005056013b0c");

    }
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
                .post("nomenclature/")
                .then().log().all()
                .assertThat()
                // .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBasicServicesGuid.json"))
                .statusCode(200);
    }
}




//    PojoPost data = PojoPost.builder()
//            .comment("Комментарий к заявке")
//            .responsibl(Responsible.builder()
//                    .fio("Иванов Иван")
//                    .email("ivanov@yandex.ru").build()).build();
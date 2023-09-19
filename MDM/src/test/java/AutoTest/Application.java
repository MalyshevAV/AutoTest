package AutoTest;

import Models.PojoPost;
import Models.Responsible;
import Specifications.Specifications;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Application {

    @Test
    @Description("using POJO")
    public void postNomenclatureChangeRequestPOJO() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        PojoPost data = PojoPost.builder().build();
        Responsible rs = Responsible.builder().build();
        data.setComment("Комментарий к заявке");
        data.setResponsibl(rs);
        rs.setEmail("ivanov@yandex.ru");
        rs.setFio("Иванов Иван");
        given()
                //.auth().basic("Administrator", "1234567809").and()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("change-request/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all().assertThat()
                .body("comment", hasSize(36));
        deleteSpec();
    }

    @Test
    @Description("Проверка Максимальных значений ")
    public void postNomenclatureChangeRequestHashMap() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> responsible = new HashMap<>();
        data.put("comment", "fCIhBzK8j8mjuwYpwGbG18XD05DAb2rWJIPgrZ5W1s5GWWwV9BWb1sKyttxgXkMIL8ySzlJgVJIGgQB7OLeSP8hAZjT4IT99EHNFsiJqXzE8ubl46y7q7WHhTSyDR2GXCHu80iLEl9foF8ZIITTjuCEkCNuuNZkiEK8wJVZgvJkqJJ0jB9VgoXi3y5utBEksE6yKBkbKQL5djEx5dpfyCbm9ETHieW9zrfwNIfzGLJJJfXMNJBjeAafzDEqlFNO1oEriqCsbBSKy5tnd5EN1i2i1VlI9woDkbNhwdr4lh7DBWEDBPZBWnFfaeOT1HbMrIvAwO6I7vOfO7sKuG8p9bUWgxRXvvn3oc1YCMP3giLu00yZpcOZ1tPZNwd2440WNZMvVSNyl8kBk9PI0jxHiSzrFzjoRx6A7NBdvMJ76zFCE30zWBBxgF7JabwETNL5meoRJiDvxl3wKuU0ej2BCoqkm2HgKrJ62xbyFDJqy0OovwMTORo4yZLiSY5SvI7yhKNzRukvZppTQLnJtbCA6XOFD54oslBVgKdQBRwZPFqoXV1vK8AP4EsLIS2rZrltKTgy0BHCyQCV8yNIcUTsHbUEged8UXkMQyNyRA0s2xDqBMnLzy9ou3qsFxfsJvLExbA3XWtUmZKXko2CsPvRi1HBvuCvfA4HT0jkh9701Nq6iI7YZMa96VXtgvgjZCjiEFMGK6uaigB7K6hlfTeAekier9VVeQ9SEIhn0PThKeqOcPFKdQtMoYazEGT7GeotFoOlR4jGO7vqNzYWbRhdbAMzHj0cCXXI1eNptCxwimVWrcXDIh4htNjJiJHA8jlx3uQclBeHPHxIkUFwUqfUEiPQ2N6XMHGiCfXmpW9fdSNwizY8LnRpb4sqGgY7epGOrYbHYHyowMA1gnLKFu8Y4R8TXe0fGGebdLxZEHdLqumw6l3ZxhnIpFYxvoKw2mqQAFpdAmNuaMsPTvjTd5GLBfWdF1XWDAxz1UkHwyQacry7KgkaXhzCwM4novgDdnOfu");
        data.put("responsible", responsible);
        responsible.put("fio", "vvtKWkBBNapIkHTbRuPuEAXXqFqhSOFkUrSwKKSZgbISNNNEkCZZrANErsLsZNUgUQJeyKRrzoZFUGkvXCLXPNnhOsyCyHYhNutY");
        responsible.put("email", "fKYepAxKTwbyLPpKBSOTPAhOWCNWti");
        given()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("change-request/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all().assertThat()
                .body("comment", hasSize(36));
        deleteSpec();
    }

    ///////////////////////////////////Негативные тесты//////////////////////////
    @Test
    @Description("Отутствует обязательное поле")
    public void postNomenclatureChangeRequestDeleteRequareString() {
        installSpec(requestSpecification(), Specifications.responseSpecification500());
        PojoPost data = PojoPost.builder().build();
        Responsible rs = Responsible.builder().build();
        data.setComment("Комментарий к заявке");
        data.setResponsibl(rs);
        rs.setEmail("ivanov@yandex.ru");
        given()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("change-request/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Добавление дополнительного поля")
    public void postNomenclatureChangeRequestExtraString() {
        installSpec(requestSpecification(), Specifications.responseSpecification500());
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> responsible = new HashMap<>();
        data.put("comment", "Комментарий к заявке");
        data.put("name", "Иванов Иван");
        data.put("responsible", responsible);
        responsible.put("fio", "Иванов Иван");
        responsible.put("email", "ivanov@yandex.ru");
        given()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("change-request/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Пустые поля в объекте")
    public void postNomenclatureChangeRequestEmptyField() {
        installSpec(requestSpecification(), Specifications.responseSpecification500());
        PojoPost data = PojoPost.builder().build();
        Responsible rs = Responsible.builder().build();
        data.setComment("");
        data.setResponsibl(rs);
        rs.setEmail("");
        rs.setFio("");
        given()
                //.auth().basic("Administrator", "1234567809").and()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("change-request/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all();
        deleteSpec();
    }

    @Test
    @Description("Неверная структура объекта")
    public void postNomenclatureChangeRequest() {
        installSpec(requestSpecification(), Specifications.responseSpecification500());
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("fio", "Иванов Иван Иванович");
        jsonAsMap.put("email", "ivanov@yandex.ru");
        jsonAsMap.put("comment", "ivanov@yandex.ru");

        given()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(jsonAsMap)
                .when()
                .post("change-request/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all();
    }
}
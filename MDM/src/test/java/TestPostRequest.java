import Models.PojoPost;
import Models.Responsible;
import io.qameta.allure.Description;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestPostRequest {

    @Test
    @Description("Создаем заявку на добавление, изменение, удаление номенклатуры")
    public void postNomenclatureChangeRequest() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("comment", "ivanov@yandex.ru");

        String userArray[] = {"fio", "Иванов Иван Иванович","email", "ivanov@yandex.ru"};
        data.put("responsible", userArray);

        given()
                .auth().basic("Administrator", "1234567809").and()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data)
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
    @Description("using org.json")
    public void postNomenclatureChangeRequestJsonLibrary() {
        JSONObject data = new JSONObject();
        data.put("comment", "ivanov@yandex.ru");

        String userArray[] = {"fio", "Иванов Иван Иванович","email", "ivanov@yandex.ru"};
        data.put("responsible", userArray);

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
}
//    PojoPost data = PojoPost.builder()
//            .comment("Комментарий к заявке")
//            .responsibl(Responsible.builder()
//                    .fio("Иванов Иван")
//                    .email("ivanov@yandex.ru").build()).build();
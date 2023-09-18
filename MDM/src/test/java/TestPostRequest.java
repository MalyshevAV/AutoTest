import Models.PojoPost;
import Models.Responsible;
import Specifications.GetPositivedataprovider;
import io.qameta.allure.Description;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static Specifications.Specifications.*;
import static org.hamcrest.Matchers.*;
import java.nio.file.Path;

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
                .post("nomenclature/")
                .then().log().all()
                .statusCode(200)
                .assertThat()
                .body("comment", notNullValue(), hasSize((36)));
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
                .post("nomenclature/")
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


    @Test(dataProvider = "type", dataProviderClass = GetPositivedataprovider.class)
    @Description("Создаем заявку на добавление, изменение, удаление номенклатуры")
    public void postNomenclatureChangeRequestMap(int value) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", value); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "Комментарий");
        postNomenclature.put("guid", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put( "fio", "Иванов Иван Иванович");
        autor.put( "email", "ivanov@yandex.ru");

        data.put("guid", "F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4");
                data.put("guidBE", "H9168C5E-CEB2-4faa-B6BF-329BF39FA1E4");
                data.put("be","МСФОКодБЕ");
                data.put("codeUC", "01.02.01");
                data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
                data.put( "fullName", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
                data.put("drawingDenotation", "24379.1-2012");
                data.put("ownershipSign", 0);
                data.put("seriality", 1);
                data.put("supplier", true);
                data.put("baseUnit", 796);
                data.put("height", "12,330");
                data.put("width", "1,450");
                data.put("length", "9,985");
                data.put("dimensionsUnit", 796);
                data.put ("weight", "1,500");
                data.put ("weightUnit", 796);

        given()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all();
//                .assertThat()
//                .body(notNullValue(), hasSize((36)));
    }



}
//    PojoPost data = PojoPost.builder()
//            .comment("Комментарий к заявке")
//            .responsibl(Responsible.builder()
//                    .fio("Иванов Иван")
//                    .email("ivanov@yandex.ru").build()).build();
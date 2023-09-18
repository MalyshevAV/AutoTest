package AutoTest;

import Models.PojoPost;
import Models.Responsible;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Application {

    @Test
    @Description("using POJO")
    public void postNomenclatureChangeRequestPOJO() {
        PojoPost data = PojoPost.builder().build();
        Responsible rs = Responsible.builder().build();
        data.setComment("Комментарий к заявке");
        data.setResponsibl(rs);
        rs.setEmail("ivanov@yandex.ru");
        rs.setFio("Иванов Иван");
        given()
                .auth().basic("Administrator", "1234567809").and()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("change-request/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all().assertThat()
                .body("comment", hasSize(36));
    }



}
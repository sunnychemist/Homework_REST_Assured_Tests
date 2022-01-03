import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.Person;
import pojo.User;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ReqresTests {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void getUser(){
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(String.valueOf(response.jsonPath().getString("")));
        System.out.println(String.valueOf(response.jsonPath().getString("data")));
        assertThat(response.statusCode()).isEqualTo(200);
//        List<Response> responses = response.jsonPath().getList("data");
        assertThat(response.jsonPath().getString("data.id")).isEqualTo("2");
//        assertThat(response.jsonPath().getString("id")).isEqualTo("2");
        assertThat(response.jsonPath().getString("data.email")).isEqualTo("janet.weaver@reqres.in");
        assertThat(response.jsonPath().getString("data.first_name")).isEqualTo("Janet");
        assertThat(response.jsonPath().getString("data.last_name")).isEqualTo("Weaver");
        assertThat(response.jsonPath().getString("data.avatar")).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
        assertThat(response.jsonPath().getString( "support.url")).isEqualTo("https://reqres.in/#support-heading");
        assertThat(response.jsonPath().getString( "support.text")).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
    }
    @Test
    void createUser() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(objectMapper.writeValueAsString(new Person("morpheus", "leader")))
                .log().all()
                .post("/api/users")
                .then()
                .extract().response();

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.jsonPath().getString("name")).isEqualTo("morpheus");
        assertThat(response.jsonPath().getString("job")).isEqualTo("leader");
    }

    @Test
    void getUsersTest(){
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get("/api/users?pa" +
                        "ge=2")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.jsonPath().getString("data"));
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("page")).isEqualTo("2");
        assertThat(response.jsonPath().getString("per_page")).isEqualTo("6");
        assertThat(response.jsonPath().getString("total")).isEqualTo("12");
        assertThat(response.jsonPath().getString("total")).isEqualTo("12");

        List<User> users = response.jsonPath().getList("data", User.class);
        User firstUser = users.get(0);

        assertThat(firstUser.getId()).isEqualTo("7");
        assertThat(firstUser.getEmail()).isEqualTo("michael.lawson@reqres.in");
        assertThat(firstUser.getFirstName()).isEqualTo("Michael");
        assertThat(firstUser.getLastName()).isEqualTo("Lawson");
        assertThat(firstUser.getAvatar()).isEqualTo("https://reqres.in/img/faces/7-image.jpg");

    }

    @Test
    void deleteTest(){
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .extract().response();
        assertThat(response.statusCode()).isEqualTo(204);
    }

    @Test
    void putTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(objectMapper.writeValueAsString(new Person("morpheus", "zion resident")))
                .log().all()
                .put("/api/users/2")
                .then()
                .extract().response();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("name")).isEqualTo("morpheus");
        assertThat(response.jsonPath().getString("job")).isEqualTo("zion resident");
    }

    @Test
    void getNotFound() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get("/api/unknown/23")
                .then()
                .statusCode(404)
                .extract().response();
        assertThat(response.statusCode()).isEqualTo(404);
    }

}



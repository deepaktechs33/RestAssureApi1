package Day1;
import org.hamcrest.Matcher;


import org.testng.annotations.Test;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;


public class HttpMethodDemo {

	String userId;   // store ID like STU_12345

    @Test(priority = 1)
    public void getUsers() {

        given()
        .when()
                .get("http://localhost:3000/students")
        .then()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json"))
                .time(lessThan(20000L))
                .body(containsString("name"))
                .body(containsString("courses"))
                .log().all();
    }


    @Test(priority = 2)
   public void createUser() {

        // Create custom ID (String + number)
        String customId = "STU_" + System.currentTimeMillis();

        HashMap<String, Object> data = new HashMap<>();
        data.put("id", customId);   // important: json-server supports custom ID
        data.put("name", "hjjnkj");
        data.put("location", "bckhdbkh");

        userId =
            given()
                    .contentType("application/json")
                    .body(data)
            .when()
                    .post("http://localhost:3000/students")
            .then()
                    .statusCode(201)
                    .header("Content-Type", equalTo("application/json"))
                    .time(lessThan(2000L))
                    .body("id", equalTo(customId))
                    .body("name", equalTo("hjjnkj"))
                    .body("location", equalTo("bckhdbkh"))
                    .log().all()
                    .extract().jsonPath().getString("id");  // store String ID
    }


   @Test(priority = 3, dependsOnMethods = {"createUser"})
    public void updateUser() {

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "updatedName");
        data.put("location", "updatedLocation");

        given()
                .contentType("application/json")
                .body(data)
        .when()
                .put("http://localhost:3000/students/" + userId)
        .then()
                .statusCode(200)    // json-server returns 200 for update
                .body("name", equalTo("updatedName"))
                .body("location", equalTo("updatedLocation"))
                .log().all();
    }


   @Test(priority = 4, dependsOnMethods = {"createUser", "updateUser"})
    public void deleteUser() {

        given()
        .when()
                .delete("http://localhost:3000/students/" + userId)
        .then()
                .statusCode(200)   // json-server returns 200 (NOT 204)
                .log().all();
    }
}

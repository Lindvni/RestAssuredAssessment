package requestBuilder;

import io.restassured.response.Response;

import static commons.Paths.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AdminRequestBuilder {

    public static String adminToken;

    public static Response approveUser(){

        String apiPath = "/APIDEV/admin/users/{userID}/approve";

        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .pathParam("userID", UserRequestBuilder.registeredUserId)
                .header("Authorization","Bearer "+adminToken)
                .header("Content-Type","application/json")
                .when()
                .put()
                .then()
                .extract().response();
    }
    public static Response adminLogin(String email, String password){

        Response response =UserRequestBuilder.loginUser(email.trim(),password.trim());
        adminToken = response.jsonPath().getString("data.token");
        return response;
    }

}

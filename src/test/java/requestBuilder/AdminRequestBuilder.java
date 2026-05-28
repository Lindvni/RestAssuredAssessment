package requestBuilder;

import io.restassured.response.Response;

import static commons.Paths.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AdminRequestBuilder {

    public static String adminToken;

    public static Response approveUser(){
        String apiPath = "/admin/users//{userID}/approve";

        return given()
                .baseUri(BASE_URL)
                //.pathParams("userID", UserRequestBuilder.registeredUserId)
                .header("Authorization","Bearer "+adminToken)
                .when()
                .put()
                .then()
                .extract().response();
    }
    public static Response adminLogin(String email, String password){

        Response response =UserRequestBuilder.loginUser(email,password);
        adminToken = response.jsonPath().getString("data.token");
        return response;
    }

}

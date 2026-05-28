package Utils;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class RegistrationTests {
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static String confirmPassword;
    static String groupId;

    static Faker faker = new Faker();

    public static void generateUserData() {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().emailAddress();
        password = "@Pass12345";
        confirmPassword = "@Pass12345";
        groupId = "5328c91e-fc40-11f0-8e00-5000e6331276";
    }

    @Test(priority = 1)
    public void userRegistrationTest() {
        generateUserData();
        Response response = requestBuilder.UserRequestBuilder.registerUser(firstName, lastName, email, password, confirmPassword, groupId);
        response.then().log().all();
    }
    @Test(priority = 2)
    public void adminLoginTest() {
        Response response = requestBuilder.AdminRequestBuilder.adminLogin("admin@gmail.com", "@12345678");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test( priority = 3)
    public void approveUserTest() {
        requestBuilder.AdminRequestBuilder.approveUser()

                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success",equalTo(true));

    }
    @Test   (priority = 4)
    public void userLoginTest() {
        requestBuilder.UserRequestBuilder.loginUser(email, password)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));

    }
}

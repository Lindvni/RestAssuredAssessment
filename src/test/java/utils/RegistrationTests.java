package Utils;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import requestBuilder.UserRequestBuilder;

import static org.hamcrest.Matchers.equalTo;
import static requestBuilder.createTestimonialBuilder.testimonialId;

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
    @Test(priority = 5, dependsOnMethods = {"adminLoginTest"})
    public void createTestimonialTest() {
        requestBuilder.createTestimonialBuilder.createTestimonial("Cucumber 19 May", "Behavior Driven", 5, true)
                .then().log().all();

    }
    @Test(priority = 6, dependsOnMethods = {"createTestimonialTest"})
    public void updateTestimonialTest() {
        requestBuilder.createTestimonialBuilder.updateTestimonial(
                        "Updated Title2", "Updated testimonial content", 5)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }
    @Test(priority = 7, dependsOnMethods = {"createTestimonialTest"})
    public void deleteTestimonialTest() {
        requestBuilder.createTestimonialBuilder.deleteTestimonial()
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }
    @Test(priority = 8)
    public void getCoursesTest() {
        requestBuilder.createTestimonialBuilder.getCourses("automation", "beginner")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}

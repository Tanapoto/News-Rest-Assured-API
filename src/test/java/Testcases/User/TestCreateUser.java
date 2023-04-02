package Testcases.User;

import PojoData.User.CreateUserPojo;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestCreateUser extends BaseSetup {

    Faker faker = new Faker();
    public static HashMap<String,String> userData = new HashMap<String, String>();

    @DataProvider(name = "createUserData")
    public Iterator<Object[]> getCreateUser() {
        final List<Object[]> getTestData = new ArrayList<>();
        getTestData.add(new Object[]{faker.name().username(), faker.name().firstName(),  faker.name().lastName()
                , faker.internet().emailAddress(), faker.internet().password(), faker.phoneNumber().cellPhone()});
        return getTestData.iterator();
    }

    @Test(priority = 0, dataProvider = "createUserData")
    public void testCreateUser(String username, String firstName, String lastName, String email, String password, String phone) {
        CreateUserPojo userPojo = new CreateUserPojo(username, firstName, lastName, email, password, phone);

        //Create User
        given().spec(request())
                .body(userPojo)
                .when()
                .post("/user")
                .then()
                .assertThat()
                .spec(response200());

        //Get Response Of created User
        String response = given().spec(request())
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CreateUserPojo createUserPojo = gson.fromJson(response, CreateUserPojo.class);

        userData.put("username",createUserPojo.getUsername());
        userData.put("password",createUserPojo.getPassword());

        Assert.assertEquals(createUserPojo.getUsername(), username);
        Assert.assertEquals(createUserPojo.getFirstName(), firstName);
        Assert.assertEquals(createUserPojo.getLastName(), lastName);
        Assert.assertEquals(createUserPojo.getEmail(), email);
        Assert.assertEquals(createUserPojo.getPassword(), password);
        Assert.assertEquals(createUserPojo.getPhone(), phone);


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Create User Successfully");
        }
    }
}























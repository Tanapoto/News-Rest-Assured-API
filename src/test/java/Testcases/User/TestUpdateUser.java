package Testcases.User;

import PojoData.User.UpdateUserPojo;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestUpdateUser extends BaseSetup {

    Faker faker = new Faker();

    @DataProvider(name = "updateUserData")
    public Iterator<Object[]> getCreateUser() {
        final List<Object[]> getTestData = new ArrayList<>();
        getTestData.add(new Object[]{faker.name().firstName(),  faker.name().lastName()
                , faker.internet().emailAddress(), faker.internet().password(), faker.phoneNumber().cellPhone()});
        return getTestData.iterator();
    }

    @Test(priority = 0, dataProvider = "updateUserData")
    public void testUpdateUser(String firstName, String lastName, String email, String password, String phone) {
        UpdateUserPojo userPojo = new UpdateUserPojo(firstName, lastName, email, password, phone);

        //Update User
     given().spec(request())
                .pathParam("username", TestCreateUser.userData.get("username"))
                .body(userPojo)
                .when()
                .put("/user/{username}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        //Get Response Of updated User
        String response = given().spec(request())
                .pathParam("username", TestCreateUser.userData.get("username"))
                .when()
                .get("/user/{username}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        UpdateUserPojo updateUserPojo = gson.fromJson(response, UpdateUserPojo.class);


        Assert.assertEquals(updateUserPojo.getUsername(), TestCreateUser.userData.get("username"));
        Assert.assertEquals(updateUserPojo.getFirstName(), firstName);
        Assert.assertEquals(updateUserPojo.getLastName(), lastName);
        Assert.assertEquals(updateUserPojo.getEmail(), email);
        Assert.assertEquals(updateUserPojo.getPassword(), password);
        Assert.assertEquals(updateUserPojo.getPhone(), phone);

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Update User Successfully");
        }
    }
}























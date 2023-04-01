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

public class TestDeleteUser extends BaseSetup {

    @Test(priority = 0)
    public void testDeleteUser() {

        given().spec(request())
                .pathParam("username", TestCreateUser.userData.get("username"))
                .when()
                .get("/user/{username}")
                .then()
                .assertThat()
                .spec(response200());


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Delete User Successfully");
        }
    }
}























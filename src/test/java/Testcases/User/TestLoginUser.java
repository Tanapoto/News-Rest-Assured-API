package Testcases.User;

import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import static io.restassured.RestAssured.given;

public class TestLoginUser extends BaseSetup {

    @Test(priority = 0)
    public void testLoginUser() {

        String response = given().spec(request())
                .pathParam("username", TestCreateUser.userData.get("username"))
                .pathParam("password", TestCreateUser.userData.get("password"))
                .when()
                .get("/user/login?username={username}&password={password}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Login User Successfully");
        }
    }
}

package Testcases.User;

import PojoData.User.LoginUserPojo;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TestLoginUser extends BaseSetup {

    public static HashMap<String, String> userKey = new HashMap<>();

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

        Gson gson = new Gson();
        LoginUserPojo loginUserPojo = gson.fromJson(response,LoginUserPojo.class);

        userKey.put("api_Key",loginUserPojo.getMessage().replace("logged in user session:",""));
        System.out.println("api_Key: " + userKey.get("api_Key"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Login User Successfully");
        }
    }
}

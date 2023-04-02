package Testcases.Pet;

import PojoData.Pet.PetPojo;
import PojoData.Pet.category;
import PojoData.Pet.tags;
import Testcases.User.TestLoginUser;
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

public class TestDeletePet extends BaseSetup {


    @Test(priority = 0)
    public void testDeletePet() {

        given().spec(request())
                .header("api_key", TestLoginUser.userKey.get("api_Key"))
                .pathParam("petId",TestCreatePet.petData.get("petId"))
                .when()
                .delete("/pet/{petId}")
                .then()
                .assertThat()
                .spec(response200());

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Delete Pet Successfully");
        }
    }
}























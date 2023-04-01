package Testcases.Pet;

import common.BaseSetup;
import helpers.Helpers;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;


import static io.restassured.RestAssured.given;

public class TestUploadImage extends BaseSetup {

    @Test(priority = 0)
    public void testCreatePet() {

                given().spec(requestWithFormData())
                        .multiPart("file", Helpers.getCurrentDir()+"src\\test\\resources\\config\\ImageTest.jpg")
                        .pathParam("petId",TestCreatePet.petId)
                        .when()
                        .post("/pet/{petId}/uploadImage")
                        .then()
                        .assertThat()
                        .spec(response200());


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Upload Image Pet Successfully");
        }
    }
}

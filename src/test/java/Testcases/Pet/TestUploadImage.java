package Testcases.Pet;

import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;


import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class TestUploadImage extends BaseSetup {

    @Test(priority = 0)
    public void testUploadImage() {

                given().spec(requestWithFormData())
                        .multiPart("file", Paths.get("").toAbsolutePath().toString()+"src\\test\\resources\\config\\ImageTest.jpg")
                        .pathParam("petId",TestCreatePet.petData.get("petId"))
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

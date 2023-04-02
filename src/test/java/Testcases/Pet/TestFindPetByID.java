package Testcases.Pet;

import PojoData.Pet.PetPojo;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.Assert;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import static io.restassured.RestAssured.*;

public class TestFindPetByID extends BaseSetup {
    @Test
    public void testFindPetById(){
        String response = given().spec(request())
                        .pathParam("petId", TestCreatePet.petData.get("petId"))
                        .when()
                        .get("pet/{petId}")
                        .then().spec(response200())
                        .extract().asString();
        
        Gson gson = new Gson();
        PetPojo petPojo = gson.fromJson(response,PetPojo.class);

        Assert.assertEquals(petPojo.getId(),TestCreatePet.petData.get("petId"));
        Assert.assertEquals(petPojo.getCategory().getId(),TestCreatePet.petData.get("categoryId"));
        Assert.assertEquals(petPojo.getCategory().getName(),TestCreatePet.petData.get("categoryName"));
        Assert.assertEquals(petPojo.getName(),TestCreatePet.petData.get("petName"));
        Assert.assertEquals(petPojo.getTags().get(0).getId(),TestCreatePet.petData.get("tagId"));
        Assert.assertEquals(petPojo.getTags().get(0).getName(),TestCreatePet.petData.get("tagName"));
        Assert.assertEquals(petPojo.getStatus(),TestCreatePet.petData.get("petStatus"));


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Create Pet By ID Successfully");
        }
        
    }
}











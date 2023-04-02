package Testcases.Pet;

import PojoData.Pet.SearchPetPojo;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.Assert;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestFindPetByStatus extends BaseSetup {
    @Test
    public void testFindPetByStatus(){

        String response = given().spec(request())
                        .pathParam("petStatus", TestCreatePet.petData.get("petStatus"))
                        .when()
                        .get("pet/findByStatus?status={petStatus}")
                        .then().spec(response200())
                        .extract().asString();
        
        Gson gson = new Gson();
        List<SearchPetPojo> petPojo = Arrays.asList(gson.fromJson(response,SearchPetPojo[].class));



        Assert.assertEquals(petPojo.get(0).getId(),TestCreatePet.petData.get("petId"));
        Assert.assertEquals(petPojo.get(0).getCategory().getId(),TestCreatePet.petData.get("categoryId"));
        Assert.assertEquals(petPojo.get(0).getCategory().getName(),TestCreatePet.petData.get("categoryName"));
        Assert.assertEquals(petPojo.get(0).getName(),TestCreatePet.petData.get("petName"));
        Assert.assertEquals(petPojo.get(0).getTags().get(0).getId(),TestCreatePet.petData.get("tagId"));
        Assert.assertEquals(petPojo.get(0).getTags().get(0).getName(),TestCreatePet.petData.get("tagName"));
        Assert.assertEquals(petPojo.get(0).getStatus(),TestCreatePet.petData.get("petStatus"));


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Search Pet By Status Successfully");
        }
        
    }
}











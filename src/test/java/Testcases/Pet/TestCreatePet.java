package Testcases.Pet;

import PojoData.Pet.PetPojo;
import PojoData.Pet.category;
import PojoData.Pet.tags;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.*;

import static io.restassured.RestAssured.given;

public class TestCreatePet extends BaseSetup {

    static Faker faker = new Faker();
    public static HashMap<String,Object> petData = new HashMap<String, Object>();

    @DataProvider(name = "createPetData")
    public Iterator<Object[]> getCreatePet() {
        final List<Object[]> getTestData = new ArrayList<>();
        getTestData.add(new Object[]{faker.number().numberBetween(1,100),faker.number().numberBetween(1,100),faker.name().lastName(),
                faker.animal().name(),faker.number().numberBetween(1,100),faker.name().firstName(), faker.name().firstName()});
        return getTestData.iterator();
    }

    @Test(priority = 0, dataProvider = "createPetData")
    public void testCreatePet(int petId, int categoryId, String categoryName, String petName,int tagId, String tagName,String petStatus) {

        category category1 = new category(categoryId, categoryName);
        List<tags> tags = new ArrayList<tags>();
        tags.add(new tags(tagId, tagName));

        PetPojo petPojo = new PetPojo(petId,category1,petName,tags,petStatus);

        //Create Pet
        String response =
                given().spec(request())
                .body(petPojo)
                .when()
                .post("/pet")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();
        Gson gson = new Gson();
        PetPojo createPetPojo = gson.fromJson(response, PetPojo.class);

        petData.put("petId", createPetPojo.getId());
        petData.put("categoryId", createPetPojo.getCategory().getId());
        petData.put("categoryName", createPetPojo.getCategory().getName());
        petData.put("tagId", createPetPojo.getTags().get(0).getId());
        petData.put("tagName", createPetPojo.getTags().get(0).getName());
        petData.put("petName", createPetPojo.getName());
        petData.put("petStatus", createPetPojo.getStatus());

        Assert.assertEquals(createPetPojo.getId(),petId);
        Assert.assertEquals(createPetPojo.getCategory().getId(),categoryId);
        Assert.assertEquals(createPetPojo.getCategory().getName(),categoryName);
        Assert.assertEquals(createPetPojo.getName(),petName);
        Assert.assertEquals(createPetPojo.getTags().get(0).getId(),tagId);
        Assert.assertEquals(createPetPojo.getTags().get(0).getName(),tagName);
        Assert.assertEquals(createPetPojo.getStatus(),petStatus);


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Create Pet Successfully");
        }
    }
}























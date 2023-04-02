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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestUpdatePet extends BaseSetup {

    Faker faker = new Faker();
    public static HashMap<String,String> userData = new HashMap<String, String>();

    @DataProvider(name = "createPetData")
    public Iterator<Object[]> getCreatePet() {
        final List<Object[]> getTestData = new ArrayList<>();
        getTestData.add(new Object[]{faker.name().lastName(), faker.animal().name(),
                faker.name().firstName(), faker.name().firstName()});
        return getTestData.iterator();
    }

    @Test(priority = 0, dataProvider = "createPetData")
    public void testUpdatePet(String categoryName, String petName, String tagName,String petStatus) {

        category category2 = new category(categoryName);
        List<tags> tags = new ArrayList<tags>();
        tags.add(new tags(tagName));

        PetPojo updatePetPojo = new PetPojo((int)TestCreatePet.petData.get("petId"),category2,petName,tags,petStatus);

        //Update Pet
        String response =
                given().spec(request())
                .body(updatePetPojo)
                .when()
                .put("/pet")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();
        Gson gson = new Gson();
        PetPojo createPetPojo = gson.fromJson(response, PetPojo.class);

        Assert.assertEquals(createPetPojo.getId(),TestCreatePet.petData.get("petId"));
        Assert.assertEquals(createPetPojo.getCategory().getName(),categoryName);
        Assert.assertEquals(createPetPojo.getName(),petName);
        Assert.assertEquals(createPetPojo.getTags().get(0).getName(),tagName);
        Assert.assertEquals(createPetPojo.getStatus(),petStatus);


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Update Pet Successfully");
        }
    }
}























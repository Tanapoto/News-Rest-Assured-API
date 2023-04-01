package Testcases.Pet;

import PojoData.Pet.CreatePetPojo;
import PojoData.Pet.category;
import PojoData.Pet.tags;
import PojoData.User.CreateUserPojo;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import common.BaseSetup;
import io.opencensus.tags.Tag;
import io.opencensus.tags.Tags;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.*;

import static io.restassured.RestAssured.given;

public class TestCreatePet extends BaseSetup {

    public static int petId;
    Faker faker = new Faker();
    public static HashMap<String,String> userData = new HashMap<String, String>();

    @DataProvider(name = "createPetData")
    public Iterator<Object[]> getCreatePet() {
        final List<Object[]> getTestData = new ArrayList<>();
        getTestData.add(new Object[]{faker.number().numberBetween(1,100),faker.number().randomDigit(),faker.name().lastName(),
                faker.animal().name(),  faker.number().randomDigit(),faker.name().firstName(), faker.name().firstName()});
        return getTestData.iterator();
    }

    @Test(priority = 0, dataProvider = "createPetData")
    public void testCreatePet(int petId, int categoryId, String categoryName, String petName,int tagId, String tagName,String petStatus) {



        category category1 = new category(categoryId, categoryName);
        List<tags> tags = new ArrayList<tags>();
        tags.add(new tags(tagId, tagName));

        CreatePetPojo petPojo = new CreatePetPojo(petId,category1,petName,tags,petStatus);

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
        CreatePetPojo createPetPojo = gson.fromJson(response, CreatePetPojo.class);

        petId = createPetPojo.getId();

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























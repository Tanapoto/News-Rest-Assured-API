package Testcases.Store;

import PojoData.Pet.PetPojo;
import PojoData.Pet.category;
import PojoData.Pet.tags;
import PojoData.Store.OrderPojo;
import Testcases.Pet.TestCreatePet;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;
import utilities.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class TestOrder extends BaseSetup {

    static Faker faker = new Faker();
    public static HashMap<String,Object> orderData = new HashMap<String, Object>();

    @DataProvider(name = "orderData")
    public Iterator<Object[]> getOrder() {
        final List<Object[]> getTestData = new ArrayList<>();
        getTestData.add(new Object[]{faker.random().nextInt(1,100),faker.number().numberBetween(1,100),
                DateUtils.getCurrentDate(faker.date().future(30, TimeUnit.DAYS)),faker.name().firstName(), faker.random().nextBoolean()});
        return getTestData.iterator();
    }

    @Test(priority = 0, dataProvider = "orderData")
    public void testOrder(int id, int quantity, String shipDate, String status, boolean complete) {


        OrderPojo orderPojo = new OrderPojo(id, (int) TestCreatePet.petData.get("petId"), quantity, shipDate, status, complete);


        String response =
                given().spec(request())
                .body(orderPojo)
                .when()
                .post("/store/order")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();
        Gson gson = new Gson();
        OrderPojo order = gson.fromJson(response, OrderPojo.class);

        orderData.put("orderId", order.getId());
        orderData.put("orderQuantity", order.getQuantity());
        orderData.put("orderShipDate", order.getShipDate());
        orderData.put("orderStatus", order.getStatus());
        orderData.put("orderComplete", order.isComplete());

        Assert.assertEquals(order.getId(),id);
        Assert.assertEquals(order.getPetId(), (int) TestCreatePet.petData.get("petId"));
        Assert.assertEquals(order.getQuantity(),quantity);
        Assert.assertTrue(order.getShipDate().contains(shipDate));
        Assert.assertEquals(order.getStatus(),status);
        Assert.assertEquals(order.isComplete(),complete);


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Order Pet Successfully");
        }
    }
}























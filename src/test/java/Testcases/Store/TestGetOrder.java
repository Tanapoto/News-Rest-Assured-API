package Testcases.Store;

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

public class TestGetOrder extends BaseSetup {

    @Test(priority = 0)
    public void testGetOrder() {


        String response =
                given().spec(request())
                .pathParam("orderId", TestOrder.orderData.get("orderId"))
                .when()
                .get("/store/order/{orderId}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();
        Gson gson = new Gson();
        OrderPojo order = gson.fromJson(response, OrderPojo.class);

        Assert.assertEquals(order.getId(),TestOrder.orderData.get("orderId"));
        Assert.assertEquals(order.getPetId(),TestCreatePet.petData.get("petId"));
        Assert.assertEquals(order.getQuantity(),TestOrder.orderData.get("orderQuantity"));
        Assert.assertEquals(order.getShipDate(),TestOrder.orderData.get("orderShipDate"));
        Assert.assertEquals(order.getStatus(),TestOrder.orderData.get("orderStatus"));
        Assert.assertEquals(order.isComplete(),TestOrder.orderData.get("orderComplete"));


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get Order Successfully");
        }
    }
}























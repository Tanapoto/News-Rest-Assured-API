package Testcases.Store;

import PojoData.Store.OrderPojo;
import Testcases.Pet.TestCreatePet;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.Assert;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TestDeleteOrder extends BaseSetup {

    @Test(priority = 0)
    public void testDeleteOrder() {

         given().spec(request())
                .pathParam("orderId", TestOrder.orderData.get("orderId"))
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .assertThat()
                .spec(response200());

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Delete Order Pet Successfully");
        }
    }
}























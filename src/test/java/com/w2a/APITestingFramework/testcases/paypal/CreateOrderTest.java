package com.w2a.APITestingFramework.testcases.paypal;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.APIs.paypal.OrderAPI;
import com.w2a.APITestingFramework.setUp.BaseTest;

import io.restassured.response.Response;

public class CreateOrderTest extends BaseTest{
	
	
	@Test
	public void createOrder() {
		
		String accesstoken = OrderAPI.getAccessToken();
		Response response = 	OrderAPI.createOrder(accesstoken);
		//response.prettyPrint();
		
		Assert.assertEquals(response.jsonPath().getString("status"), "CREATED");
		
	}

}

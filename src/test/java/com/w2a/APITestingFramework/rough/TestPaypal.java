package com.w2a.APITestingFramework.rough;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.pojo.Orders;
import com.w2a.APITestingFramework.pojo.PurchaseUnits;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;

public class TestPaypal {

	//https://api.sandbox.paypal.com/v1/oauth2/token
	static String access_token;
	static String client_key="Aafrf-nQ-kr6xW2XoVwoPL6DwWkFw2Og5lFJP47RkTh53nXLBW_kEss2XcAwuyCL6ASmAJ3oEf_RWoAM";
	static String secret="EF933duGm91qYVklp6lHYu_7QZosqA4IPGt24PeoZgA7AY0VK9LipWJExIz4YRxxyYIvm6ndsdJ5RGl5";
	static String orderId;
	
	@Test(priority = 1)
	public void getAuthKey() {
		RestAssured.baseURI = "https://api.sandbox.paypal.com";
		
		
	Response response = 	given()
		.param("grant_type","client_credentials")
		.auth().preemptive()
		.basic(client_key, secret)
		.post("/v1/oauth2/token");
	
	//response.prettyPrint();
	access_token = response.jsonPath().get("access_token");
	System.out.println(access_token);
	}
	
	@Test(priority = 2,dependsOnMethods = "getAuthKey")
	public void createOrder() {
	/*	String jsonBody ="{\r\n"
				+ "    \"intent\": \"CAPTURE\",\r\n"
				+ "    \"purchase_units\": [\r\n"
				+ "        {\r\n"
				+ "            \"amount\": {\r\n"
				+ "                \"currency_code\": \"USD\",\r\n"
				+ "                \"value\": \"100.00\"\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";*/
		
		ArrayList<PurchaseUnits> purchase_unitsListArray = new ArrayList<PurchaseUnits>();
		PurchaseUnits purchaseUnits  = new PurchaseUnits("USD", "500.00");
		purchase_unitsListArray.add(purchaseUnits);
		Orders order = new Orders("CAPTURE", purchase_unitsListArray);
		
		
		RestAssured.baseURI = "https://api.sandbox.paypal.com";
		
		
		Response response = 	given()
		.contentType(ContentType.JSON)
		.auth()
		.oauth2(access_token)
		.body(order)
		.post("/v2/checkout/orders");
		
		//response.prettyPrint();
		
		//System.out.println(response.jsonPath().getString("status"));
		orderId = response.jsonPath().getString("id");
		System.out.println(orderId);
		Assert.assertEquals(response.jsonPath().getString("status"), "CREATED");
		
	}
	
	@Test(priority = 3,dependsOnMethods = {"getAuthKey","createOrder"})
	public void getOrder() {
		RestAssured.baseURI = "https://api.sandbox.paypal.com";
		
		Response response = 	given()
		.contentType(ContentType.JSON)
		.auth()
		.oauth2(access_token)
		.get("/v2/checkout/orders"+"/"+orderId);
		//response.prettyPrint();
		//System.out.println(response.jsonPath().getString("value"));
		
		JsonPath jp =response.jsonPath();
		//System.out.println(jp.getString("purchase_units[0].payee.email_address"));
		System.out.println(jp.getString("links[0].rel").toString());
		System.out.println(jp.getString("links[0].method").toString());
		
		
		
	}
	
	

}

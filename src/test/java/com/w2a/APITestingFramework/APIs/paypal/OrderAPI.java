package com.w2a.APITestingFramework.APIs.paypal;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import com.w2a.APITestingFramework.pojo.Orders;
import com.w2a.APITestingFramework.pojo.PurchaseUnits;
import com.w2a.APITestingFramework.setUp.BaseTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OrderAPI extends BaseTest{
	
	static String client_key=prop.getProperty("paypalClientID");
	static String secret=prop.getProperty("paypalscrete");
	static String orderId;
	
	
	public static String getAccessToken() {
		String	access_token = 	given()
				.param("grant_type","client_credentials")
				.auth().preemptive()
				.basic(client_key, secret)
				.post("/v1/oauth2/token")
				.jsonPath().
				get("access_token").toString(); // fetch the access token form this
		
			///System.out.println(access_token);
		
		return access_token;
	}
	
	public static Response createOrder(String access_token){
		ArrayList<PurchaseUnits> purchase_unitsListArray = new ArrayList<PurchaseUnits>();
		PurchaseUnits purchaseUnits  = new PurchaseUnits("USD", "500.00");
		purchase_unitsListArray.add(purchaseUnits);
		Orders order = new Orders("CAPTURE", purchase_unitsListArray);
		Response response = 	given()
				.contentType(ContentType.JSON)
				.auth()
				.oauth2(access_token)
				.body(order)
				.post("/v2/checkout/orders");
		return response;
		
	}
	public static Response  getOrder(String access_token){
		Response response = 	given()
				.contentType(ContentType.JSON)
				.auth()
				.oauth2(access_token)
				.get("/v2/checkout/orders"+"/"+orderId);
		
		return response;
	}
	


}

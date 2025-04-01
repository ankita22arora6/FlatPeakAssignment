package com.FlatPeak.base;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Properties;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;

import com.FlatPeak.config.ConfigLoader;
import com.FlatPeak.endpoints.APIEndPoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseTest {
	
	public static Properties prop;
	protected static String bearerToken;
	protected static String connectToken;
	
	public void setUp() throws IOException
	{
		prop = ConfigLoader.loadProperties("config.properties");
		RestAssured.baseURI=prop.getProperty("baseURI");
	}
	
	//Create bearer Token
	public String generateBearerToken()
	{		
		Response response_bearerToken = given().
	  			  auth()
	  			  .preemptive()
	  			  .basic("acc_672a0c0c9986827a8308670b","sk_live_b97932ef78b844e7792d737f5f28191a")
	  			  .when()
	  			  .get(APIEndPoints.login);
		
		response_bearerToken.prettyPrint();

		String bearerToken = response_bearerToken.jsonPath().getString("bearer_token");
		System.out.println(bearerToken);
		
		return bearerToken;
	}
		
	//Create connect Token
	public String generateConnectToken(String bearer_Token)
	{
		JSONObject postalAddress = new JSONObject();
		postalAddress.put("country_code", "GB");
		
		JSONObject obj = new JSONObject();
		obj.put("direction", "IMPORT");
		obj.put("type", "COMMODITY");
		obj.put("callback_uri",prop.getProperty("callback_uri"));
		obj.put("postal_address", postalAddress);
		
		String payload = obj.toString();
		
		Response response_connectToken = given()
				.header("Authorization", "Bearer "+bearer_Token)
				.header("Content-Type", "application/json")
				.body(payload)
				.post(APIEndPoints.connect_token_endpoint);
		
		Assert.assertEquals(response_connectToken.getStatusCode(), 200);
		
		response_connectToken.prettyPrint();
		
		response_connectToken.then().log().all(); // logs full response
		given().log().all(); // logs full request
		
		String connectToken = response_connectToken.jsonPath().getString("connect_token");
		return connectToken;
				
	}
  
	@AfterSuite
	public void tearDown()
	{
		
	}
}

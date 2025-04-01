package com.FlatPeak.connect;

import com.FlatPeak.base.BaseTest;
import com.FlatPeak.endpoints.APIEndPoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ExchangeConnectToken extends BaseTest {
	
	public String exchangeConnectTokenForLocationId(String bearerToken, String connectToken)
	{
		
		Response response = RestAssured
						   .given()
						   .header("Authorization","Bearer " + bearerToken)
						   .queryParam("connect_token",connectToken)
						   .get(APIEndPoints.connect_token_endpoint);
		
		response.then().log().all().statusCode(200);
		
		String locationId = response.jsonPath().getString("location_id");
		System.out.println(locationId);
		
		return locationId;
	}

}

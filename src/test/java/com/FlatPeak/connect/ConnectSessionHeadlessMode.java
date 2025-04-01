package com.FlatPeak.connect;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import com.FlatPeak.DataUtil.ExtractDetails;
import com.FlatPeak.DataUtil.RouteTestData;
import com.FlatPeak.base.BaseTest;

import io.restassured.response.Response;

public class ConnectSessionHeadlessMode extends BaseTest{
	
	public void runHeadlessConnectSession(String connectToken)
	{
		String currentRoute = "session_restore";
	    JSONObject data = null;
	    ExtractDetails es = new ExtractDetails();

	    while (!"session_complete".equals(currentRoute)) {
	        JSONObject payload = new JSONObject();
	        payload.put("connect_token", connectToken);
	        payload.put("route", currentRoute);
	        
	   
	        if (data != null) {
	            payload.put("data", data);
	        }
	        
	        if("summary_tou_confirm".equals(currentRoute))
	        {
	        	payload.put("action","SAVE");
	        }
	        
	        Response response = given()
					.baseUri(prop.getProperty("connect_uri"))
					.header("Content-Type","application/json")
					.body(payload.toString())
					.when()
					.post();
	        
	        response.then().log().all().statusCode(200);
	        
	        currentRoute = response.jsonPath().getString("route");
	        System.out.println("Next Route is:" +currentRoute);
	        
	        
	        
	        data=null;
	  
	        if("provider_select".equals(currentRoute))
	        {
	        	String providerId = es.extractProviderId(response, "Ecotricity");
	        	data = new JSONObject();
				JSONObject provider = new JSONObject();
				provider.put("id", providerId);
				data.put("provider",provider);
	        }
	        else
	        {
	        	RouteTestData testData = new RouteTestData();
		        data = testData.getRouteTestData(currentRoute);
	        	
	        }
	}
	}
}

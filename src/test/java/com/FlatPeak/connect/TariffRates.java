package com.FlatPeak.connect;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.FlatPeak.base.BaseTest;
import com.FlatPeak.endpoints.APIEndPoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TariffRates extends BaseTest {
	
	public Response getTariffRates(String bearerToken, String locationId, String startTime, String endTime) throws InterruptedException
	{
		Thread.sleep(10000);
		Response response = RestAssured
							.given()
							.header("Authorization", "Bearer " + bearerToken)
			                .queryParam("start_time", startTime)
			                .queryParam("end_time", endTime)
			                .queryParam("include_tariff", true)
			                .queryParam("include_carbon", true)
			                .queryParam("direction", "IMPORT")
			                .when()
			                .get(APIEndPoints.tariff_rates+locationId);
		response.then().log().all().statusCode(200);
		return response;
				
	}
	
	public String getCurrentTime() {
		 ZonedDateTime now = ZonedDateTime
	                .now(ZoneOffset.UTC)
	                .withSecond(0)
	                .withNano(0);
	        
	    return now.format(DateTimeFormatter.ISO_INSTANT); 
	}
	
	public String getTimeforTomorrowMidnight() {
		 ZonedDateTime tomorrowMidnightUTC = ZonedDateTime
	                .now(ZoneOffset.UTC)
	                .plusDays(1)
	                .withHour(0)
	                .withMinute(0)
	                .withSecond(0)
	                .withNano(0);
	        
	        return tomorrowMidnightUTC.format(DateTimeFormatter.ISO_INSTANT);
	}

}

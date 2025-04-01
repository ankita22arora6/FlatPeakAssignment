package com.FlatPeak.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.FlatPeak.base.BaseTest;
import com.FlatPeak.connect.ConnectSessionHeadlessMode;
import com.FlatPeak.connect.ExchangeConnectToken;
import com.FlatPeak.connect.TariffRates;

import io.restassured.response.Response;

public class EndToEndTest extends BaseTest{
  
	 Response response;
	 String startTime;
	 String endTime;
	 List<Map<String, Object>> rates;
	
	 //Initial Setup
	 
	  @BeforeClass
	  public void initialSetup() throws IOException, InterruptedException {
		  
	  setUp();
	  
	  String bearerToken=generateBearerToken();
	  String connectToken=generateConnectToken(bearerToken);
	  
	  ConnectSessionHeadlessMode cs= new ConnectSessionHeadlessMode();
	  
	  cs.runHeadlessConnectSession(connectToken);
	  
	  ExchangeConnectToken ect = new ExchangeConnectToken();
	  
	  String locationId = ect.exchangeConnectTokenForLocationId(bearerToken, connectToken);
	  
	  TariffRates tr = new TariffRates();
	  
	  startTime = tr.getCurrentTime();
	  endTime = tr.getTimeforTomorrowMidnight();
	  
	  
	  
	   response = tr.getTariffRates(bearerToken, locationId, startTime, endTime);
	}
			  
	  //Verification End Time matches the Request
	  @Test(priority=0)
	  public void verifyEndTime()
	  {
		  String actualEnd = response.jsonPath().getString("data[-1].valid_to");
		  Assert.assertTrue(actualEnd.startsWith(endTime.substring(0, 10)), "End time should match");
	  }
      
	  //Verification Start Time matches the current time
	  
	  @Test(priority=1)
	  public void verifyStartTime()
	  {
		  String actualStart = response.jsonPath().getString("data[0].valid_from");
	      Assert.assertTrue(actualStart.startsWith(startTime.substring(0, 10)), "Start time should match");
	  }
	      
      //Verification of Currency code
	  
	  @Test(priority=2)
	  public void verifyCurrencyCode()
	  {
	  
		  String currencyCode = response.jsonPath().getString("currency_code");
		  Assert.assertEquals(currencyCode,"GBP", "Currency code should be GBP");
	  }
	  
    
      //Verification of Data array should have hourly rates
	  
	  @Test(priority=3)
	  public void verifyHourlyRatesArePresent()
	  {
	      rates = response.jsonPath().getList("data");
	      Assert.assertFalse(rates.isEmpty(), "Rates array should not be empty");
	  
	  }

      //Verification that confidence is 1 for all rates
	  
	  @Test(priority=4)
	  public void verifyConfidenceValue()
	  {
         for (Map<String, Object> rate : rates) {
          Map<String, Object> carbon = (Map<String, Object>) rate.get("carbon");
          
          if (carbon == null || !carbon.containsKey("confidence")) {
              Assert.fail("Carbon object or confidence value is missing in rate: " + rate);
          }

          Object confidence = carbon.get("confidence");
          Assert.assertEquals(confidence, 1, "Confidence should be 1 for all carbon entries.");
      }
	  }
         @AfterTest
         public void close()
         {
        	 System.out.println("Test Execution Done.");
         }
	  }
	  
	  
	  
  


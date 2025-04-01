package com.FlatPeak.DataUtil;

import java.util.List;
import java.util.Map;

import io.restassured.response.Response;

public class ExtractDetails {
	
	public String extractProviderId(Response response, String providerName)
	{
		List<Map<String,Object>> providers = response.jsonPath().getList("data.providers");
		
		
		for(Map<String,Object> provider:providers)
		{
			String displayName = (String) provider.get("display_name");
			if(providerName.equalsIgnoreCase(displayName))
			{
				return (String) provider.get("id");
			}
		}
		
		throw new RuntimeException("Provider "+providerName+" is not found in the response.");
	}

}

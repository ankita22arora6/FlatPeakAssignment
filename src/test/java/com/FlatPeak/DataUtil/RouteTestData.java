package com.FlatPeak.DataUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class RouteTestData {
	
	public JSONObject getRouteTestData(String route)
	{
		switch(route)
		{
		case "postal_address_capture" : {
			JSONObject addr = new JSONObject();
			JSONObject postal = new JSONObject();
			postal.put("address_line1","145");
			postal.put("address_line2","Glenville Grove");
			postal.put("city", "London");
			postal.put("state", "Greater London");
			postal.put("post_code", "SE8 4BJ");
			postal.put("country_code", "GB");
			addr.put("postal_address", postal);
			return addr;
			}
		case "tariff_structure_select" : {
			JSONObject data = new JSONObject();
			JSONArray optionsArray = new JSONArray();
			optionsArray.put("MARKET");
			data.put("options", optionsArray);
			return data;
			}
		 case "market_surcharge_capture" : {
			 JSONObject data = new JSONObject();
			 JSONObject surcharge = new JSONObject();
			 surcharge.put("fixed", 0.345);
			 surcharge.put("percentage", 18.50);

			 data.put("surcharge", surcharge);
			 data.put("region", "FR");

			 return data;
	        }
	      case "tariff_name_capture" : {
	    	  JSONObject data = new JSONObject();
	    	  JSONObject tariff = new JSONObject();
	    	  tariff.put("name", "Super Cheap Energy 2008");
	    	  data.put("tariff", tariff);
	    	  return data;
	        }
	       case "contract_term_capture" : {
	          JSONObject data = new JSONObject();
	          data.put("contract_end_date", "2025-06-15T00:00:00Z");
	          return data;
	        }
	        default : {
	            return null; 
	        }
		}
		
		}
	}



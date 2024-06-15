package com.w2a.APITestingFramework.utility;

import org.json.JSONObject;

import com.w2a.APITestingFramework.listeners.ExtentListeners;

public class TestUtil {
	
	public static boolean jsonHasKey(String json, String key) {
		
		JSONObject js = new JSONObject(json);
		ExtentListeners.testReport.get().info("Validating the presence of "+key);
		return js.has(key);
	}
	
	public static String getJsonKeyValue(String json, String key) {
		JSONObject js = new JSONObject(json);
		String value =js.getString(key);
		ExtentListeners.testReport.get().info("Validating the presence value "+value);
		return value;
		
	}

}

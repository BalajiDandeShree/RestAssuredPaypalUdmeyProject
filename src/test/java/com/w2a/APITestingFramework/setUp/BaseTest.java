package com.w2a.APITestingFramework.setUp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.w2a.APITestingFramework.utility.ExcelReader;

import io.restassured.RestAssured;

public class BaseTest {
private	FileInputStream fis;
public static	Properties prop;// =new Properties();
public static ExcelReader excelreader = new ExcelReader(".\\src\\test\\resources\\excel\\testdata.xlsx");

	@BeforeSuite
	public void setUp() throws IOException {
		fis = new FileInputStream(".\\src\\test\\resources\\properties\\config.properties");
		prop =new Properties();
		prop.load(fis);
		
		//RestAssured.baseURI = "https://api.stripe.com";
		RestAssured.baseURI =prop.getProperty("baseURI");
		
		
	}
	
	@AfterSuite
	public void tearDown() {

	}
}

package com.w2a.APITestingFramework.utility;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.DataProvider;

import com.w2a.APITestingFramework.setUp.BaseTest;

public class DataUtil extends BaseTest{
	
	@DataProvider(name = "data")
	public Object[][] getData(Method m){
		System.out.println("SheetName " + m.getName());
		
		String sheetName = m.getName();
		int rows = excelreader.getRowCount(sheetName);
		int cols = excelreader.getColumnCount(sheetName);
		System.out.println(rows +" " + cols);
		
		Object[] [] data = new Object[rows-1][cols];
		
		for(int row=2;row<=rows;row++) {
			for(int col=0;col<cols;col++) {
				data[row-2][col]=excelreader.getCellData(sheetName, col, row);	
			}
		}
		
//		for(int i = 0;i<(rows-1);i++) {
//			for(int j=0;i<cols;j++) {
//				data[i][j] = excelreader.getCellData(sheetName, j, i+2);
//			}
//		}
		
		
//		data[0][0] = excelreader.getCellData(sheetName, 0, 2);
//		data[0][1] = excelreader.getCellData(sheetName, 1, 2);
//		data[0][2] = excelreader.getCellData(sheetName, 2, 2);
//		
//		data[1][0] = excelreader.getCellData(sheetName, 0, 3);
//		data[1][1] = excelreader.getCellData(sheetName, 1, 3);
//		data[1][2] = excelreader.getCellData(sheetName, 2, 3);
		
		return data;
	}
	
	@DataProvider(name = "excelData")
	public Object[][] getData(){
		ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata2.xlsx");
		int rows  =excel.getRowCount(Constants.DATAS_HEET);
		System.out.println("Total no of rows" + rows);
		String testName = "validateCreateCustomerAPI";
		//Below code find the test case start 
		int testCaseRowNum=1;
		
		for ( testCaseRowNum = 1; testCaseRowNum <=rows; testCaseRowNum++) {
		String  testNameExcel=	excel.getCellData(Constants.DATAS_HEET, 0, testCaseRowNum);
		if(testNameExcel.equalsIgnoreCase(testName)) {
			break;
		}
		}
		
		System.out.println("test case start from row no."+testCaseRowNum);
		
		// Checking total rows in test case
		int dataStartRowNum =testCaseRowNum+2;
		int testrows=0;
		
		while(!excel.getCellData(Constants.DATAS_HEET, 0, dataStartRowNum+testrows).equals("")) {
			testrows++;
		}
			
		System.out.println("Total data rows are " + testrows);
		
		//checking total cols in test case
		
		int colstartColNum = testCaseRowNum+1;
		int testCols=0;
		
		while(!excel.getCellData(Constants.DATAS_HEET, testCols, colstartColNum+testrows).equals("")) {
			testCols++;
		}
		System.out.println("Total data cols are " + testCols);
		
		Object data[][] = new Object[testrows][1];
		int i=0;
		for(int rNum=dataStartRowNum; rNum<(dataStartRowNum+testrows);rNum++ ) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			for(int cNum=0;cNum<testCols;cNum++) {
			//System.out.print(excel.getCellData(Constants.DATAS_HEET, cNum, rNum)+" ");
				//data[rNum-dataStartRowNum][cNum] 
				String testData =excel.getCellData(Constants.DATAS_HEET, cNum, rNum);
				String colName = excel.getCellData(Constants.DATAS_HEET, cNum, colstartColNum);
				table.put(colName, testData);
			}
			//System.out.println();
			data[i][0]=table;
			i++;
		}
	
		return data;
	}
	
	
	


	

}

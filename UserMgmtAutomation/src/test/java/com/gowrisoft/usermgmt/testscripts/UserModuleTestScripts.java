package com.gowrisoft.usermgmt.testscripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;

import com.gowrisoft.usermgmt.driver.DriverScript;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class UserModuleTestScripts extends DriverScript{
	/****************************************************************
	 * TestScript Name	: TS_Create_Delete_User
	 * Test case ID		: user_101
	 * Purpose			: automated the test case user_101
	 * Author			: 
	 * Parameters		: NA
	 * Return Type		: boolean
	 * 
	 ****************************************************************/
	public boolean TS_Create_Delete_User(String moduleName, String testCaseID)
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String strStatus = null;
		String strUserName = null;
		ExtentReports extent = null;
		ExtentTest test = null;
		String resultLocation = null;
		try {
			resultLocation = reports.createResultDirectories(appInd.readPropData("BuildNumber"), moduleName, testCaseID);
			
			extent = reports.startReport(resultLocation, "Create_Delete_User");
			test = extent.startTest("Pre requisite");
			objData = datatable.getExcelTestData("UserTestData", "user_101", moduleName, resultLocation, test);
			oBrowser = appInd.launchApp(objData.get("TD_BrowserType"), resultLocation, test);
			
			if(oBrowser !=null ) {
				strStatus+= appDep.navigateURL(oBrowser, appInd.readPropData("URL"), resultLocation, test);
				reports.writeResult(oBrowser, "screenshot", "Login page opened successful", resultLocation, test);
				
				test = extent.startTest("loginToApplication");
				strStatus+= appDep.loginToApp(oBrowser, objData.get("TD_UN"), objData.get("TD_PWD"), resultLocation, test);
				
				test = extent.startTest("createUser");
				strUserName = userModule.createUser(oBrowser, objData, resultLocation, test);
				
				test = extent.startTest("deleteUser");
				strStatus+= userModule.deleteUser(oBrowser, strUserName, resultLocation, test);
				
				test = extent.startTest("createUser");
				strStatus+= appDep.logoutFromApp(oBrowser, resultLocation, test);
				
				if(strStatus.contains("false")) {
					return false;
				}else {
					return true;
				}
			}else {
				reports.writeResult(oBrowser, "Fail", "Failed to launch the '"+objData.get("TD_BrowserType")+"' browser", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in TS_Create_Delete_User() test script", resultLocation, test);
			return false;
		}
		finally
		{
			appInd.closeBrowser(oBrowser, resultLocation, test);
			reports.endTest(extent, test);
			oBrowser = null;
		}
	}
}

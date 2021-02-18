package com.gowrisoft.usermgmt.testscripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.gowrisoft.usermgmt.driver.DriverScript;

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
		try {
			test = extent.startTest("TS_Create_Delete_User");
			objData = datatable.getExcelTestData("UserTestData", "user_101", moduleName, test);
			oBrowser = appInd.launchApp(objData.get("TD_BrowserType"), test);
			
			if(oBrowser !=null ) {
				strStatus+= appDep.navigateURL(oBrowser, appInd.readPropData("URL"), test);
				reports.writeResult(oBrowser, "screenshot", "Login page opened successful", test);
				
				strStatus+= appDep.loginToApp(oBrowser, objData.get("TD_UN"), objData.get("TD_PWD"), test);
				
				strUserName = userModule.createUser(oBrowser, objData, test);
				
				strStatus+= userModule.deleteUser(oBrowser, strUserName, test);
				
				strStatus+= appDep.logoutFromApp(oBrowser, test);
				
				if(strStatus.contains("false")) {
					return false;
				}else {
					return true;
				}
			}else {
				reports.writeResult(oBrowser, "Fail", "Failed to launch the '"+objData.get("TD_BrowserType")+"' browser", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in TS_Create_Delete_User() test script", test);
			return false;
		}
		finally
		{
			appInd.closeBrowser(oBrowser, test);
			reports.endExtentReport(test);
			oBrowser = null;
		}
	}
}

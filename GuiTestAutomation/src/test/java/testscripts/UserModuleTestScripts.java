package testscripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import driver.DriverScript;


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
	public boolean TS_Create_Delete_User()
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String strStatus = null;
		String strUserName = null;
		try {
			extent = reports.startReport("Create_Delete_User", strTestCaseID, appInd.readPropData("BuildNumber"));
			test = extent.startTest("Pre requisite");
			objData = datatable.getExcelTestData("UserTestData", "user_101");
			oBrowser = appInd.launchApp(objData.get("TD_BrowserType"));
			
			if(oBrowser !=null ) {
				strStatus+= appDep.navigateURL(oBrowser, appInd.readPropData("URL"));
				reports.writeResult(oBrowser, "screenshot", "Login page opened successful", test);
				strStatus+= appDep.loginToApp(oBrowser, objData.get("TD_UN"), objData.get("TD_PWD"));
				strUserName = userModule.createUser(oBrowser, objData);
				strStatus+= userModule.deleteUser(oBrowser, strUserName);
				strStatus+= appDep.logoutFromApp(oBrowser);
				
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
			appInd.closeBrowser(oBrowser);
			reports.endTest(test);
			oBrowser = null;
		}
	}
}

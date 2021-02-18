package com.gowrisoft.usermgmt.testscripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.gowrisoft.usermgmt.driver.DriverScript;

public class ProjectModuleTestScripts extends DriverScript{
	/****************************************************************
	 * TestScript Name	: TS_Create_Delete_Project
	 * Test case ID		: project_101
	 * Purpose			: automated the test case project_101
	 * Author			: 
	 * Parameters		: NA
	 * Return Type		: boolean
	 * 
	 ****************************************************************/
	public boolean TS_Create_Delete_Project(String moduleName, String testCaseID)
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String strStatus = null;
		String strProjectName = null;
		try {			
			test = extent.startTest("TS_Create_Delete_Project");
			objData = datatable.getExcelTestData("ProjectTestData", "project_101", moduleName, test);
			oBrowser = appInd.launchApp(objData.get("TD_BrowserType"), test);
			
			if(oBrowser !=null ) {
				strStatus+= appDep.navigateURL(oBrowser, appInd.readPropData("URL"), test);
				
				strStatus+= appDep.loginToApp(oBrowser, objData.get("TD_UN"), objData.get("TD_PWD"), test);
				
				strProjectName = projectModule.createProject(oBrowser, objData, test);
				
				strStatus+= projectModule.deleteProject(oBrowser, strProjectName, test);
				
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
			reports.writeResult(oBrowser, "Exception", "Exception in TS_Create_Delete_Project() test script", test);
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

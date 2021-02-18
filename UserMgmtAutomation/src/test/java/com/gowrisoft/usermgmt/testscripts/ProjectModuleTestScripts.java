package com.gowrisoft.usermgmt.testscripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;

import com.gowrisoft.usermgmt.driver.DriverScript;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

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
		ExtentReports extent = null;
		ExtentTest test = null;
		String resultLocation = null;
		try {
			resultLocation = reports.createResultDirectories(appInd.readPropData("BuildNumber"), moduleName, testCaseID);
			
			extent = reports.startReport(resultLocation, "Create_Delete_Project");
			test = extent.startTest("Pre requisite");
			objData = datatable.getExcelTestData("ProjectTestData", "project_101", moduleName, resultLocation, test);
			oBrowser = appInd.launchApp(objData.get("TD_BrowserType"), resultLocation, test);
			
			if(oBrowser !=null ) {
				strStatus+= appDep.navigateURL(oBrowser, appInd.readPropData("URL"), resultLocation, test);
				
				test = extent.startTest("login To Application");
				strStatus+= appDep.loginToApp(oBrowser, objData.get("TD_UN"), objData.get("TD_PWD"), resultLocation, test);
				
				test = extent.startTest("Create Project");
				strProjectName = projectModule.createProject(oBrowser, objData, resultLocation, test);
				
				test = extent.startTest("Delete Project");
				strStatus+= projectModule.deleteProject(oBrowser, strProjectName, resultLocation, test);
				
				test = extent.startTest("Logout From Application");
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
			reports.writeResult(oBrowser, "Exception", "Exception in TS_Create_Delete_Project() test script", resultLocation, test);
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

package testscripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import driver.DriverScript;

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
	public boolean TS_Create_Delete_Project()
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String strStatus = null;
		String strProjectName = null;
		try {
			extent = reports.startReport("Create_Delete_Project", strTestCaseID, appInd.readPropData("BuildNumber"));
			test = extent.startTest("Pre requisite");
			objData = datatable.getExcelTestData("ProjectTestData", "project_101");
			oBrowser = appInd.launchApp(objData.get("TD_BrowserType"));
			
			if(oBrowser !=null ) {
				strStatus+= appDep.navigateURL(oBrowser, appInd.readPropData("URL"));
				strStatus+= appDep.loginToApp(oBrowser, objData.get("TD_UN"), objData.get("TD_PWD"));
				strProjectName = projectModule.createProject(oBrowser, objData);
				strStatus+= projectModule.deleteProject(oBrowser, strProjectName);
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
			reports.writeResult(oBrowser, "Exception", "Exception in TS_Create_Delete_Project() test script", test);
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

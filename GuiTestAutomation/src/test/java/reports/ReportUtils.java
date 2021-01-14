package reports;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import driver.DriverScript;

public class ReportUtils extends DriverScript{
	/****************************************************************
	 * Method Name	: startReport
	 * Purpose		: To start the extentReport mechanism
	 * Author		: 
	 * Parameters	: String fileName, String scenarioName, String strBuildNum
	 * Return Type	: ExtentReports
	 * 
	 ****************************************************************/
	public ExtentReports startReport(String fileName, String scenarioName, String strBuildNum)
	{
		String strPath = null;
		File objResLocation = null;
		File objScreenShot = null;
		try {
			strPath = System.getProperty("user.dir")+"\\Results";
			
			
			strResultLocation = strPath +"\\"+strBuildNum+"\\"+strModuleName+"\\"+scenarioName;
			strScreenshotLocation = strResultLocation+"\\screenshots";
						
			
			//Create the result dir
			objResLocation = new File(strResultLocation);
			if(!objResLocation.exists()) {
				objResLocation.mkdirs();
			}
			
			//Create screenshot dir
			objScreenShot = new File(strScreenshotLocation);
			if(!objScreenShot.exists())
			{
				objScreenShot.mkdir();
			}
			
			//Create a object for extent resports
			extent = new ExtentReports(strResultLocation + "\\" + fileName + "_" + appInd.getDataTime("ddMMYYYY_hhmmss")+".html", true);
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("Environment", appInd.readPropData("Environment"));
			extent.addSystemInfo("AppName", appInd.readPropData("AppName"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			return extent;
		}catch(Exception e)
		{
			System.out.println("Exception in startReport() method. "+e.getMessage());
			return null;
		}
		finally {
			objResLocation = null;
			objScreenShot = null;
		}
	}
	
	
	
	
	/****************************************************************
	 * Method Name	: endTest
	 * Purpose		: To stop the extentReport mechanism
	 * Author		: 
	 * Parameters	: ExtentTest test
	 * Return Type	: void
	 * 
	 ****************************************************************/
	public void endTest(ExtentTest test)
	{
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
	
	/****************************************************************
	 * Method Name	: getScreenshot
	 * Purpose		: To catpture the screen shot
	 * Author		: 
	 * Parameters	: WebDriver oDriver
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String getScreenshot(WebDriver oDriver)
	{
		File objSource = null;
		String destination = null;
		File objDest = null;
		try {
			TakesScreenshot ts = (TakesScreenshot) oDriver;
			objSource = ts.getScreenshotAs(OutputType.FILE);
			destination = strScreenshotLocation+"\\"+"screenshot_"+appInd.getDataTime("ddMMYYYY_hhmmss")+".png";
			objDest = new File(destination);
			FileHandler.copy(objSource, objDest);
			return destination;
		}catch(Exception e)
		{
			System.out.println("Exception in getScreenshot() method. "+e.getMessage());
			return null;
		}
		finally {
			objSource = null;
			objDest = null;
		}
	}
	
	
	
	/****************************************************************
	 * Method Name	: writeResult
	 * Purpose		: To catpture the screen shot
	 * Author		: 
	 * Parameters	: WebDriver oDriver, Status, Description, ExtentTest
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public void writeResult(WebDriver oDriver, String status, String description, ExtentTest test)
	{
		try {
			switch(status.toLowerCase())
			{
				case "pass":
					test.log(LogStatus.PASS, description);
					break;
				case "fail":
					if(oDriver==null) {
						test.log(LogStatus.FAIL, description);
					}else {
						test.log(LogStatus.FAIL, description+": "+test.addScreenCapture(reports.getScreenshot(oDriver)));
					}
					break;
				case "info":
					test.log(LogStatus.INFO, description);
					break;
				case "exception":
					if(oDriver==null) {
						test.log(LogStatus.FATAL, description);
					}else {
						test.log(LogStatus.FATAL, description+": "+test.addScreenCapture(reports.getScreenshot(oDriver)));
					}
					break;
				case "warning":
					test.log(LogStatus.WARNING, description);
					break;
				case "screenshot":
					test.log(LogStatus.INFO, description+": "+test.addScreenCapture(reports.getScreenshot(oDriver)));
					break;
				default:
					System.out.println("Invalid status '"+status+"' was mentioned");
			}
		}catch(Exception e)
		{
			System.out.println("Exception in writeResult() metehod. "+e.getMessage());
		}
	}
}

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
	 * Method Name	: createResultDirectories
	 * Purpose		: To create the directory structure the the test scripts results
	 * Author		: 
	 * Parameters	: String buildNum, String moduleName, String testCaseID
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String createResultDirectories(String buildNum, String moduleName, String testCaseID) {
		File objFileDir = null;
		String resultDir = null;
		try {
			resultDir = System.getProperty("user.dir")+"\\Results\\"+buildNum+"\\"+moduleName+"\\"+testCaseID;
			objFileDir = new File(resultDir);
			if(!objFileDir.exists()) {
				objFileDir.mkdirs();
			}
			
			return resultDir;
		}catch(Exception e)
		{
			System.out.println("Exception in createResultDirectories() method. "+e.getMessage());
			return null;
		}
		finally {
			objFileDir = null;
			resultDir = null;
		}
	}
	
	
	
	
	
	
	/****************************************************************
	 * Method Name	: startReport
	 * Purpose		: To start the extentReport mechanism
	 * Author		: 
	 * Parameters	: String strResultLocation
	 * Return Type	: ExtentReports
	 * 
	 ****************************************************************/
	public ExtentReports startReport(String strResultLocation, String fileName)
	{
		ExtentReports extentReport = null;
		try {		
			//Create a object for extent resports
			extentReport = new ExtentReports(strResultLocation + "\\" + fileName + "_" + appInd.getDataTime("ddMMYYYY_hhmmss")+".html", true);
			extentReport.addSystemInfo("Host Name", System.getProperty("os.name"));
			extentReport.addSystemInfo("Environment", appInd.readPropData("Environment"));
			extentReport.addSystemInfo("AppName", appInd.readPropData("AppName"));
			extentReport.addSystemInfo("User Name", System.getProperty("user.name"));
			extentReport.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			return extentReport;
		}catch(Exception e)
		{
			System.out.println("Exception in startReport() method. "+e.getMessage());
			return null;
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
	public void endTest(ExtentReports extent, ExtentTest test)
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
	 * Parameters	: WebDriver oDriver, String resultLocation
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String getScreenshot(WebDriver oDriver, String resultLocation)
	{
		File objSource = null;
		String destination = null;
		File objDest = null;
		File resultDir = null;
		try {
			
			resultDir = new File(resultLocation+"\\screenshots\\");
			if(!resultDir.exists()) {
				resultDir.mkdir();
			}
			
			TakesScreenshot ts = (TakesScreenshot) oDriver;
			objSource = ts.getScreenshotAs(OutputType.FILE);
			destination = resultLocation+"\\screenshots\\"+"screenshot_"+appInd.getDataTime("ddMMYYYY_hhmmss")+".png";
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
			resultDir = null;
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
	public void writeResult(WebDriver oDriver, String status, String description, String resultLocation, ExtentTest test)
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
						test.log(LogStatus.FAIL, description+": "+test.addScreenCapture(reports.getScreenshot(oDriver, resultLocation)));
					}
					break;
				case "info":
					test.log(LogStatus.INFO, description);
					break;
				case "exception":
					if(oDriver==null) {
						test.log(LogStatus.FATAL, description);
					}else {
						test.log(LogStatus.FATAL, description+": "+test.addScreenCapture(reports.getScreenshot(oDriver, resultLocation)));
					}
					break;
				case "warning":
					test.log(LogStatus.WARNING, description);
					break;
				case "screenshot":
					test.log(LogStatus.INFO, description+": "+test.addScreenCapture(reports.getScreenshot(oDriver, resultLocation)));
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

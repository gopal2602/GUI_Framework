package com.gowrisoft.usermgmt.reports;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.gowrisoft.usermgmt.driver.DriverScript;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class ReportUtils extends DriverScript{
	/***********************************************************
	 * Method Name			: startExtentReport()
	 * Author Name			: 
	 * Purpose				: It is to start the extentReporting directories and files
	 * Arguments			: String fileName, String buildName
	 * Return Type			: ExtentReports
	 * 
	 ************************************************************/
	public ExtentReports startExtentReport(String fileName, String buildName)
	{
		String resultPath = null;
		File objResPath = null; 
		File objScreenShot = null;
		File objArchive = null;
		File objResultFile = null;
		try {
			resultPath = System.getProperty("user.dir")+"\\Results\\"+buildName;
			
			objResPath = new File(resultPath);
			if(!objResPath.exists()) {
				objResPath.mkdirs();
			}
			
			screenshotLocation = resultPath+"\\screenshot";
			objScreenShot = new File(screenshotLocation);
			if(!objScreenShot.exists()) {
				objScreenShot.mkdirs();
			}
			
			objArchive = new File(resultPath + "\\Archive");
			if(!objArchive.exists()) {
				objArchive.mkdir();
			}
			
			objResultFile = new File(objResPath +"\\"+fileName+".html");
			if(objResultFile.exists()) {
				objResultFile.renameTo(new File(objArchive+"\\" + buildName+"_"+fileName+appInd.getDateTime("ddMMYYYY_hhmmss")+".html"));
			}
			
			extent = new ExtentReports(objResPath +"\\"+fileName+".html", false);
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("Environment", appInd.readPropData("Environment"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			return extent;
		}catch(Exception e)
		{
			System.out.println("Exception in 'startExtentReport()' method. "+e.getMessage());
			return null;
		}
		finally {
			objResPath = null; 
			objScreenShot = null;
		}
	}
	
	
	
	/***********************************************************
	 * Method Name			: endExtentReport()
	 * Author Name			: 
	 * Purpose				: It is to end the extentReporting. So that it will write ot the html file
	 * Arguments			: ExtentTest test
	 * Return Type			: void
	 * 
	 ************************************************************/
	public void endExtentReport(ExtentTest test)
	{
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e)
		{
			System.out.println("Exception in 'endExtentReport()' method. "+e.getMessage());
		}
	}
	
	
	
	
	/***********************************************************
	 * Method Name			: getScreenshot()
	 * Author Name			: 
	 * Purpose				: It is to capture the screenshot when required
	 * Arguments			: WebDriver oBrowser
	 * Return Type			: String
	 * 
	 ************************************************************/
	public String getScreenshot(WebDriver oBrowser)
	{
		File objSource = null;
		String strDestination = null;
		File objDestination = null;
		try {
			strDestination = screenshotLocation +"\\"+"screenshot_"+appInd.getDateTime("ddMMYYYY_hhmmss")+".png";
			TakesScreenshot ts = (TakesScreenshot) oBrowser;
			objSource = ts.getScreenshotAs(OutputType.FILE);
			
			objDestination = new File(strDestination);
			FileHandler.copy(objSource, objDestination);
			
			return strDestination;
		}catch(Exception e)
		{
			System.out.println("Exception in 'getScreenshot()' method. "+e.getMessage());
			return null;
		}
		finally {
			objSource = null; 
			objDestination = null;
		}
	}
	
	
	
	
	/***********************************************************
	 * Method Name			: writeResult()
	 * Author Name			: 
	 * Purpose				: It is to capture the screenshot when required
	 * Arguments			: WebDriver oBrowser
	 * Return Type			: String
	 * 
	 ************************************************************/
	public void writeResult(WebDriver oBrowser, String status, String strDescription, ExtentTest test)
	{
		try {
			switch(status.toLowerCase())
			{
				case  "pass":
					test.log(LogStatus.PASS, strDescription);
					break;
				case "fail":
					test.log(LogStatus.FAIL, strDescription + " : " + test.addScreenCapture(reports.getScreenshot(oBrowser)));
					break;
				case "warning":
					test.log(LogStatus.WARNING, strDescription);
					break;
				case "info":
					test.log(LogStatus.INFO, strDescription);
					break;
				case "exception":
					test.log(LogStatus.FATAL, strDescription + " : " + test.addScreenCapture(reports.getScreenshot(oBrowser)));
					break;
				case "screenshot":
					test.log(LogStatus.PASS, strDescription + " : " + test.addScreenCapture(reports.getScreenshot(oBrowser)));
					break;
				default:
					System.out.println("Invalid result status '"+status+"'. Please provide appropriate status for result");
			}
		}catch(Exception e)
		{
			System.out.println("Exception in 'writeResult()' method. "+e.getMessage());
		}
	}
}

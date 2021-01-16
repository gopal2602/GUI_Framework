package methods;

import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.ExtentTest;
import driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/****************************************************************
	 * Method Name	: getDataTime
	 * Purpose		: To get the time stamp for the current system date
	 * Author		: 
	 * Parameters	: format
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String getDataTime(String format)
	{
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(format);
			return sdf.format(dt);
		}catch(Exception e)
		{
			System.out.println("Exception in getDataTime() method. "+e.getMessage());
			return null;
		}
		finally {
			dt = null;
			sdf = null;
		}
	}
	
	
	
	/****************************************************************
	 * Method Name	: readPropData
	 * Purpose		: To read the master data from the prop file
	 * Author		: 
	 * Parameters	: strKey
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String readPropData(String strKey)
	{
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir")+"\\Configurations\\Config.properties");
			prop = new Properties();
			prop.load(fin);
			
			return prop.getProperty(strKey);
		}catch(Exception e)
		{
			System.out.println("Exception in readPropData() method. "+e.getMessage());
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				prop = null;
			}catch(Exception e)
			{
				System.out.println("Exception in readPropData() method. "+e.getMessage());
				return null;
			}
		}
	}
	
	
	
	/************************************************
	 * Method Name		: launchApp()
	 * Purpose			: to launch the required browsers
	 * Author			:
	 * Reviewer			:
	 * Arguments		: String browserName, String resultLocation. ExtentTest test
	 * Return type		: WebDriver
	 * Date Created		:
	 * **********************************************
	 */
	public WebDriver launchApp(String browserName, String resultLocation, ExtentTest test)
	{
		WebDriver oDriver = null;
		try {
			switch(browserName.toLowerCase())
			{
				case "chrome":
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
							+"\\Library\\drivers\\chromedriver.exe");
					oDriver = new ChromeDriver();
					break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
							+"\\Library\\drivers\\geckodriver.exe");
					oDriver = new FirefoxDriver();
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")
							+"\\Library\\drivers\\IEDriverServer.exe");
					oDriver = new InternetExplorerDriver();
					break;
				default:
					reports.writeResult(oDriver, "Fail", "Invalid browser name '"+browserName+"'", resultLocation, test);
			}
			
			if(oDriver!=null) {
				reports.writeResult(oDriver, "Pass", "The '"+browserName+"' browser was launched successful", resultLocation, test);
				oDriver.manage().window().maximize();
				return oDriver;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to launch the '"+browserName+"' browser", resultLocation, test);
				return null;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in launchApp() method. "+e.getMessage(), resultLocation, test);
			return null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: closeBrowser()
	 * Purpose			: to close the browser
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String resultLocation, ExtentTest test
	 * Return type		: void
	 * Date Created		:
	 * **********************************************
	 */
	public void closeBrowser(WebDriver oDriver, String resultLocation, ExtentTest test)
	{
		try {
			oDriver.close();
			oDriver = null;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in closeBrowser() method. "+e.getMessage(), resultLocation, test);
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: clickObject()
	 * Purpose			: to click the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, By objBy, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean clickObject(WebDriver oDriver, By objBy, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			
			if(oEles.size() > 0) {
				oEles.get(0).click();
				reports.writeResult(oDriver, "Pass", "The Element '"+String.valueOf(objBy)+"' was licked successful.", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to click as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in clickObject() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: clickObject()
	 * Purpose			: to click the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean clickObject(WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				oEles.get(0).click();
				reports.writeResult(oDriver, "Pass", "The Element '"+strObjectName+"' was licked successful.", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to click as the Element '"+strObjectName+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in clickObject() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: setObject()
	 * Purpose			: to enter the value in the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, By objBy, String strData, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean setObject(WebDriver oDriver, By objBy, String strData, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			
			if(oEles.size() > 0) {
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oDriver, "Pass", "The data '"+strData+"' was entered in the Element '"+String.valueOf(objBy)+"' successful", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to Enter the data '"+strData+"' as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in setObject() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: setObject()
	 * Purpose			: to enter the value in the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String strObjectName, String strData, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean setObject(WebDriver oDriver, String strObjectName, String strData, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oDriver, "Pass", "The data '"+strData+"' was entered in the Element '"+strObjectName+"' successful", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to Enter the data '"+strData+"' as the Element '"+strObjectName+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in setObject() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	
	/************************************************
	 * Method Name		: verifyText()
	 * Purpose			: to validate the element text values
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, By objBy, String objectType, String expected, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyText(WebDriver oDriver, By objBy, String objectType, String expected, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		Select oSel = null;
		String actual = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0)
			{
				switch(objectType.toLowerCase())
				{
					case "text":
						actual = oEles.get(0).getText();
						break;
					case "value":
						actual = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(oEles.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(null, "Fail", "Invalid object type '"+objectType+"' was specified.", resultLocation, test);
				}
				
				if(appInd.compareValues(oDriver, actual, expected, resultLocation, test)) {
					return true;
				}else {
					return false;
				}
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to validate the text as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyText() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
			oSel = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: verifyText()
	 * Purpose			: to validate the element text values
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String strObjectName, String objectType, String expected, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyText(WebDriver oDriver, String strObjectName, String objectType, String expected, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		Select oSel = null;
		String actual = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() > 0)
			{
				switch(objectType.toLowerCase())
				{
					case "text":
						actual = oEles.get(0).getText();
						break;
					case "value":
						actual = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(oEles.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(null, "Fail", "Invalid object type '"+objectType+"' was specified.", resultLocation, test);
				}
				
				if(appInd.compareValues(oDriver, actual, expected, resultLocation, test)) {
					return true;
				}else {
					return false;
				}
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to validate the text as the Element '"+strObjectName+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyText() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
			oSel = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: compareValues()
	 * Purpose			: to compare both actual and expected values
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String actual, String expected, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean compareValues(WebDriver oDriver, String actual, String expected, String resultLocation, ExtentTest test)
	{
		try {
			if(actual.equalsIgnoreCase(expected)) {
				reports.writeResult(oDriver, "Pass", "Both actual '"+actual+"' & expected '"+expected+"' values are matched", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Mis-match in both actual '"+actual+"' & expected '"+expected+"' values", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in compareValues() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
	}
	
	
	
	
	
	/************************************************
	 * Method Name		: verifyElementExist()
	 * Purpose			: to check for the presence of the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, By objBy, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyElementExist(WebDriver oDriver, By objBy, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+String.valueOf(objBy)+"' was present in the DOM", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "The element '"+String.valueOf(objBy)+"' DOESNOT present in the DOM", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyElementExist() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: verifyElementExist()
	 * Purpose			: to check for the presence of the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyElementExist(WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() > 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+strObjectName+"' was present in the DOM", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "The element '"+strObjectName+"' DOESNOT present in the DOM", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyElementExist() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	
	/************************************************
	 * Method Name		: verifyElementNotExist()
	 * Purpose			: to check for the invisibility of the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, By objBy, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyElementNotExist(WebDriver oDriver, By objBy, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() == 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+String.valueOf(objBy)+"' Not present in the DOM", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "The element '"+String.valueOf(objBy)+"' present in the DOM", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyElementNotExist() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: verifyElementNotExist()
	 * Purpose			: to check for the invisibility of the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyElementNotExist(WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() == 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+strObjectName+"' Not present in the DOM", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "The element '"+strObjectName+"' present in the DOM", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyElementNotExist() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	
	/************************************************
	 * Method Name		: verifyOptionalElement()
	 * Purpose			: to check for the optional element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, By objBy, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyOptionalElement(WebDriver oDriver, By objBy, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() == 0) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyOptionalElement() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	
	/************************************************
	 * Method Name		: verifyOptionalElement()
	 * Purpose			: to check for the optional element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyOptionalElement(WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() == 0) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyOptionalElement() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: selectObject()
	 * Purpose			: to enter the value in the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, By objBy, String strData, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean selectObject(WebDriver oDriver, By objBy, String strData, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		Select oSel = null;
		try {
			oEles = oDriver.findElements(objBy);
			
			if(oEles.size() > 0) {
				oSel = new Select(oEles.get(0));
				oSel.selectByVisibleText(strData);
				reports.writeResult(oDriver, "Pass", "The data '"+strData+"' was selected from the Element '"+String.valueOf(objBy)+"' successful", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to select the data '"+strData+"' as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in selectObject() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: setObject()
	 * Purpose			: to enter the value in the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String strObjectName, String strData, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean selectObject(WebDriver oDriver, String strObjectName, String strData, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		Select oSel = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				oSel = new Select(oEles.get(0));
				oSel.selectByVisibleText(strData);
				reports.writeResult(oDriver, "Pass", "The data '"+strData+"' was selected from the Element '"+strObjectName+"' successful", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to select the data '"+strData+"' as the Element '"+strObjectName+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in selectObject() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	
	
	/****************************************************************
	 * Method Name	: waitFor
	 * Purpose		: To wait for the elements dynamically
	 * Author		: 
	 * Parameters	: WebDriver oDriver, By objBy, String strReason, String strText, int timeOut
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean waitFor(WebDriver oDriver, By objBy, String strReason, String strText, int timeOut)
	{
		WebDriverWait oWait = null;
		try {
			oWait = new WebDriverWait(oDriver, timeOut);
			switch(strReason.toLowerCase()) {
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(objBy));
					return true;
				case "visibility":
					oWait.until(ExpectedConditions.visibilityOfElementLocated(objBy));
					return true;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, strText));
					return true;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, strText));
					return true;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
					return true;
				default:
					System.out.println("Invalid wait condition provided: '"+strReason+"'.");
					return false;
			}
			
		}catch(Exception e)
		{
			System.out.println("Exception in waitFor() method. "+e.getMessage());
			return false;
		}
		finally
		{
			try {
				oWait = null;
				Thread.sleep(1000);
			}catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
	
	
	
	
	/****************************************************************
	 * Method Name	: waitFor
	 * Purpose		: To wait for the elements dynamically
	 * Author		: 
	 * Parameters	: WebDriver oDriver, String strObjectName, String strReason, String strText, int timeOut
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean waitFor(WebDriver oDriver, String strObjectName, String strReason, String strText, int timeOut)
	{
		WebDriverWait oWait = null;
		WebElement oEle = null;
		try {
			oEle = oDriver.findElement(By.xpath(strObjectName));
			oWait = new WebDriverWait(oDriver, timeOut);
			switch(strReason.toLowerCase()) {
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(oEle));
					return true;
				case "visibility":
					oWait.until(ExpectedConditions.visibilityOf(oEle));
					return true;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElement(oEle, strText));
					return true;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(oEle, strText));
					return true;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOf(oEle));
					return true;
				case "alert":
					oWait.until(ExpectedConditions.alertIsPresent());
					return true;
				default:
					System.out.println("Invalid wait condition provided: '"+strReason+"'.");
					return false;
			}
			
		}catch(Exception e)
		{
			System.out.println("Exception in waitFor() method. "+e.getMessage());
			return false;
		}
		finally
		{
			try {
				oWait = null;
				oEle = null;
				Thread.sleep(1000);
			}catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
	
	
	
	/****************************************************************
	 * Method Name	: verifyAlertPresent
	 * Purpose		: To check for the presence of the alert
	 * Author		: 
	 * Parameters	: WebDriver oDriver
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean verifyAlertPresent(WebDriver oDriver)
	{
		try {
			oDriver.switchTo().alert();
			return true;
		}catch(NoAlertPresentException oAlert) {
			return false;
		}
	}
	
	
	
	
	/****************************************************************
	 * Method Name	: getMonthInNumber
	 * Purpose		: To get the month in numbers based on month Name
	 * Author		: 
	 * Parameters	: String monthName
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String getMonthInNumber(String monthName) {
		Date date = null;
		Calendar cal = null;
		try {
			date = new SimpleDateFormat("MMMM").parse(monthName);
			cal = Calendar.getInstance();
			cal.setTime(date);
			if((cal.get(Calendar.MONTH)+1) < 10) {
				return "0" + (cal.get(Calendar.MONTH)+1);
			}else {
				return String.valueOf((cal.get(Calendar.MONTH)+1));
			}
		}catch(ParseException e) {
			System.out.println("Exception in getMonthInNumber() method. "+e.getMessage());
			return null;
		}
		finally {
			date = null;
			cal = null;
		}
	}
	
	
	
	
	/****************************************************************
	 * Method Name	: getElementText
	 * Purpose		: To get the text/value present on the element
	 * Author		: 
	 * Parameters	: WebDriver oDriver, By objBy, String objectType, String resultLocation, ExtentTest test
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String getElementText(WebDriver oDriver, By objBy, String objectType, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		String strText = null;
		Select oSel = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0) {
				switch(objectType.toLowerCase()) {
					case "text":
						strText = oEles.get(0).getText();
						break;
					case "value":
						strText = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(oEles.get(0));
						strText = oSel.getFirstSelectedOption().getText();
					default:
						reports.writeResult(oDriver, "Fail", "Invalid object type '"+objectType+"' was specified.", resultLocation, test);
						return null;
				}
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to find the Element '"+String.valueOf(objBy)+"' in the DOM.", resultLocation, test);
				return null;
			}
			
			return strText;
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in selectObject() method. "+e.getMessage(), resultLocation, test);
			return null;
		}
		finally {
			oEles = null;
			strText = null;
			oSel = null;
		}
	}
	
	
	
	
	
	/****************************************************************
	 * Method Name	: getElementText
	 * Purpose		: To get the text/value present on the element
	 * Author		: 
	 * Parameters	: WebDriver oDriver, String strObjectName, String objectType, String resultLocation, ExtentTest test
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String getElementText(WebDriver oDriver, String strObjectName, String objectType, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		String strText = null;
		Select oSel = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() > 0) {
				switch(objectType.toLowerCase()) {
					case "text":
						strText = oEles.get(0).getText();
						break;
					case "value":
						strText = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(oEles.get(0));
						strText = oSel.getFirstSelectedOption().getText();
					default:
						reports.writeResult(oDriver, "Fail", "Invalid object type '"+objectType+"' was specified.", resultLocation, test);
						return null;
				}
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to find the Element '"+strObjectName+"' in the DOM.", resultLocation, test);
				return null;
			}
			
			return strText;
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in selectObject() method. "+e.getMessage(), resultLocation, test);
			return null;
		}
		finally {
			oEles = null;
			strText = null;
			oSel = null;
		}
	}
	
	
	
	
	
	/************************************************
	 * Method Name		: javaScriptclickObject()
	 * Purpose			: to click the element using JavaScriptExecutor
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, By objBy, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean javaScriptclickObject(WebDriver oDriver, By objBy, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		JavascriptExecutor js = null;
		try {
			oEles = oDriver.findElements(objBy);
			js = (JavascriptExecutor) oDriver;
			if(oEles.size() > 0) {
				js.executeScript("arguments[0].click();", oEles.get(0));
				reports.writeResult(oDriver, "Pass", "The Element '"+String.valueOf(objBy)+"' was licked successful.", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to click as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in javaScriptclickObject() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: javaScriptclickObject()
	 * Purpose			: to click the element using JavaScriptExecutor
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean javaScriptclickObject(WebDriver oDriver, String strObjectName, String resultLocation, ExtentTest test)
	{
		List<WebElement> oEles = null;
		JavascriptExecutor js = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			js = (JavascriptExecutor) oDriver;
			if(oEles.size() > 0) {
				js.executeScript("arguments[0].click();", oEles.get(0));
				reports.writeResult(oDriver, "Pass", "The Element '"+strObjectName+"' was licked successful.", resultLocation, test);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to click as the Element '"+strObjectName+"' doesnot exist in DOM.", resultLocation, test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in javaScriptclickObject() method. "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
}

package com.gowrisoft.usermgmt.methods;

import org.openqa.selenium.WebDriver;

import com.gowrisoft.usermgmt.driver.DriverScript;
import com.gowrisoft.usermgmt.locators.ObjectLocators;
import com.relevantcodes.extentreports.ExtentTest;


public class AppDependentMethods extends DriverScript implements ObjectLocators{
	/*********************************************
	 * Method Name	: navigateURL()
	 * Purpose		: to navigate the required URL
	 * Author		: 
	 * Parameters	: WebDriver oDriver, String URL, ExtentTest test
	 * Return Type	: boolean
	 * Reviewed By	: 
	 * Date Created	:
	 *********************************************/
	public boolean navigateURL(WebDriver oDriver, String URL, ExtentTest test)
	{
		try {
			oDriver.navigate().to(URL);
			appInd.waitFor(oDriver, obj_Login_btn, "Clickable", "", 5);
			
			if(appInd.compareValues(oDriver, oDriver.getTitle(), "Gowri Soft", test))
			{
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Excception", "Exception in the method navigateURL(). "+e.getMessage(), test);
			return false;
		}
	}
	
	
	
	
	/*********************************************
	 * Method Name	: loginToApp()
	 * Purpose		: to login to the application
	 * Author		: 
	 * Parameters	: WebDriver oDriver, String userName, String password, ExtentTest test
	 * Return Type	: boolean
	 * Reviewed By	: 
	 * Date Created	:
	 *********************************************/
	public boolean loginToApp(WebDriver oDriver, String userName, String password, ExtentTest test)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.setObject(oDriver, obj_Email_Edit, userName, test);
			strStatus+= appInd.setObject(oDriver, obj_Password_Edit, password, test);
			strStatus+= appInd.clickObject(oDriver, obj_Login_btn, test);
			appInd.waitFor(oDriver, obj_SignInSuccessful_Msg, "Visibility", "", 5);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to login to the application", test);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "Login to the application was successful", test);
				reports.writeResult(oDriver, "screenshot", "login was successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method loginToApp(). "+e.getMessage(), test);
			return false;
		}
	}
	
	
	
	
	
	/*********************************************
	 * Method Name	: logoutFromApp()
	 * Purpose		: to logout from the application
	 * Author		: 
	 * Parameters	: WebDriver oDriver, ExtentTest test
	 * Return Type	: boolean
	 * Reviewed By	: 
	 * Date Created	:
	 *********************************************/
	public boolean logoutFromApp(WebDriver oDriver, ExtentTest test)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.clickObject(oDriver, obj_Logout_Link, test);
			appInd.waitFor(oDriver, obj_Login_btn, "Visibility", "", 5);
			
			strStatus+= appInd.verifyElementExist(oDriver, obj_Login_btn, test);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to logout from the application", test);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "Logout from the application was successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method logoutFromApp(). "+e.getMessage(), test);
			return false;
		}
	}
}

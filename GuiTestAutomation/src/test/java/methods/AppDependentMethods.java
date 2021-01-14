package methods;

import org.openqa.selenium.WebDriver;
import driver.DriverScript;
import locators.ObjectLocators;

public class AppDependentMethods extends DriverScript implements ObjectLocators{
	/*********************************************
	 * Method Name	: navigateURL()
	 * Purpose		: to navigate the required URL
	 * Author		: tester1
	 * Parameters	: WebDriver, URL
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean navigateURL(WebDriver oDriver, String URL)
	{
		try {
			oDriver.navigate().to(URL);
			appInd.waitFor(oDriver, obj_Login_btn, "Clickable", "", 5);
			
			if(appInd.compareValues(oDriver, oDriver.getTitle(), "SGSoftwareTesting"))
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
	 * Author		: tester1
	 * Parameters	: WebDriver, UserName, Password
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean loginToApp(WebDriver oDriver, String userName, String password)
	{
		String strStatus = null;
		try {
			test = extent.startTest("loginToApp");
			strStatus+= appInd.setObject(oDriver, obj_Email_Edit, userName);
			strStatus+= appInd.setObject(oDriver, obj_Password_Edit, password);
			strStatus+= appInd.clickObject(oDriver, obj_Login_btn);
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
	 * Author		: tester1
	 * Parameters	: WebDriver
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean logoutFromApp(WebDriver oDriver)
	{
		String strStatus = null;
		try {
			test = extent.startTest("logoutFromApp");
			strStatus+= appInd.clickObject(oDriver, obj_Logout_Link);
			appInd.waitFor(oDriver, obj_Login_btn, "Visibility", "", 5);
			
			strStatus+= appInd.verifyElementExist(oDriver, obj_Login_btn);
			
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

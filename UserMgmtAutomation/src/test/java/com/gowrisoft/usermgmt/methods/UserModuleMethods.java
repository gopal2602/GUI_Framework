package com.gowrisoft.usermgmt.methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gowrisoft.usermgmt.driver.DriverScript;
import com.gowrisoft.usermgmt.locators.ObjectLocators;
import com.relevantcodes.extentreports.ExtentTest;


public class UserModuleMethods extends DriverScript implements ObjectLocators{
	/****************************************************************
	 * Method Name	: createUser
	 * Purpose		: To create the new user
	 * Author		: 
	 * Parameters	: WebDriver oDriver, Map<String, String> objData, ExtentTest test
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String createUser(WebDriver oDriver, Map<String, String> objData, ExtentTest test)
	{
		String strStatus = null;
		String strLastName = null;
		try {
			appInd.waitFor(oDriver, obj_User_Menu_Link, "Clickable", "", 5);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, obj_User_Menu_Link, test));
			appInd.waitFor(oDriver, obj_User_NewUser_Link, "Clickable", "", 5);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, obj_User_NewUser_Link, test));
			appInd.waitFor(oDriver, obj_User_FirstName_Edit, "Visibility", "", 5);
			strLastName = objData.get("TD_LastName")+"_"+appInd.getDateTime("ddMMYYYYhhmmss");
			objData.put("TD_LastName", strLastName);
			
			
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_User_FirstName_Edit, objData.get("TD_FirstName"), test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_User_LastName_Edit, objData.get("TD_LastName"), test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_User_Email_Edit, objData.get("TD_Email"), test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_User_PhoneNumber_Edit, objData.get("TD_PhoneNumber"), test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_User_Address_TextArea, objData.get("TD_Address"), test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_User_State_Select, objData.get("TD_State"), test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_User_Zipcode_Edit, objData.get("TD_ZipCode"), test));
			
			strStatus+=String.valueOf(appInd.clickObject(oDriver, obj_User_CreateUser_Btn, test));
			appInd.waitFor(oDriver, obj_User_Confirmation_Msg, "Visibility", "", 5);
			
			//VErify company created successful
			strStatus+=verifyUserCreatedSuccessful(oDriver, objData, test);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to create the new user", test);
				return null;
			}else {
				reports.writeResult(oDriver, "Pass", "The new user was created & validated successful", test);
				reports.writeResult(oDriver, "screenshot", "The new user was created successful", test);
				return objData.get("TD_LastName");
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method createUser(). "+e.getMessage(), test);
			return null;
		}
		finally
		{
			strStatus = null;
			strLastName = null;
		}
	}
	
	
	
	/****************************************************************
	 * Method Name	: verifyUserCreatedSuccessful
	 * Purpose		: To check that the user is created or not
	 * Author		: 
	 * Parameters	: WebDriver oDriver, Map<String, String> objData, ExtentTest test
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean verifyUserCreatedSuccessful(WebDriver oDriver, Map<String, String> objData, ExtentTest test)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_User_Confirmation_FirstName_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_FirstName"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_User_Confirmation_LastName_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_LastName"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_User_Confirmation_Email_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_Email"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_User_Confirmation_PhoneNumber_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_PhoneNumber"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_User_Confirmation_Address_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_Address"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_User_Confirmation_State_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_State"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_User_Confirmation_ZipCode_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_ZipCode"), test);
			
			if(strStatus.contains("false")) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e){
			reports.writeResult(oDriver, "Exception", "Exception in the method verifyUserCreatedSuccessful(). "+e.getMessage(), test);
			return false;
		}
		finally{
			strStatus = null;
		}
	}
	
	
	
	
	/****************************************************************
	 * Method Name	: deleteUser
	 * Purpose		: To delete the user
	 * Author		: 
	 * Parameters	: WebDriver oDriver, String userName, ExtentTest test
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean deleteUser(WebDriver oDriver, String userName, ExtentTest test)
	{
		String strStatus = null;
		int countBefore = 0;
		int countAfter = 0;
		try {
			strStatus+= appInd.clickObject(oDriver, obj_User_Menu_Link, test);
			appInd.waitFor(oDriver, obj_User_Destroy_Link, "Clickable", "", 10);
			countBefore = oDriver.findElements(By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+userName+"'"+"]")).size();
			
			strStatus+=String.valueOf(appInd.clickObject(oDriver, By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+userName+"'"+"]/following-sibling::td/a[text()='Destroy']"), test));
			Thread.sleep(2000);
			if(appInd.verifyAlertPresent(oDriver)) {
				oDriver.switchTo().alert().accept();
				appInd.waitFor(oDriver, obj_User_Deleted_Confirmation_Msg, "Visibility", "", 10);
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to display the alert while deleting the user", test);
				return false;
			}
			
			countAfter = oDriver.findElements(By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+userName+"'"+"]")).size();
			strStatus+=String.valueOf(appInd.compareValues(oDriver, String.valueOf(countAfter), String.valueOf(countBefore -1), test));
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to delete the user '"+userName+"'", test);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "The user '"+userName+"' was deleted successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method deleteUser(). "+e.getMessage(), test);
			return false;
		}
		finally{
			strStatus = null;
		}
	}
}

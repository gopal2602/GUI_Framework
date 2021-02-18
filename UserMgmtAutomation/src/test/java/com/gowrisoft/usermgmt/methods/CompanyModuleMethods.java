package com.gowrisoft.usermgmt.methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.gowrisoft.usermgmt.driver.DriverScript;
import com.gowrisoft.usermgmt.locators.ObjectLocators;
import com.relevantcodes.extentreports.ExtentTest;

public class CompanyModuleMethods extends DriverScript implements ObjectLocators{
	
	/****************************************************************
	 * Method Name	: createCompany
	 * Purpose		: To create the new compnay
	 * Author		: 
	 * Parameters	: WebDriver oDriver, Map<String, String> objData, String ExtentTest test
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String createCompany(WebDriver oDriver, Map<String, String> objData, ExtentTest test)
	{
		String strStatus = null;
		String strCompanyName = null;
		try {
			appInd.waitFor(oDriver, obj_Company_Menu_Link, "Clickable", "", 5);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, obj_Company_Menu_Link, test));
			appInd.waitFor(oDriver, obj_Company_NewCompany_Link, "Clickable", "", 5);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, obj_Company_NewCompany_Link, test));
			appInd.waitFor(oDriver, obj_Company_Name_Edit, "Visibility", "", 5);
			strCompanyName = objData.get("TD_CompanyName")+"_"+appInd.getDateTime("ddMMYYYYhhmmss");
			objData.put("TD_CompanyName", strCompanyName);
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_Company_Name_Edit, objData.get("TD_CompanyName"), test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_Company_Email_Edit, objData.get("TD_Email"), test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_Company_PhoneNumber_Edit, objData.get("TD_PhoneNumber"), test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_Company_Address_TextArea, objData.get("TD_Address"), test));
			strStatus+=String.valueOf(appInd.clickObject(oDriver, obj_Company_CreateCompany_Btn, test));
			appInd.waitFor(oDriver, obj_Company_Confirmation_Msg, "Visibility", "", 5);
			
			//VErify company created successful
			strStatus+=verifyCompnayCreatedSuccessful(oDriver, objData, test);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to create the new company", test);
				return null;
			}else {
				reports.writeResult(oDriver, "Pass", "The new company was created & validated successful", test);
				reports.writeResult(oDriver, "screenshot", "The new Company was created successful", test);
				return objData.get("TD_CompanyName");
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method createCompany(). "+e.getMessage(), test);
			return null;
		}
		finally
		{
			strStatus = null;
			strCompanyName = null;
		}
	}
	
	
	
	/****************************************************************
	 * Method Name	: verifyCompnayCreatedSuccessful
	 * Purpose		: To check that the company is created or not
	 * Author		: 
	 * Parameters	: WebDriver oDriver, Map<String, String> objData, String ExtentTest test
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean verifyCompnayCreatedSuccessful(WebDriver oDriver, Map<String, String> objData, ExtentTest test)
	{
		String strStatus = null;
		try {
			
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Company_Confirmation_Name_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_CompanyName"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Company_Confirmation_Email_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_Email"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Company_Confirmation_Address_label, "Text", test).split(":"))[1].trim(), objData.get("TD_Address"), test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Company_Confirmation_PhoneNumber_Label, "Text", test).split(":"))[1].trim(), objData.get("TD_PhoneNumber"), test);
			
			if(strStatus.contains("false")) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e){
			reports.writeResult(oDriver, "Exception", "Exception in the method verifyCompnayCreatedSuccessful(). "+e.getMessage(), test);
			return false;
		}
		finally{
			strStatus = null;
		}
	}
	
	
	
	
	/****************************************************************
	 * Method Name	: deleteCompany
	 * Purpose		: To delete the company
	 * Author		: 
	 * Parameters	: WebDriver oDriver, String companyName, String ExtentTest test
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean deleteCompany(WebDriver oDriver, String companyName, ExtentTest test)
	{
		String strStatus = null;
		int countBefore = 0;
		int countAfter = 0;
		try {
			appInd.waitFor(oDriver, obj_Company_Menu_Link, "Clickable", "", 10);
			strStatus+= appInd.clickObject(oDriver, obj_Company_Menu_Link, test);
			appInd.waitFor(oDriver, obj_Company_Destroy_Link, "Clickable", "", 10);
			countBefore = oDriver.findElements(By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+companyName+"'"+"]")).size();
			
			strStatus+=String.valueOf(appInd.clickObject(oDriver, By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+companyName+"'"+"]/following-sibling::td/a[text()='Destroy']"), test));
			Thread.sleep(2000);
			if(appInd.verifyAlertPresent(oDriver)) {
				oDriver.switchTo().alert().accept();
				appInd.waitFor(oDriver, obj_Company_Deleted_Confirmation_Msg, "Visibility", "", 10);
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to display the alert while deleting the company", test);
				return false;
			}
			
			countAfter = oDriver.findElements(By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+companyName+"'"+"]")).size();
			strStatus+=String.valueOf(appInd.compareValues(oDriver, String.valueOf(countAfter), String.valueOf(countBefore -1), test));
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to delete the company '"+companyName+"'", test);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "The company '"+companyName+"' was deleted successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method deleteCompany(). "+e.getMessage(), test);
			return false;
		}
		finally{
			strStatus = null;
		}
	}
}

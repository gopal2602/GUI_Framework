package com.gowrisoft.usermgmt.driver;

import java.lang.reflect.Method;
import java.util.Hashtable;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.gowrisoft.usermgmt.methods.AppDependentMethods;
import com.gowrisoft.usermgmt.methods.AppIndependentMethods;
import com.gowrisoft.usermgmt.methods.CompanyModuleMethods;
import com.gowrisoft.usermgmt.methods.Datatable;
import com.gowrisoft.usermgmt.methods.ProjectModuleMethods;
import com.gowrisoft.usermgmt.methods.UserModuleMethods;
import com.gowrisoft.usermgmt.reports.ReportUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static ReportUtils reports = null;	
	public static UserModuleMethods userModule = null;
	public static CompanyModuleMethods companyModule = null;
	public static ProjectModuleMethods projectModule = null;
	public static String controller = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String screenshotLocation = null;
	public static String moduleName = null;
	
	@BeforeSuite
	public void loadClasses() {
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			datatable = new Datatable();
			reports = new ReportUtils();
			userModule = new UserModuleMethods();
			companyModule = new CompanyModuleMethods();
			projectModule = new ProjectModuleMethods();
			controller = System.getProperty("user.dir")+"\\ExecutionController\\RunnerFile.xlsx";
			extent = reports.startExtentReport("GUITestResults", appInd.readPropData("BuildNumber"));
		}catch(Exception e)
		{
			System.out.println("Exception in loadClasses() method. "+e.getMessage());
		}
	}
	
	
	
	@DataProvider(name = "ExecutionController", parallel = true)
	public Object[][] getDataProvider() throws Exception{
		Object[][] data = datatable.createDataProvider("RunnerFile", "ExecuteTest");
		return data;
	}
	
	
	@Test(dataProvider = "ExecutionController")
	public void executeTest(Hashtable<String,String> objData)
	{
		Class cls = null;
		Object obj = null;
		Method meth = null;
		String status = null;
		String strTestCaseID = null;
		Class resultInfo[] = null;
		try {			
			resultInfo = new Class[2];
			resultInfo[0] = String.class;
			resultInfo[1] = String.class;
			
			
			cls = Class.forName(objData.get("ClassName"));
			obj = cls.newInstance();
			meth = obj.getClass().getMethod(objData.get("TestScriptName"), resultInfo);
			strTestCaseID = objData.get("TestCaseID");
			moduleName = objData.get("ModuleName");
			
			status = String.valueOf(meth.invoke(obj, moduleName, strTestCaseID));
			
			if(status.equals("true")) {
				datatable.setCellData(controller, "ExecuteTest", "Status", objData.get("TestScriptName"), "Passed");
			}else {
				datatable.setCellData(controller, "ExecuteTest", "Status", objData.get("TestScriptName"), "Failed");
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		finally {
			cls = null;
			obj = null;
			meth = null;
		}
	}
}

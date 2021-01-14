package driver;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import methods.AppDependentMethods;
import methods.AppIndependentMethods;
import methods.CompanyModuleMethods;
import methods.Datatable;
import methods.ProjectModuleMethods;
import methods.UserModuleMethods;
import reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static ReportUtils reports = null;
	public static String strResultLocation = null;
	public static String strScreenshotLocation = null;
	public static String strModuleName = null;
	public static String strTestCaseID = null;
	public static UserModuleMethods userModule = null;
	public static CompanyModuleMethods companyModule = null;
	public static ProjectModuleMethods projectModule = null;
	public static String controller = null;
	
	
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
		Hashtable<String, String> data = null;
		String status = null;
		int count = 0;
		try {
			cls = Class.forName(objData.get("ClassName"));
			obj = cls.newInstance();
			meth = obj.getClass().getMethod(objData.get("TestScriptName"));
			strTestCaseID = objData.get("TestCaseID");
			strModuleName = objData.get("ModuleName");
			
			status = String.valueOf(meth.invoke(obj));
			
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
			data = null;
		}
	}
}

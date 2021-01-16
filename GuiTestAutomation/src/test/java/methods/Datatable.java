package methods;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.relevantcodes.extentreports.ExtentTest;

import driver.DriverScript;

public class Datatable extends DriverScript{
	/************************************************
	 * Method Name		: getRowNumber()
	 * Purpose			: to get the row number form the excel file
	 * Author			:
	 * Reviewer			:
	 * Arguments		: String filePath, String sheetName, String resultLocation, ExtentTest test
	 * Return type		: int
	 * Date Created		: 
	 * **********************************************
	 */
	public int getRowNumber(String filePath, String sheetName, String resultLocation, ExtentTest test)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		int rowCount = 0;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist", resultLocation, test);
				return -1;
			}
			
			rowCount = sh.getPhysicalNumberOfRows()-1;
			return rowCount;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getRowNumber() method. "+e.getMessage(), resultLocation, test);
			return -1;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getRowNumber() method. "+e.getMessage(), resultLocation, test);
			}
		}
	}
	
	
	
	
	
	
	/************************************************
	 * Method Name		: getCellData()
	 * Purpose			: to read the cell data form the excel file
	 * Author			:
	 * Reviewer			:
	 * Arguments		: String filePath, String sheetName, String colName, int rowNum, String resultLocation, ExtentTest test
	 * Return type		: String
	 * Date Created		: 
	 * **********************************************
	 */
	public String getCellData(String filePath, String sheetName, String colName, int rowNum, String resultLocation, ExtentTest test)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		String strData = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist", resultLocation, test);
				return null;
			}
			
			//Find out the column number based on the column name
			row = sh.getRow(0);
			for(int c=0; c<row.getPhysicalNumberOfCells(); c++)
			{
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(colName))
				{
					colNum = c;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			if(cell==null || cell.getCellType()==CellType.BLANK)
			{
				strData = "";
			}else if(cell.getCellType()==CellType.BOOLEAN) {
				strData = String.valueOf(cell.getBooleanCellValue());
			}else if(cell.getCellType()==CellType.STRING)
			{
				strData = cell.getStringCellValue();
			}else if(cell.getCellType()==CellType.NUMERIC)
			{
				//Validate the cell comtain date
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(dt));
					
					//If day is less than 10 then prefix zero
					if(cal.get(Calendar.DAY_OF_MONTH)<10) {
						sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
					}else {
						sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}
					
					
					//If month is less than 10 then prefix zero
					if(cal.get(Calendar.DAY_OF_MONTH)<10) {
						sMonth = "0" + (cal.get(Calendar.MONTH)+1);
					}else {
						sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
					}
					
					sYear = String.valueOf(cal.get(Calendar.YEAR));
					strData = sDay +"-"+ sMonth +"-"+ sYear;
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
			return strData;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getCellData() method. "+e.getMessage(), resultLocation, test);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getCellData() method. "+e.getMessage(), resultLocation, test);
				return null;
			}
		}
	}
	
	
	
	
	
	/************************************************
	 * Method Name		: setCellData()
	 * Purpose			: write to the excel cell
	 * Author			:
	 * Reviewer			:
	 * Arguments		: String filePath, String sheetName, String colName, String logicalName, String strData
	 * Return type		: String
	 * Date Created		: 
	 * **********************************************
	 */
	public void setCellData(String filePath, String sheetName, String colName, String logicalName, String strData)
	{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		int rowNum = 0;
		CellStyle style = null;
		Font font = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				System.out.println("The sheet '"+sheetName+"' doesnot exist. Hence cannot continue setCellData() method");
				return;
			}
			
			//Find out the column number based on the column name
			row = sh.getRow(0);
			for(int c=0; c<row.getPhysicalNumberOfCells(); c++)
			{
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(colName))
				{
					colNum = c;
					break;
				}
			}
			
			
			//Find the row number based on the TestScriptName
			for(int r=0; r<sh.getPhysicalNumberOfRows(); r++)
			{
				row = sh.getRow(r);
				cell = row.getCell(3);
				if(cell.getStringCellValue().trim().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			style = wb.createCellStyle();
			font = wb.createFont();
			if(strData.equalsIgnoreCase("Passed")) {
				style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
				style.setFillPattern(FillPatternType.LEAST_DOTS);
				font.setBold(true);
				style.setFont(font);
			}else if(strData.equalsIgnoreCase("Failed")) {
				style.setFillBackgroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(FillPatternType.LEAST_DOTS);
				font.setBold(true);
				style.setFont(font);
			}else if(strData.equalsIgnoreCase("Skipped")) {
				style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
				style.setFillPattern(FillPatternType.LEAST_DOTS);
				font.setBold(true);
				style.setFont(font);
			}
			
			
			if(row.getCell(colNum)==null) {
				cell = row.createCell(colNum);
			}
			
			cell.setCellValue(strData);
			cell.setCellStyle(style);
			fout = new FileOutputStream(filePath);
			wb.write(fout);
			
		}catch(Exception e)
		{
			System.out.println("Exception in setCellData() method. "+e.getMessage());
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				System.out.println("Exception in setCellData() method. "+e.getMessage());
			}
		}
	}
	
	
	
	
	
	
	/************************************************
	 * Method Name		: getExcelTestData()
	 * Purpose			: to read the data from the excel based on logicalName
	 * Author			:
	 * Reviewer			:
	 * Arguments		: String sheetName, String logicalName, String strModuleName, String resultLocation, ExtentTest test
	 * Return type		: String
	 * Date Created		: 
	 * **********************************************
	 */
	public Map<String, String> getExcelTestData(String sheetName, String logicalName, String strModuleName, String resultLocation, ExtentTest test)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		int rowNum = 0;
		String key = null;
		String value = null;
		String sDay = null;
		String sMonth = null;
		String sYears = null;
		Map<String, String> objData = null;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\"+strModuleName+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist. Hence can't read the test data", resultLocation, test);
				return null;
			}
			
			//Find the row number of the given logical name
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rows; r++)
			{
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().equals(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			
			//If logical name is present then read the data from the row
			//and take the keys from the columns and read a MAP
			if(rowNum>0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				for(int c=0; c<row1.getPhysicalNumberOfCells(); c++)
				{
					cell1 = row1.getCell(c);
					cell2 = row2.getCell(c);
					key = cell1.getStringCellValue();
					
					//Foramt and read the test data
					if(cell2==null || cell2.getCellType()==CellType.BLANK)
					{
						value = "";
					}else if(cell2.getCellType()==CellType.BOOLEAN) {
						value = String.valueOf(cell2.getBooleanCellValue());
					}else if(cell2.getCellType()==CellType.STRING)
					{
						value = cell2.getStringCellValue();
					}else if(cell2.getCellType()==CellType.NUMERIC)
					{
						//Validate the cell comtain date
						if(HSSFDateUtil.isCellDateFormatted(cell2))
						{
							double dt = cell2.getNumericCellValue();
							Calendar cal = Calendar.getInstance();
							cal.setTime(HSSFDateUtil.getJavaDate(dt));
							
							//If day is less than 10 then prefix zero
							if(cal.get(Calendar.DAY_OF_MONTH)<10) {
								sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							
							//If month is less than 10 then prefix zero
							if(cal.get(Calendar.DAY_OF_MONTH)<10) {
								sMonth = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							sYears = String.valueOf(cal.get(Calendar.YEAR));
							value = sDay +"-"+ sMonth +"-"+ sYears;
						}else {
							value = String.valueOf(cell2.getNumericCellValue());
						}
					}
					objData.put(key, value);
				}
				reports.writeResult(null, "Pass", "The testdata was read from the '"+sheetName+"' sheet under '"+strModuleName+".xlsx"+"' file", resultLocation, test);
				return objData;
			}else {
				reports.writeResult(null, "Fail", "Failed to find the logical name '"+logicalName+"' in the test data sheet", resultLocation, test);
				return null;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getExcelData() method. "+e.getMessage(), resultLocation, test);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getExcelData() method. "+e.getMessage(), resultLocation, test);
				return null;
			}
		}
	}
	
	
	
	
	/*********************************************
	 * Method Name	: createDataProvider()
	 * Author		: 
	 * Purpose		: to read data from excel and create a dataProvider
	 * Arguments	: String strFileName, String strSheetName
	 * Date created	: 
	 * 
	 * *******************************************
	 */
	public Object[][] createDataProvider(String strFileName, String strSheetName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int rowCount = 0;
		int colCount = 0;
		int executionCount = 0;
		Object[][] data = null;
		List<String> colNames = null;
		int actualRows = 0;
		Hashtable<String, String> cellData = null;
		String strValue = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir")+"\\ExecutionController\\"+strFileName+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(strSheetName);
			
			if(sh == null) {
				System.out.println("The sheet '"+strSheetName+"' doesnot exist. Hence can't read the excel data");
				return null;
			}
			rowCount = sh.getPhysicalNumberOfRows();
			row = sh.getRow(0);
			colCount = row.getPhysicalNumberOfCells();
			
			//Loop no. of rows to find the count of test cases selected for execution
			for(int rows = 0; rows<rowCount; rows++)
			{
				row = sh.getRow(rows);
				cell = row.getCell(5);
				if(cell.getStringCellValue().trim().equalsIgnoreCase("Yes")) {
					executionCount++;
				}
			}
			
			
			data = new Object[executionCount][1];
			colNames= new ArrayList<String>();

			for(int col=0; col<colCount; col++) 
			{
				row = sh.getRow(0);
				cell = row.getCell(col);
				colNames.add(col, cell.getStringCellValue());
			}
			
			
			for(int r=1; r<rowCount; r++)
			{
				row = sh.getRow(r);
				cell = row.getCell(5);
				if(cell.getStringCellValue().equalsIgnoreCase("Yes"))
				{
					cellData = new Hashtable<String,String>();
					for(int col=0;col<colCount;col++)
					{
						cell = row.getCell(col);
						if(cell==null || cell.getCellTypeEnum()==CellType.BLANK)
						{
							strValue = "";
						}else if(cell.getCellTypeEnum()==CellType.BOOLEAN)
						{
							strValue = String.valueOf(cell.getBooleanCellValue());
						}else if(cell.getCellTypeEnum()==CellType.STRING)
						{
							strValue = cell.getStringCellValue();
						}else if(cell.getCellTypeEnum()==CellType.NUMERIC)
						{
							if(HSSFDateUtil.isCellDateFormatted(cell)) {
								double dt = cell.getNumericCellValue();
								Calendar cal = Calendar.getInstance();
								cal.setTime(HSSFDateUtil.getJavaDate(dt));
								
								//Prefix zero if day is <10
								if(cal.get(Calendar.DAY_OF_MONTH)<10) {
									sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
								}else {
									sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
								}
								
								//Prefix zero if month is <10
								if((cal.get(Calendar.MONTH)+1)<10) {
									sMonth = "0" + (cal.get(Calendar.MONTH)+1);
								}else {
									sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
								}
								
								sYear = String.valueOf(cal.get(Calendar.YEAR));
								strValue = sDay+"-"+sMonth+"-"+sYear;
							}else {
								strValue = String.valueOf(cell.getNumericCellValue());
							}
							strValue = cell.getStringCellValue();
						}
						cellData.put(colNames.get(col), strValue);

					}
					data[actualRows][0] = cellData;
					actualRows++;
				}
			}
			System.out.println("DataProvider has been created and returned successful");
			return data;
		}catch(Exception e)
		{
			System.out.println("Exception while executing 'createDataProvider()' method. "+e.getMessage());
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
				//colNames = null;
				//cellData = null;
			}catch(Exception e)
			{
				System.out.println("Exception while executing 'createDataProvider()' method. "+e.getMessage());
			}
		}
	}
}

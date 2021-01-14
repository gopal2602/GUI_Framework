package locators;

public interface ObjectLocators {
	
	//************************************************************************************
	//			Login Page Locators
	//************************************************************************************
	final String obj_Email_Edit = "//input[@id='student_email']";
	final String obj_Password_Edit = "//input[@id='student_password']";
	final String obj_Login_btn = "//input[@value='Log in']";
	final String obj_Logout_Link = "//a[text()='Logout']";
	
	
	
	//************************************************************************************
	//			Home Page Locators
	//************************************************************************************
	final String obj_RememberMe_Chk = "//input[@id='student_remember_me']";
	final String obj_SignInSuccessful_Msg = "//div[@class='middle']/h1[contains(text(), 'Welcome To')]";
	final String obj_Company_Menu_Link = "//a[text()='Companies']";
	final String obj_Project_Menu_Link = "//a[text()='Projects']";
	final String obj_User_Menu_Link = "//a[text()='Users']";
	final String obj_Home_Menu_Link = "//a[text()='Home']";
	
	
	
	
	//************************************************************************************
	//			Project Page Locators
	//************************************************************************************
	final String obj_Project_NewProject_Link = "//a[text()='New Project']";
	final String obj_Project_Destroy_Link = "//a[text()='Destroy']";
	final String obj_Project_Company_Select = "//select[@id='project_company_id']";
	final String obj_Project_Name_Edit = "//input[@id='project_name']";
	final String obj_Project_TeamSize_Edit = "//input[@id='project_team_size']";
	final String obj_Project_StartDate_Years_Select = "//select[@id='project_start_date_1i']";
	final String obj_Project_StartDate_Months_Select = "//select[@id='project_start_date_2i']";
	final String obj_Project_StartDate_Days_Select = "//select[@id='project_start_date_3i']";
	final String obj_Project_StartDate_Hours_Select = "//select[@id='project_start_date_4i']";
	final String obj_Project_StartDate_Minutes_Select = "//select[@id='project_start_date_5i']";
	final String obj_Project_EndDate_Years_Select = "//select[@id='project_end_date_1i']";
	final String obj_Project_EndDate_Months_Select = "//select[@id='project_end_date_2i']";
	final String obj_Project_EndDate_Days_Select = "//select[@id='project_end_date_3i']";
	final String obj_Project_EndDate_Hours_Select = "//select[@id='project_end_date_4i']";
	final String obj_Project_EndDate_Minutes_Select = "//select[@id='project_end_date_5i']";
	final String obj_Project_Description_TextArea = "//textarea[@id='project_description']";
	final String obj_CreateProject_Btn = "//input[@value='Create Project']";
	final String obj_Project_Confirmation_Msg = "//div/p[contains(text(), 'Project was successfully created')]";
	final String obj_Project_Confirmation_Name_Label = "//strong[text()='Name:']/parent::p";
	final String obj_Project_Confirmation_TeamSize_Label = "//strong[text()='Team size:']/parent::p";
	final String obj_Project_Confirmation_StartDate_Label = "//strong[text()='Start date:']/parent::p";
	final String obj_Project_Confirmation_EndDate_Label = "//strong[text()='End date:']/parent::p";
	final String obj_Project_Confirmation_Description_Label = "//strong[text()='Description:']/parent::p";
	final String obj_Project_Confirmation_Company_Label = "//strong[text()='Company:']/parent::p";
	final String obj_Project_Deleted_Confirmation_Msg = "//p[contains(text(), 'Project was successfully destroyed')]";
	


	//************************************************************************************
	//			Company Page Locators
	//************************************************************************************
	final String obj_Company_NewCompany_Link = "//a[text()='New Company']";
	final String obj_Company_Destroy_Link = "//a[text()='Destroy']";
	final String obj_Company_Name_Edit = "//input[@id='company_name']";
	final String obj_Company_Email_Edit = "//input[@id='company_email']";
	final String obj_Company_PhoneNumber_Edit = "//input[@id='company_phone_number']";
	final String obj_Company_Address_TextArea = "//textarea[@id='company_address']";
	final String obj_Company_CreateCompany_Btn = "//input[@value='Create Company']";
	final String obj_Company_Confirmation_Msg = "//div/p[contains(text(), 'Company was successfully created')]";
	final String obj_Company_Confirmation_Name_Label = "//strong[text()='Name:']/parent::p";
	final String obj_Company_Confirmation_Email_Label = "//strong[text()='Email:']/parent::p";
	final String obj_Company_Confirmation_Address_label = "//strong[text()='Addess:']/parent::p";
	final String obj_Company_Confirmation_PhoneNumber_Label = "//strong[text()='Phone number:']/parent::p";
	final String obj_Company_Deleted_Confirmation_Msg = "//p[contains(text(), 'Company was successfully destroyed')]";
	
	
	
	
	//************************************************************************************
	//			User Page Locators
	//************************************************************************************
	final String obj_User_NewUser_Link = "//a[text()='New User']";
	final String obj_User_Export_Link = "//a[text()='Export']";
	final String obj_User_Import_Btn = "//input[@value='Import']";
	final String obj_User_FirstName_Edit = "//input[@id='user_first_name']";
	final String obj_User_LastName_Edit = "//input[@id='user_last_name']";
	final String obj_User_Email_Edit = "//input[@id='user_email']";
	final String obj_User_PhoneNumber_Edit = "//input[@id='user_phone_number']";
	final String obj_User_Address_TextArea = "//textarea[@id='user_address']";
	final String obj_User_State_Select = "//select[@id='user_state']";
	final String obj_User_Zipcode_Edit = "//input[@id='user_zipcode']";
	final String obj_User_CreateUser_Btn = "//input[@value='Create User']";
	final String obj_User_Confirmation_Msg = "//p[contains(text(), 'User was successfully created')]";
	final String obj_User_Confirmation_FirstName_Label = "//strong[text()='First name:']/parent::p";
	final String obj_User_Confirmation_LastName_Label = "//strong[text()='Last name:']/parent::p";
	final String obj_User_Confirmation_Email_Label = "//strong[text()='Email:']/parent::p";
	final String obj_User_Confirmation_PhoneNumber_Label = "//strong[text()='Phone number:']/parent::p";
	final String obj_User_Confirmation_Address_Label = "//strong[text()='Address:']/parent::p";
	final String obj_User_Confirmation_State_Label = "//strong[text()='State:']/parent::p";
	final String obj_User_Confirmation_ZipCode_Label = "//strong[text()='Zipcode:']/parent::p";
	final String obj_User_Destroy_Link = "//a[text()='Destroy']";
	final String obj_User_Deleted_Confirmation_Msg = "//p[contains(text(), 'User was successfully destroyed')]";
	
	
	
	
}

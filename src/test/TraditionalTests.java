package test;

import main.ReusableActions;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class TraditionalTests {
    ReusableActions ra;
    String url = "https://demo.applitools.com/hackathonV2.html";

    @Test
    public void validateAllElementsOnLoginPage(ITestContext context) {
        ra.navigate(url);
        ra.displayedMultipleElements(new String[]{"login.username", "login.password", "login.login_button", "login.login_form_title", "login.username_label", "login.password_label", "login.person_icon", "login.finger_print_icon", "login.remember_me", "login.twitter_image", "login.facebook_image", "login.linkedin_image"});
    }


    @Test
    public void loginNoUserPass(ITestContext context) {
        ra.navigate(url);
        ra.click("login.login_button");
        ra.displayedWithText("login.login_message", "Both Username and Password must be present");

    }

    @Test
    public void loginNoPass(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.username","honora");
        ra.click("login.login_button");
        ra.displayedWithText("login.login_message", "Password must be present");

    }

    @Test
    public void loginNoUser(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.displayedWithText("login.login_message", "Username must be present");
    }

    @Test
    public void loginCorrect(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.username","honora");
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.displayed("home.financial_overview");
    }



    @Test
    public void tableSort(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.username","honora");
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.displayed("home.financial_overview");
        ra.click("home.amount_column_header");
        ra.verifElementsInAscendingOrder("home.amount_column_values");
    }

    @Test
    public void canvasChartValidations(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.username","honora");
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.displayed("home.financial_overview");
        ra.click("home.compare_expenses_link");
        ra.validateChartTotalNumberBars("expense_chart.expenses_chart_script", 14);
        ra.validateChart2017Heights("expense_chart.expenses_chart_script", "10,20,30,40,50,60,70");
        ra.validateChart2018Heights("expense_chart.expenses_chart_script", "8,9,-10,10,40,60,40");
        ra.click("expense_chart.add_next_year");
        /*
        Cant validate 2019 data without assistance from another tool like sikuli that allows analyzing images. Properties not availible in html.
         */

    }


    @Test
    public void dynamicAd(ITestContext context){
        ra.navigate(url + "?showAd=true");
        ra.enterText("login.username","honora");
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.displayed("home.financial_overview");
        ra.elementsDisplayed("home.flash_sale_ads", 2);
    }

    @BeforeMethod
    public void setUp(ITestContext context){
        ra = new ReusableActions();
    }

    @AfterMethod
    public void tearDown(ITestContext context){
        ra.closeBrowser();
    }

}

package test;

import com.applitools.eyes.selenium.Eyes;
import main.ReusableActions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VisualAITests {
    ReusableActions ra;
    String url = "https://demo.applitools.com/hackathonV2.html";

    @Test
    public void validateAllElementsOnLoginPage(){
        ra.navigate(url);
        ra.checkWindow("Demo Applitools Hackathon", "validateAllElementsOnLoginPage");
    }


    @Test
    public void loginNoUserPass(ITestContext context) {
        ra.navigate(url);
        ra.click("login.login_button");
        ra.checkWindowContent("Demo Applitools Hackathon", "loginNoUserPass");

    }

    @Test
    public void loginNoPass(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.username","honora");
        ra.click("login.login_button");
        ra.checkWindowContent("Demo Applitools Hackathon", "loginNoPass");

    }

    @Test
    public void loginNoUser(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.checkWindowContent("Demo Applitools Hackathon", "loginNoUser");
    }

    @Test
    public void loginCorrect(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.username","honora");
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.checkWindowContent("Demo Applitools Hackathon", "loginCorrect");
    }

    @Test
    public void tableSort(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.username","honora");
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.displayed("home.financial_overview");
        ra.click("home.amount_column_header");
        ra.checkWindowContent("Demo Applitools Hackathon", "tableSort");
    }

    @Test
    public void canvasChartValidations(ITestContext context){
        ra.navigate(url);
        ra.enterText("login.username","honora");
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.displayed("home.financial_overview");
        ra.click("home.compare_expenses_link");
        ra.checkWindowContent("Demo Applitools Hackathon", "2017 & 2018 data");
        ra.click("expense_chart.add_next_year");
        ra.checkWindowContent("Demo Applitools Hackathon", "2019 data");
    }


    @Test
    public void dynamicAd(ITestContext context){
        ra.navigate(url + "?showAd=true");
        ra.enterText("login.username","honora");
        ra.enterText("login.password","honora");
        ra.click("login.login_button");
        ra.displayed("home.financial_overview");
        ra.checkWindowLayout("Demo Applitools Hackathon", "dynamicAd");
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

package main;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class DriverUtil {

    public WebDriver createDriver(String browserType) {

        if (browserType.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\webdrivers\\chromedriver.exe");
            return new FirefoxDriver();
        }
        else if (browserType.equalsIgnoreCase("IE")) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\webdrivers\\chromedriver.exe");
            return new InternetExplorerDriver();
        }
        else
        {
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("useAutomationExtension", false);
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\webdrivers\\chromedriver.exe");
            return new ChromeDriver(options);

        }

    }

    public FluentWait<WebDriver> createWait(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.ignoring(NoSuchElementException.class).ignoring(JavaScriptException.class).ignoring(StaleElementReferenceException.class);
    }
}

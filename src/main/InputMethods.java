package main;

import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.selenium.Eyes;
import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class InputMethods {

    public WebDriver getDriver() {
        return driver;
    }

    WebDriver driver;
    FluentWait<WebDriver> wait;
    DriverUtil du;

    public InputMethods(){
        if(driver == null || ((RemoteWebDriver) driver).getSessionId()==null){
            du = new DriverUtil();
            driver = du.createDriver("chrome");
            wait = du.createWait(driver);
            driver.manage().window().maximize();
        }
        if(eyes == null){
            eyes = new Eyes();
            eyes.setApiKey("DWB6AJQHJ633EQfuMTdkQWmTemzWwOqSMtUJ5j105qL1c110");
        }
    }

    public void navigate(String url){
        driver.get(url);
    }

    public void click(String accessType, String accessName) {
        wait.until(ExpectedConditions.elementToBeClickable(getElementByAttributes(accessType, accessName)));
        WebElement element = driver.findElement(getElementByAttributes(accessType, accessName));
        element.click();
    }

    public void enterText(String accessType, String accessName, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(getElementByAttributes(accessType, accessName)));
        WebElement element = driver.findElement(getElementByAttributes(accessType, accessName));
        element.sendKeys(text);
    }


    public By getElementByAttributes(String accessType, String accessName) {

        By by;
        switch (accessType.toLowerCase()) {
            case "id":
                by = By.id(accessName);
                break;
            case "name":
                by = By.name(accessName);
                break;
            case "class":
                by = By.className(accessName);
                break;
            case "xpath":
                by = By.xpath(accessName);
                break;
            case "css":
                by = By.cssSelector(accessName);
                break;
            case "linktext":
                by = By.linkText(accessName);
                break;
            case "partiallinktext":
                by = By.partialLinkText(accessName);
                break;
            case "tagname":
                by = By.tagName(accessName);
                break;
            default:
                by = null;

        }
        return by;
    }

    public void closerBrowser() {
        driver.quit();
        eyes.abortIfNotClosed();
    }

    public void displayed(String element, String accessType, String accessName) {
        boolean isDisplayed;
        try {
            isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(getElementByAttributes(accessType, accessName))).isDisplayed();
        }
        catch (TimeoutException e){
            isDisplayed = false;
        }
        Assert.assertTrue(isDisplayed, element + " is displayed");
    }

    public void displayedWithText(String element, String accessType, String accessName, String text) {
        boolean isDisplayedWithText;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(getElementByAttributes(accessType, accessName)));
            String elementText = wait.until(ExpectedConditions.visibilityOfElementLocated(getElementByAttributes(accessType, accessName))).getText().trim();
            Boolean checkText = elementText.equals(text);
            Assert.assertTrue(checkText, element + " is displayed with text "+ elementText);
        }
        catch (TimeoutException e){
            isDisplayedWithText = false;
            Assert.assertTrue(isDisplayedWithText, element + " is not displayed");
        }

    }

    public void displayedMultipleElements(String[] elements, LinkedHashMap<String, LinkedHashMap<String, String>> elementProperties) {

        ArrayList<String> notFoundElements = new ArrayList<>();
        ArrayList<String> foundElements = new ArrayList<>();

        for (int i = 0; i < elements.length; i++){
            String element = elements[i];
            String accessType = elementProperties.get(element).get("accessType");
            String accessName = elementProperties.get(element).get("accessName");
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(getElementByAttributes(accessType, accessName))).isDisplayed();
                foundElements.add(elements[i]);
            }
            catch (TimeoutException e){
                e.printStackTrace();
                notFoundElements.add(elements[i]);
            }
        }
        Boolean allElementsFound = true;
        if (notFoundElements.size() > 0){
            allElementsFound = false;
        }
        if(allElementsFound == false) {
            Assert.assertTrue(allElementsFound, notFoundElements.toString() + " displayed");
        }
        else{
            Assert.assertTrue(allElementsFound, foundElements.toString() + " displayed");
        }
    }

    public void displayedMultipleElementsWithText(String[] elements, LinkedHashMap<String, LinkedHashMap<String, String>> elementProperties, String[] texts) {

        ArrayList<String> notFoundElements = new ArrayList<>();
        ArrayList<String> foundElements = new ArrayList<>();

        for (int i = 0; i < elements.length; i++){
            String element = elements[i];
            String accessType = elementProperties.get(element).get("accessType");
            String accessName = elementProperties.get(element).get("accessName");
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(getElementByAttributes(accessType, accessName))).isDisplayed();
                foundElements.add(elements[i]);
            }
            catch (TimeoutException e){
                e.printStackTrace();
                notFoundElements.add(elements[i]);
            }
        }
        Boolean allElementsFound = true;
        if (notFoundElements.size() > 0){
            allElementsFound = false;
        }
        if(allElementsFound == false) {
            Assert.assertTrue(allElementsFound, notFoundElements.toString() + " displayed");
        }
        else{
            Assert.assertTrue(allElementsFound, foundElements.toString() + " displayed");
        }
    }

    public void verifElementsInAscendingOrder(String accessType, String accessName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(getElementByAttributes(accessType, accessName)));
        List<WebElement> elements = driver.findElements(getElementByAttributes(accessType, accessName));
        List<Double> values = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++){
            String text = elements.get(i).getText();
            text = text.replace(" ", "");
            text = text.replace("USD", "");
            text = text.replace("+", "");
            text = text.replace(",", "");
            double d = Double.parseDouble(text);
            values.add(d);
        }
        Boolean listInAscendingOrder=true;
        for(int i = 0; i <values.size()-1; i++){
            if(values.get(i)>values.get(i+1)){
                listInAscendingOrder=false;
            }
        }
        Assert.assertTrue(listInAscendingOrder, values.toString() + " are in ascending order");
    }

    public void validateChartMonths(String accessType, String accessName, String months) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(getElementByAttributes(accessType, accessName)));
        String script = element.getAttribute("innerHTML");
        String requiredString = script.substring(script.indexOf("MONTHS = [") + 10, script.indexOf("]"));
        Boolean validateMonths = requiredString.equals(months);
        Assert.assertTrue(validateMonths, "Table has months: " + months);
    }

    public void validateChart2018Heights(String accessType, String accessName, String heights) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(getElementByAttributes(accessType, accessName)));
        String script = element.getAttribute("innerHTML");
        script = script.replaceAll("[\\n\\t\\r]", "");
        String requiredString = script.substring(script.indexOf("Colors.blue,borderWidth: 1,data: [") + 34, script.indexOf("]}]};window"));
        requiredString = requiredString.replaceAll(" ", "");
        Boolean validateMonthHeights = requiredString.equals(heights);
        Assert.assertTrue(validateMonthHeights, "Table 2018 month heights, " + requiredString + ", equal " + heights);
    }

    public void validateChart2017Heights(String accessType, String accessName, String heights) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(getElementByAttributes(accessType, accessName)));
        String script = element.getAttribute("innerHTML");
        script = script.replaceAll("[\\n\\t\\r]", "");
        String requiredString = script.substring(script.indexOf("Colors.red,borderWidth: 1,data: [") + 33, script.indexOf("]}, {label: '2018"));
        requiredString = requiredString.replaceAll(" ", "");
        Boolean validateMonthHeights = requiredString.equals(heights);
        Assert.assertTrue(validateMonthHeights, "Table 2017 month heights, " + requiredString + ", equal " + heights);
    }

    public void validateChartTotalNumberBars(String accessType, String accessName, int bars) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(getElementByAttributes(accessType, accessName)));
        String script = element.getAttribute("innerHTML");
        script = script.replaceAll("[\\n\\t\\r]", "");
        String requiredString = script.substring(script.indexOf("Colors.red,borderWidth: 1,data: [") + 33, script.indexOf("]}, {label: '2018"));
        String requiredString2 = script.substring(script.indexOf("Colors.blue,borderWidth: 1,data: [") + 34, script.indexOf("]}]};window"));
        String allbars = requiredString + ", " + requiredString2;
        String[] parts = allbars.split(",");
        Boolean numberOfBars = parts.length == bars;
        Assert.assertTrue(numberOfBars, "Number of bars in table, " + parts.length + ", equals " + bars);
    }

    public void elementsDisplayed(String accessType, String accessName, int numberOfElements) {
        List<WebElement> elements = driver.findElements(getElementByAttributes(accessType, accessName));
        Boolean size = elements.size() == numberOfElements;
        Assert.assertTrue(size, "The number of elements present, " + elements.size() + ", is " + numberOfElements);
    }

    Eyes eyes;

    public void checkWindow(String appName, String test) {
        eyes.open(driver, appName, test);
        eyes.setForceFullPageScreenshot(true);
        eyes.checkWindow();
        eyes.close();
    }

    public void checkWindowContent(String appName, String test) {
        eyes.open(driver, appName, test);
        eyes.setMatchLevel(MatchLevel.CONTENT);
        eyes.setForceFullPageScreenshot(true);
        eyes.checkWindow();
        eyes.close();

    }

    public void checkWindowLayout(String appName, String test) {
        eyes.open(driver, appName, test);
        eyes.setMatchLevel(MatchLevel.LAYOUT);
        eyes.setForceFullPageScreenshot(true);
        eyes.checkWindow();
        eyes.close();

    }

    //
}

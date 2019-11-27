package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.LinkedHashMap;
import java.util.List;

public class ReusableActions {
    RepoReader rr = new RepoReader();
    InputMethods im = new InputMethods();
    String accessType = "accessType";
    String accessName = "accessName";

    public void navigate(String url){
        im.navigate(url);
    }

    public void click(String element)
    {
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.click(elementProperties.get(accessType), elementProperties.get(accessName));
    }

    public void enterText(String element, String text){
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.enterText(elementProperties.get(accessType), elementProperties.get(accessName), text);
    }

    public void closeBrowser(){
        im.closerBrowser();
    }

    public void displayed(String element){
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.displayed(element, elementProperties.get(accessType), elementProperties.get(accessName));
    }

    public void displayedWithText(String element, String text){
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.displayedWithText(element, elementProperties.get(accessType), elementProperties.get(accessName), text);
    }

    public void displayedMultipleElements(String[] elements){
        LinkedHashMap<String, LinkedHashMap<String, String>> elementProperties = rr.getObjectRepo();
        im.displayedMultipleElements(elements, elementProperties);

    }

    public void verifElementsInAscendingOrder(String element) {
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.verifElementsInAscendingOrder(elementProperties.get(accessType), elementProperties.get(accessName));
    }

    public void validateChart2018Heights(String element, String heights) {
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.validateChart2018Heights(elementProperties.get(accessType), elementProperties.get(accessName), heights);
    }

    public void validateChart2017Heights(String element, String heights) {
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.validateChart2017Heights(elementProperties.get(accessType), elementProperties.get(accessName), heights);
    }

    public void validateChartTotalNumberBars(String element, int bars) {
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.validateChartTotalNumberBars(elementProperties.get(accessType), elementProperties.get(accessName), bars);
    }

    public void elementsDisplayed(String element, int numberOfElements) {
        LinkedHashMap<String, String> elementProperties = rr.getObjectRepo().get(element);
        im.elementsDisplayed(elementProperties.get(accessType), elementProperties.get(accessName), numberOfElements);
    }

    public void checkWindow(String appName, String test){
        im.checkWindow(appName, test);
    }
    public void checkWindowContent(String appName, String test){
        im.checkWindowContent(appName, test);
    }
    public void checkWindowLayout(String appName, String test){
        im.checkWindowLayout(appName, test);
    }

}

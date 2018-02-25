package com.netcracker.travelplanner.api;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebParser {

    public static WebDriver getDriver(){

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"phantomjs.exe");

        return new PhantomJSDriver(capabilities);
    }
    public static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(driver, 6);
        wait.until(pageLoadCondition);
    }

}

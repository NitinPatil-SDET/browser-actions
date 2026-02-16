package com.nitin.automation.actions;

import com.nitin.automation.WaitUtils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BrowserActions {

    private final WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public static void clickOn(WebDriver driver, By locator) {
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            element.clear();
            element.click();
        } catch (StaleElementReferenceException e) {
            // re-wait and re-fetch element from refreshed DOM
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            element.clear();
            element.click();
        }
    }


    public void insertText(By locator, String text) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to insertText into element: " + locator, e);
        }
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void closeBrowser(WebDriver driver){
        driver.quit();
    }


}

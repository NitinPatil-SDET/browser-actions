package com.nitin.automation.action;

import org.openqa.selenium.WebDriver;
import com.nitin.automation.waits.WaitUtils;
import org.openqa.selenium.WebElement;

public class BrowserActions {
    private BrowserActions() {}

    public static void clickOn(WebDriver driver, WebElement element) {
        WaitUtils.waitForClickable(driver, element).click();
    }

    public static void type(WebDriver driver, WebElement element, String text) {
        WebElement el = WaitUtils.waitForVisible(driver, element);
        el.clear();
        el.sendKeys(text);
    }

    public static void openUrl(WebDriver driver, String url) {
        driver.get(url);
    }
}

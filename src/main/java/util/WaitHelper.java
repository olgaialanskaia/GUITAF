package util;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WaitHelper {

    public static boolean elementExists(WebDriver driver, WebElement element, int seconds) {
        try {
            (new WebDriverWait(driver, seconds))
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        }
        catch(NotFoundException ex) {
            return false;
        }
    }

    public static void waitForElementVisibility(WebDriver driver, WebElement el, long time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(el));
    }

}

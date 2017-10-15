package adapters;

import configuration.Settings;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.reporters.TestHTMLReporter;

import java.io.File;
import java.io.IOException;

public class WordpressTestSessionListener extends TestHTMLReporter implements ITestListener {

    protected Settings settings = new Settings();

    protected File createScreenshot(String screenName) {
        try {
            File scrFile = ((TakesScreenshot)WebDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            String destDir = "reports/screenshots";
            boolean isFolderExists = (new File(destDir).exists());

            if (!isFolderExists) {
                boolean isFolder = (new File(destDir).mkdirs());
                Assert.assertTrue(isFolder, "Folder was not created.");
            }

            String destFile = String.format("%s/%s.png", destDir, screenName);
            File screen = new File(destFile);
            FileUtils.copyFile(scrFile, screen);
            return screen;

        } catch (SessionNotCreatedException e) {
            e.printStackTrace();
            Assert.fail("WebDriver session is lost.", e.getCause());
        } catch (IOException | WebDriverException e) {
            e.printStackTrace();
            System.out.println("Screenshot was not created. Due to an error: " + e.getMessage());
        }
        return null;
    }

    protected void reportLogScreenshot(String screenName) {
        System.setProperty("org.uncommons.reporting.escape-output", "false");
        createScreenshot(screenName);
        String destFile = String.format("$.png", screenName);
        String logImage = "a id=\"screenName\" class=\"picture\" href=screenshots/" + destFile + "><img style=\"width:300px\" src=screenshots/" + destFile + "></a><br>";
        Reporter.log(logImage);
        Reporter.setCurrentTestResult(null);
    }

    @Override
    public void onTestStart(ITestResult result) {
        WebDriver driver = settings.getDriver();
        WebDriverManager.setWebDriver(driver);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class) != null) {
            WebDriverManager.getDriver().close();
            WebDriverManager.getDriver().quit();
        }

    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class) != null) {
            reportLogScreenshot(String.format("%s.%s", result.getInstanceName(), result.getName()));
            WebDriverManager.getDriver().close();
            WebDriverManager.getDriver().quit();
        }
    }


}

package platform.pages;

import adapters.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.log4testng.Logger;
import util.WaitHelper;

public class LoginPage extends AbstractPage {

    @FindBy(id = "usernameOrEmail")
    protected WebElement usernameField;
    @FindBy(id = "password")
    protected WebElement passwordField;
    @FindBy(css = ".button.is-primary")
    protected WebElement loginButton;
    @FindBy(css = ".form-input-validation.is-error")
    protected WebElement loginErrorMessage;

    static Logger logger = Logger.getLogger(LoginPage.class);

    public static LoginPage initialize(WebDriver driver) {
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public LoginPage(WebDriver driver) {
        super(driver);

        logger.debug("Instantiating Login Page.");
    }

    public Boolean isLoaded() {

        return loginButton
                .isDisplayed();

    }

    public ReaderPage regularUser() {
        String username = "olgaialanskaia";
        String pwd = "newyorkdogjump";

        logger.debug("Logging in with username/password: " + username + "/" + pwd);

        usernameField
                .sendKeys(username);

        passwordField
                .sendKeys(pwd);

        loginButton
                .click();

        ReaderPage readerPage =
                PageFactory.initElements(WebDriverManager.getDriver(), ReaderPage.class);

        Assert.assertTrue(readerPage.isLoaded(), "Page is not loaded.");

        return readerPage;

    }

    public LoginPage tryLoginWithInvalidCredentials() {
        String username = "johnsmith";
        String pwd = "123PH!@";

        logger.debug("Logging in with username/password: " + username + "/" + pwd);

        usernameField
                .sendKeys(username);

        passwordField
                .sendKeys(pwd);

        loginButton
                .click();

        boolean exists = WaitHelper.elementExists(getWebDriver(), loginErrorMessage, 5);

        Assert.assertTrue(exists && loginErrorMessage
                .isDisplayed(), "Error message not displayed.");

        return this;

    }

    public LoginPage tryLoginWithNoCredentials() {

        logger.debug("Logging with no credentials, username and password fields left blank.");

        loginButton
                .click();

        LoginPage loginPage =
                PageFactory.initElements(WebDriverManager.getDriver(), LoginPage.class);

        Assert.assertTrue(loginPage.isLoaded(), "Page is not loaded.");

        return loginPage;
    }

}
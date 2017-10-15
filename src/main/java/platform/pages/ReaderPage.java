package platform.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.log4testng.Logger;

public class ReaderPage extends AbstractPage {

    @FindBy(id="content")
    protected WebElement pageCentralPanel;

    static Logger logger = Logger.getLogger(ReaderPage.class);

    public ReaderPage(WebDriver driver) {
        super(driver);

        logger.debug("Instantiating Reader Page.");
    }

    public Boolean isLoaded() {

        return pageCentralPanel
                .isDisplayed();

    }



}

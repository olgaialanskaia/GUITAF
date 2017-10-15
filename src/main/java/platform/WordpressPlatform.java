package platform;

import adapters.WebDriverManager;
import configuration.Settings;
import platform.pages.LoginPage;

public class WordpressPlatform {

    static Settings settings = new Settings();

    public static LoginPage loginAs() {

        String environment = settings.getWordpressUrl();
        WebDriverManager.getDriver().navigate().to(environment);
        WebDriverManager.getDriver().manage().window().maximize();

        return LoginPage.initialize(WebDriverManager.getDriver());
    }

}



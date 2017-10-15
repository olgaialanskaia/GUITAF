package configuration;

import browser.BrowserType;
import exceptions.UnknownBrowserException;
import exceptions.UnknownPropertyException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    private static final String SELENIUM_BROWSER = "selenium.browser";
    private static final String SELENIUM_PROPERTIES = "projectOlga.properties";
    private static String stagingUrl;
    private static final String RETRY_COUNT = "retryCount";
    private BrowserType browser;
    private static String retryCount;

    private Properties properties = new Properties();

    public Settings() {
        loadSettings();
    }

    private void loadSettings() {
        properties = loadPropertiesFile();
        browser = BrowserType.Browser(getPropertyOrNull(SELENIUM_BROWSER));
        retryCount = getPropertyOrNull(RETRY_COUNT);
    }

    private Properties loadPropertiesFile() {
        try {
            // get specified property file
            String filename = getPropertyOrNull(SELENIUM_PROPERTIES);
            // if not defined, use default
            if (filename == null) {
                filename = SELENIUM_PROPERTIES;
            }

            //try to load from classpath
            InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
            // no file in classpath, look on disk
            if (stream == null) {
                stream = new FileInputStream(new File(filename));
            }
            Properties result = new Properties();
            result.load(stream);
            return result;
        } catch (IOException e) {
            throw new UnknownPropertyException("Property file is not found");
        }
    }

    public String getPropertyOrNull(String name) {
        return getProperty(name, false);
    }

    private String getProperty(String name, boolean forceExceptionIfNotDefined) {
        String result;
        if ((result = System.getProperty(name)) != null && result.length() > 0) {
            return result;
        } else if ((result = getPropertyFromPropertiesFile(name)) != null && result.length() > 0) {
            return result;
        } else if (forceExceptionIfNotDefined) {
            throw new UnknownPropertyException("Unknown property: [" + name + "]");
        }
        return result;
    }

    private String getPropertyFromPropertiesFile(String name) {
        Object result = properties.get(name);
        if (result == null) {
            return null;
        } else {
            return result.toString();
        }
    }

    public WebDriver getDriver() {
        return getDriver(browser);
    }

    private WebDriver getDriver(BrowserType browserType) {
        switch (browserType) {
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "/src/main/resources/chromedriver");
                return new FirefoxDriver();
            case CHROME:
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "/src/main/resources/chromedriver");
                return new ChromeDriver();
            default:
                throw new UnknownBrowserException("Cannot create driver for unknown browser type.");
        }
    }

    public String getWordpressUrl() {
        return String.format("https://wordpress.com/wp-login.php", stagingUrl);
    }


    public static int getRetryCount() {
        return Integer.parseInt(retryCount);
    }

}

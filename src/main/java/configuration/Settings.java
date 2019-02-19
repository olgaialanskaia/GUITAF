package configuration;

import browser.BrowserType;
import exceptions.UnknownBrowserException;
import exceptions.UnknownPropertyException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    private static final String SELENIUM_PROPERTIES = "projectOlga.properties";
    private static final String SELENIUM_BROWSER = "selenium.browser";
    private static final String RETRY_COUNT = "retryCount";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static String stagingUrl;
    private BrowserType browser;
    private static String username;
    private static String password;
    private static String retryCount;

    private Properties properties = new Properties();

    public Settings() { loadSettings(); }

    public WebDriver getDriver() { return getDriver(browser); }

    public String getPropertyOrNull(String name) { return getProperty(name, false); }

    public String getWordpressUrl() { return String.format("https://wordpress.com/wp-login.php", stagingUrl); }

    public static int getRetryCount() { return Integer.parseInt(retryCount); }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    private void loadSettings() {
        // Preferentially load properties - first check environment variables, then properties file
        username = getPropertyOrNull(USERNAME);
        if (username == null) {
            // If no system variables found, load properties
            properties = loadPropertiesFile();
            username = getPropertyOrNull(USERNAME);
        }

        browser = BrowserType.Browser(getPropertyOrNull(SELENIUM_BROWSER));
        password = getPropertyOrNull(PASSWORD);
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

    private WebDriver getDriver(BrowserType browserType) {
        switch (browserType) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            default:
                throw new UnknownBrowserException("Cannot create driver for unknown browser type.");
        }
    }

}

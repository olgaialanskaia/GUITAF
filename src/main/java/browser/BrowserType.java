package browser;

import exceptions.UnknownBrowserException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum BrowserType {

    FIREFOX,
    CHROME;

    private static Map<String, BrowserType> browsersMap = new HashMap<String, BrowserType>();

    static {
        browsersMap.put("firefox", BrowserType.FIREFOX);
        browsersMap.put("chrome", BrowserType.CHROME);
    }

    public static BrowserType Browser(String name) {
        BrowserType browserType = null;
        if (name != null) {
            browserType = browsersMap.get(name.toLowerCase().trim());
            if (browserType == null) {
                throw new UnknownBrowserException("Unknown browser [" + name + "]. Use one of following: "
                        + StringUtils.join(browsersMap.keySet().toArray(), ", "));
            }
        }

        return browserType;
    }
}

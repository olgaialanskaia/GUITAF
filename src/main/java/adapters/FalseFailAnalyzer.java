package adapters;

import configuration.Settings;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class FalseFailAnalyzer implements IRetryAnalyzer {
    private int count = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (count < Settings.getRetryCount()) {
            count++;
            return true;
        }

        return false;
    }
}

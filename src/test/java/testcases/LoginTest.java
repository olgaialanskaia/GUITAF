package testcases;

import adapters.FalseFailAnalyzer;
import adapters.WordpressTestSessionListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import platform.WordpressPlatform;

@Listeners({WordpressTestSessionListener.class})

public class LoginTest {

    @Test(retryAnalyzer = FalseFailAnalyzer.class)
    public void shouldLoginWithValidCredentials() {

        WordpressPlatform.loginAs()
                .regularUser();
    }


    @Test(retryAnalyzer = FalseFailAnalyzer.class)
    public void shouldNotLoginWithInvalidCredentialsUnregisteredUser() {

        WordpressPlatform.loginAs()
                .tryLoginWithInvalidCredentials();


    }

    @Test(retryAnalyzer = FalseFailAnalyzer.class)
    public void shouldNotLoginWithNoCredentials() {

        WordpressPlatform.loginAs()
                .tryLoginWithNoCredentials();


    }


}

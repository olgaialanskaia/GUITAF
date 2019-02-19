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

        WordpressPlatform.navigateToSite()
                .loginWithValidCredentials();
    }


    @Test(retryAnalyzer = FalseFailAnalyzer.class)
    public void shouldNotLoginWithInvalidCredentialsUnregisteredUser() {

        WordpressPlatform.navigateToSite()
                .tryLoginWithInvalidCredentials();


    }

    @Test(retryAnalyzer = FalseFailAnalyzer.class)
    public void shouldNotLoginWithNoCredentials() {

        WordpressPlatform.navigateToSite()
                .tryLoginWithNoCredentials();


    }


}

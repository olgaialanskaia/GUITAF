package testcases;

import adapters.FalseFailAnalyzer;
import adapters.TestSessionListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import platform.ApplicationUnderTest;

@Listeners({TestSessionListener.class})

public class LoginTest {

    @Test(retryAnalyzer = FalseFailAnalyzer.class)
    public void shouldLoginWithValidCredentials() {

        ApplicationUnderTest.navigateToSite()
                .loginWithValidCredentials();
    }


    @Test(retryAnalyzer = FalseFailAnalyzer.class)
    public void shouldNotLoginWithInvalidCredentialsUnregisteredUser() {

        ApplicationUnderTest.navigateToSite()
                .tryLoginWithInvalidCredentials();


    }

    @Test(retryAnalyzer = FalseFailAnalyzer.class)
    public void shouldNotLoginWithNoCredentials() {

        ApplicationUnderTest.navigateToSite()
                .tryLoginWithNoCredentials();


    }


}

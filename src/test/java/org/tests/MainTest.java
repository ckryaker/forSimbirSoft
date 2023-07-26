package org.tests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.pages.LoginPage;
import org.pages.ProfilePage;
import org.pages.TransactionPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static org.methods.AdditionalMethods.getFibonacciValue;

public class MainTest{
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static TransactionPage transactionPage;
    public static WebDriver driver;
    private final int fibonacciValue = getFibonacciValue(LocalDate.now().getDayOfMonth() + 1);

    @BeforeClass
    public static void login() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        transactionPage = new TransactionPage(driver);
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/");
    }

    @Test
    public void e2eTest(){
        loginPage.choseCustType();
        loginPage.choseName("Harry Potter");
        profilePage.setDeposit(fibonacciValue, "Deposit Successful");
        profilePage.setWithdrawl(fibonacciValue, "Transaction successful");
        profilePage.checkBalance("0", 3000);
        profilePage.transactions();
        transactionPage.rowCount(3,  1000, 5);
        transactionPage.csvFileCreate();
    }

    @After
    public void exit(){
        driver.quit();
    }
}

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

public class MainTest {

    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static TransactionPage transactionPage;
    public static WebDriver driver;

    public static int getFibonacciValue(int n) {
        if (n <= 1) {
            return 0;
        } else if (n == 2) {
            return 1;
        } else  {
            return getFibonacciValue(n - 1) + getFibonacciValue(n - 2);
        }
    }

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
        int fibonacciValue = getFibonacciValue(LocalDate.now().getDayOfMonth() + 1);
        loginPage.choseCustType(2);
        loginPage.choseName("Harry Potter", true, 2);
        profilePage.setDeposit(fibonacciValue, "Deposit Successful", 2, true, 2);
        profilePage.setWithdrawl(fibonacciValue, "Transaction successful", 2, true, 2);
        profilePage.checkBalance("0", 1000, 5, 2);
        profilePage.transactions(2);
        transactionPage.rowCount(2, 2, true, 5000, 5);
        transactionPage.csvFileCreate();
    }

    @After
    public void exit(){
        driver.quit();
    }

}

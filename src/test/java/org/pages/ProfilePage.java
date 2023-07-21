package org.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.methods.AdditionalMethods.*;

public class ProfilePage {
    public WebDriver driver;

    public ProfilePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css="button[ng-class='btnClass2']")
    private WebElement deposit;

    @FindBy(xpath=".//div/label[text()='Amount to be Deposited :']/following-sibling::input")
    private WebElement depositAmount;

    @FindBy(css="button[type='submit']")
    private WebElement submitAmount;

    @FindBy(xpath=".//div/label[text()='Amount to be Withdrawn :']/following-sibling::input")
    private WebElement withdrawlAmount;

    @FindBy(css="button[ng-class='btnClass3']")
    private WebElement withdrawl;

    @FindBy(xpath="(.//div[@ng-hide='noAccount'])[1]/*[@class='ng-binding'][2]")
    private WebElement balance;

    @FindBy(css="button[ng-class='btnClass1']")
    private WebElement transactions;

    @Step("Депозит на {amount}. Ожидается \"{expectedAnswer}\" ответ")
    public void setDeposit(int amount, String expectedAnswer, int timeoutForExpRes, boolean screenshot, int wait){
        new WebDriverWait(driver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.visibilityOf(this.deposit));
        deposit.click();
        new WebDriverWait(driver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.visibilityOf(this.depositAmount));
        depositAmount.sendKeys(String.valueOf(amount));
        submitAmount.click();
        if (screenshot) screenshot();
        try {
            waitingElementDisplay(timeoutForExpRes, ".//*[contains(text(), '" + expectedAnswer + "')]");
        }catch (TimeoutException ignored){
            Allure.getLifecycle().updateStep(stepResult -> stepResult.setStatus(Status.FAILED));
            Allure.getLifecycle().updateTestCase(stepResult -> stepResult.setStatus(Status.FAILED));
            Allure.getLifecycle().stopStep();
        }
    }

    @Step ("Списание средств на {amount}. Ожидается \"{expectedAnswer}\" ответ")
    public void setWithdrawl(int amount, String expectedAnswer, int timeoutForExpRes, boolean screenshot, int wait){
        new WebDriverWait(driver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.visibilityOf(this.withdrawl));
        withdrawl.click();
        new WebDriverWait(driver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.visibilityOf(this.withdrawlAmount));
        withdrawlAmount.sendKeys(String.valueOf(amount));
        submitAmount.click();
        if (screenshot) screenshot();
        try {
            waitingElementDisplay(timeoutForExpRes, ".//*[contains(text(), '" + expectedAnswer + "')]");
        }catch (TimeoutException ignored){
            Allure.getLifecycle().updateStep(stepResult -> stepResult.setStatus(Status.FAILED));
            Allure.getLifecycle().updateTestCase(stepResult -> stepResult.setStatus(Status.FAILED));
            Allure.getLifecycle().stopStep();
        }
    }

    @Step("Проверка баланса")
    public void checkBalance(String expectedValue, int repeatTime, int repeatCount ,int wait){
        new WebDriverWait(driver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.visibilityOf(this.balance));
        checkValue(expectedValue, balance.getText(), repeatTime, repeatCount, balance);
        }

    @Step("Переход к транзакциям")
    public void transactions(int wait){
        new WebDriverWait(driver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.visibilityOf(this.transactions));
        transactions.click();
    }
}

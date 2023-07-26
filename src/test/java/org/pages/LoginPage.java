package org.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    public WebDriver driver;
    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath=".//button[text()='Customer Login']")
    private WebElement custType;

    @FindBy(css="#userSelect")
    private WebElement selectName;

    @FindBy(xpath=".//button[text()='Login']")
    private WebElement loginButton;

    @Step("Выбор типа кастомера")
    public void choseCustType(){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(this.custType));
        custType.click();
    }

    @Step("Выбор {value} кастомера")
    public void choseName(String value){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(this.selectName));
        Select dropDown = new Select(selectName);
        dropDown.selectByVisibleText(value);
        loginButton.click();
    }
}

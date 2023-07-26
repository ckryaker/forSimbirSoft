package org.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.methods.AdditionalMethods.*;

public class TransactionPage {

    public WebDriver driver;

    public TransactionPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath=".//table")
    private WebElement table;

    @FindBy(xpath=".//table/tbody/tr")
    private List<WebElement> rows;

    @Step("Подсчет транзакций")
    public void rowCount(int expectedRowCount, int timeOut, int repeatCount){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(this.table));
        checkRowsCount(expectedRowCount, rows.size(), timeOut, repeatCount);
    }

    @Step("Создание файла")
    public void csvFileCreate(){
        try {
            FileWriter csvWriter = new FileWriter("src/test/resources/new.csv");
            csvWriter.append("Date and Time");
            csvWriter.append("\t\t");
            csvWriter.append("Amount");
            csvWriter.append("\t\t");
            csvWriter.append("Type");
            csvWriter.append("\n");
            for(int i = 0; i < rows.size(); i++) {
                List<WebElement> var1 = rows.get(i).findElements(By.xpath("td"));
                for(int k = 0; k < var1.size(); k++){
                    String var2 = var1.get(k).getText();
                    if (k == 0){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d',' yyyy h':'mm':'ss a", Locale.US);
                        LocalDateTime date = LocalDateTime.parse(var2, formatter);
                        var2 = date.format(DateTimeFormatter.ofPattern("dd MM yyyy HH:mm:ss"));
                    }
                    csvWriter.append(var2);
                    if (k == 1)
                        csvWriter.append("\t\t");
                    else
                        csvWriter.append("\t");
                }
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            attachFileType_CSV("src/test/resources/new.csv");
        }catch (Exception ignored){
        }
    }
}

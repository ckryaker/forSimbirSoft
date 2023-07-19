import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionPage {

    public WebDriver driver;

    public TransactionPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    private void checkRowsCount(int expectedValue, int actualValue, int repeatTime, int repeatCount){
        try {
            assertEquals(actualValue, expectedValue);
        }catch (AssertionFailedError error){
            for(int i = 0; i < repeatCount; i++){
                try{
                    Thread.sleep(repeatTime);
                    try{
                        driver.navigate().refresh();
                        actualValue = driver.findElements(By.xpath(".//tbody/tr")).size();
                        assertEquals(actualValue, expectedValue);
                        break;
                    }
                    catch (AssertionFailedError ignored) {
                    }
                }catch (Exception ignored){
                }
            }
            try{
                assertEquals(actualValue, expectedValue);
            }catch (AssertionFailedError ignored){
                throw new AssertionFailedError();
            }
        }

    }

    private static byte[] fileToBytes(String fileName) throws Exception {
        File file = new File(fileName);
        return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
    }

    @Attachment
    public static byte[] attachFileType_CSV(String filePath) throws Exception {
        return fileToBytes(filePath);
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @FindBy(xpath=".//table")
    private WebElement table;

    @FindBy(xpath=".//table/tbody/tr")
    private List<WebElement> rows;

    @Step("Подсчет транзакций")
    public void rowCount(int expectedRowCount, int wait, boolean screenshot, int repeatTime, int repeatCount){
        new WebDriverWait(driver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.visibilityOf(this.table));
        checkRowsCount(expectedRowCount, rows.size(), repeatTime, repeatCount);
        if (screenshot) screenshot();
    }


    @Step("Создание файла")
    public void csvFileCreate(){
        try {
            FileWriter csvWriter = new FileWriter("new.csv");
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
            attachFileType_CSV("new.csv");
        }catch (Exception ignored){
        }
    }
}

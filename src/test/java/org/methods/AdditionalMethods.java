package org.methods;

import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tests.MainTest.driver;

public class AdditionalMethods {
    public static int getFibonacciValue(int n) {
        if (n <= 1) {
            return 0;
        } else if (n == 2) {
            return 1;
        } else  {
            return getFibonacciValue(n - 1) + getFibonacciValue(n - 2);
        }
    }

    public static void checkRowsCount(int expectedValue, int actualValue, int repeatTime, int repeatCount){
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

    @Attachment(value = "CSV file", type = "text/csv")
    public static byte[] attachFileType_CSV(String filePath) throws Exception {
        return fileToBytes(filePath);
    }

    public static void waitingElementDisplay(int timeOut, String xpath){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))).isDisplayed();
    }

    public static void checkValue(String expectedValue, int timeOut, WebElement element){
        String sElement = element.toString().split("-> ")[1];
        String loc0 = sElement.split(": ")[1];
        String theLocator = loc0.substring(0,loc0.length()-1);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.textToBe(By.xpath(theLocator), expectedValue));
    }
}

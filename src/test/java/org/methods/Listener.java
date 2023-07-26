package org.methods;


import io.qameta.allure.Allure;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static org.tests.MainTest.driver;


public class Listener implements StepLifecycleListener {
    @Override
    public void beforeStepStop(StepResult result){
        screenshot();
    }

    public static void screenshot() {
        byte[] content = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        Allure.getLifecycle().addAttachment("screenshot", "image/png", ".png", content);
    }
}

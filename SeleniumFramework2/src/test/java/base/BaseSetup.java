package base;

import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.SafeActions;

import java.io.File;
import java.util.HashMap;

public class BaseSetup {

    public static WebDriver driver;

    @Before
    public void launchDriver(){
        PropertyConfigurator.configure("config/log4j.properties");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", System.getProperty("user.dir"));
        options.setExperimentalOption("prefs", chromePrefs);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        setDriver(driver);
    }

    public void setDriver(WebDriver driver){
        this.driver=driver;
    }

    public WebDriver getDriver(){
        return driver;
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            String screenshotName = scenario.getName()+SafeActions.getRandomNumber();
            if (scenario.isFailed()) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File srcFile = ts.getScreenshotAs(OutputType.FILE);
                File destPath = new File(System.getProperty("user.dir")+"/Reports/cucumber-reports/FailedScreenshots/"+screenshotName+".png");
                FileUtils.copyFile(srcFile, destPath);
                Reporter.addScreenCaptureFromPath("FailedScreenshots/"+screenshotName+".png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
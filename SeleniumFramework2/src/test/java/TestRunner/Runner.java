package TestRunner;

import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
//  features="src\\test\\java\\features\\DatabaseChecks.feature",
 features="src\\test\\java\\features\\ApplicationChecks.feature",
     //        features="src\\test\\java\\features\\ViewImages.feature",
     //       features="src\\test\\java\\features\\ViewEORDocument.feature",
        //     features="src\\test\\java\\features\\PIPLogChecks.feature",
//         features="src\\test\\java\\features\\CASSValidation.feature",



        // features="src\\test\\java\\features\\MTRECalculationChecks.feature",
        //  features="src\\test\\java\\features\\3MWebservices.feature",

//To run below features - we need to run MTRECalculationChecks and 3MWebservices at first and wait for some time
//  features="src\\test\\java\\features\\DraftPrintingChecks.feature",
//   features="src\\test\\java\\features\\PPOValidations.feature",
//  features="src\\test\\java\\features\\3MWebserviceValidations.feature",
//  features="src\\test\\java\\features\\MoveDocToNurseReview.feature",



//     features="src\\test\\java\\features\\UploadImage.feature",

        glue="",
        plugin={"com.cucumber.listener.ExtentCucumberFormatter:Reports/cucumber-reports/report.html"}
)


public class Runner {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/config/extent-config.xml"));
        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
    }
}

//move to nursereview atlast
//ZZ1112237 ,ZZ0604200

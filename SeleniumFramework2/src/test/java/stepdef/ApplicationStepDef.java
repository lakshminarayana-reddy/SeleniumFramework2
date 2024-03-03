package stepdef;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.CommonPageActions;
import pages.HomePage;
import utils.ConfigManager;
import utils.ExcelUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationStepDef {
    CommonPageActions commonPageActions = new CommonPageActions();

    @When("^Navigate to application and login with credentials into client and verify home page$")
    public void navigateToURLAndLoginWithCredentials() {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for(int i=0; i<excelData.size(); i++) {
            String username= (String) excelData.get(i).get("UserName");
            String password= (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            HomePage homePage = new HomePage();
            homePage.verifyHomePage();
        }
    }

    @Then("^Verify home page is opened$")
    public void verifyHomePageIsOpened() {
        HomePage homePage = new HomePage();
        homePage.verifyHomePage();
    }

    @When("^Navigate to application \"([^\"]*)\" and login with credentials into \"([^\"]*)\" client$")
    public void navigateToApplicationAndLoginWithCredentialsIntoClient(String appUrl, String client) throws Throwable {
        String username=null;
        String password=null;
        commonPageActions.userNavigatesToApp(appUrl);
        ConfigManager configManager = new ConfigManager();
        if(appUrl.contains("myreview")){
            username = configManager.getProperty("myReviewUsername");
            password = configManager.getProperty("myReviewPassword");
        } else if(appUrl.contains("qm16web1")){
            username = configManager.getProperty("qaUserName");
            password = configManager.getProperty("qaPassword");
        } else if(appUrl.contains("test")){
            username = configManager.getProperty("uatUserName");
            password = configManager.getProperty("uatPassword");
        } else if(appUrl.contains("ad")){
            username = configManager.getProperty("prodUserName");
            password = configManager.getProperty("prodPassword");
        } else if(appUrl.contains("blue")){
            username = configManager.getProperty("blueUsername");
            password = configManager.getProperty("bluePassword");
        } else if(appUrl.contains("geico")){
            username = configManager.getProperty("geicoUsername");
            password = configManager.getProperty("geicoPassword");
        }
        commonPageActions.login(username,password, client);
    }
}

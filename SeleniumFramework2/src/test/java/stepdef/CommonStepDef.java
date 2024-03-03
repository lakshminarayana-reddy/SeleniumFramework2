package stepdef;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.log4j.Logger;
import pages.CommonPageActions;
import pages.HomePage;
import pages.WorkFlowManagerPage;
import utils.ExcelUtils;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

public class CommonStepDef {
    public static ArrayList<HashMap<Object, Object>> excelData = new ArrayList<>();
    private static Logger logger = Logger.getLogger(DriverManager.class);

    @Given("^User get data from excel sheet \"([^\"]*)\" for health checks$")
    public void userGetDataFromExcelSheetForMTRECalculations(String sheetName) {
        excelData = ExcelUtils.userGetDataFromExcel(sheetName);
        logger.info("Cloned data from excel - "+excelData);
    }

    @Then("^User search doc and move doc to nurse review bin$")
    public void userSearchDocAndMoveDocToNurseReviewBin() {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for(int i=0; i<excelData.size(); i++) {
            String docId = (String) excelData.get(i).get("Doc_Id");
            String username = (String) excelData.get(i).get("UserName");
            String password = (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            CommonPageActions commonPageActions = new CommonPageActions();
            HomePage homePage = new HomePage();
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            homePage.switchDefaultContent();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Workflow Manager");
            WorkFlowManagerPage workFlowManagerPage = new WorkFlowManagerPage();
            workFlowManagerPage.verifyWorkFlowManagerPage();
            workFlowManagerPage.searchPatientWithDocId(docId);
            workFlowManagerPage.checkAndMoveToRequiredBin("Nurse Review", (String) excelData.get(i).get("ProcessorName"));
            workFlowManagerPage.verifyDocIsMoved(docId);
        }
    }
}

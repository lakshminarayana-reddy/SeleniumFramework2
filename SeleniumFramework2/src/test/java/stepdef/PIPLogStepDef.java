package stepdef;

import cucumber.api.java.en.Then;
import pages.CommonPageActions;
import pages.HomePage;
import pages.MedicalBillReviewSolutionsPage;
import utils.ExcelUtils;
import utils.Waits;

import java.util.ArrayList;
import java.util.HashMap;

public class PIPLogStepDef {

    CommonPageActions commonPageActions = new CommonPageActions();
    MedicalBillReviewSolutionsPage medicalBillReviewSolutionsPage = new MedicalBillReviewSolutionsPage();

    @Then("^User navigates to Claim File View in medical bill review solution and verify claim document$")
    public void userNavigatesToClaimFileViewInMedicalBillReviewSolutionAndVerifyClaimDocument() {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for (int i=0; i<excelData.size(); i++){
            String username= (String) excelData.get(i).get("UserName");
            String password= (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            HomePage homePage = new HomePage();
            homePage.verifyHomePage();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Claim File View");
            medicalBillReviewSolutionsPage.searchWithDocId((String) excelData.get(i).get("DocId"));
            medicalBillReviewSolutionsPage.navigateToRequiredTab("PIP Log");
            medicalBillReviewSolutionsPage.clickOnPrint();
            medicalBillReviewSolutionsPage.waitForSeconds(Waits.VERYSHORTWAIT);
            medicalBillReviewSolutionsPage.switchToFirstWindow();
            medicalBillReviewSolutionsPage.verifyDocIsOpened();
            medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
        }
    }
}

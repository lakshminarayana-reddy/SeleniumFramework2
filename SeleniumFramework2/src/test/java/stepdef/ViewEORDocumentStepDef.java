package stepdef;

import cucumber.api.java.en.Then;
import pages.CommonPageActions;
import pages.HomePage;
import pages.MedicalBillReviewSolutionsPage;
import utils.Waits;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewEORDocumentStepDef {

    @Then("^Verify EOR document in claim file view$")
    public void verifyEORDocumentInClaimFileView(){
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for(int i=0; i<excelData.size(); i++){
            String username= (String) excelData.get(i).get("UserName");
            String password= (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            CommonPageActions commonPageActions = new CommonPageActions();
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            HomePage homePage = new HomePage();
            homePage.verifyHomePage();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Claim File View");
            MedicalBillReviewSolutionsPage medicalBillReviewSolutionsPage = new MedicalBillReviewSolutionsPage();
            medicalBillReviewSolutionsPage.searchWithDocId((String) excelData.get(i).get("DocId"));
            medicalBillReviewSolutionsPage.navigateToRequiredTab("Doc List");
            medicalBillReviewSolutionsPage.switchToWindow("MedFlow - Adjuster Desktop ()");
            medicalBillReviewSolutionsPage.clickOnFirstEORDocument();
            medicalBillReviewSolutionsPage.waitForSeconds(Waits.VERYSHORTWAIT);
            medicalBillReviewSolutionsPage.switchToFirstWindow();
            medicalBillReviewSolutionsPage.verifyDocIsOpened();
            medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
        }
    }
}

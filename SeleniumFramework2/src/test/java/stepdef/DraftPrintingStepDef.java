package stepdef;

import cucumber.api.java.en.Then;
import pages.CommonPageActions;
import pages.HomePage;
import pages.MedicalBillReviewSolutionsPage;

import java.util.ArrayList;
import java.util.HashMap;

public class DraftPrintingStepDef {
    @Then("^Verify draft printing in claim file view$")
    public void verifyDraftPrintingInClaimFileView() {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for (int i=0; i<excelData.size(); i++){
            String username= (String) excelData.get(i).get("UserName");
            String password= (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            String docId = (String) excelData.get(i).get("Doc_Id");
            CommonPageActions commonPageActions = new CommonPageActions();
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            HomePage homePage = new HomePage();
            homePage.verifyHomePage();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Claim File View");
            MedicalBillReviewSolutionsPage medicalBillReviewSolutionsPage = new MedicalBillReviewSolutionsPage();
            medicalBillReviewSolutionsPage.searchWithDocId(docId);
            medicalBillReviewSolutionsPage.navigateToRequiredTab("Doc List");
            medicalBillReviewSolutionsPage.clickRPTTypeMore();
            medicalBillReviewSolutionsPage.verifyMorePopUp();
            medicalBillReviewSolutionsPage.verifyDraftEOBCreatedDate();
            medicalBillReviewSolutionsPage.switchToParentFrame();
        }
    }

    @Then("^Verify draft printing for 3MWebservice in claim file view$")
    public void verifyDraftPrintingForMWebserviceInClaimFileView() {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for (int i=0; i<excelData.size(); i++) {
            String username = (String) excelData.get(i).get("UserName");
            String password = (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            String docId = (String) excelData.get(i).get("Doc_Id");
            CommonPageActions commonPageActions = new CommonPageActions();
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            HomePage homePage = new HomePage();
            homePage.verifyHomePage();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Claim File View");
            MedicalBillReviewSolutionsPage medicalBillReviewSolutionsPage = new MedicalBillReviewSolutionsPage();
            medicalBillReviewSolutionsPage.searchWithDocId(docId);
            medicalBillReviewSolutionsPage.navigateToRequiredTab("Doc List");
            medicalBillReviewSolutionsPage.clickRPTTypeMore();
            medicalBillReviewSolutionsPage.verifyMorePopUp();
            medicalBillReviewSolutionsPage.verifyDraftEOBFor3M();
            medicalBillReviewSolutionsPage.switchToParentFrame();
        }
    }
}

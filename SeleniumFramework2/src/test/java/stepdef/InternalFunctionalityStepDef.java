package stepdef;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pages.CommonPageActions;
import pages.HomePage;
import pages.MedicalBillReviewSolutionsPage;
import utils.ExcelUtils;
import utils.Waits;

import java.util.ArrayList;
import java.util.HashMap;

public class InternalFunctionalityStepDef {
    ArrayList<HashMap<Object, Object>> excelData = new ArrayList<>();
    CommonPageActions commonPageActions = new CommonPageActions();
    MedicalBillReviewSolutionsPage medicalBillReviewSolutionsPage = new MedicalBillReviewSolutionsPage();

    @Given("^User get data from excel for internal functionality checks$")
    public void userGetDataFromExcelForPIPLogChecks() {
        excelData = ExcelUtils.userGetDataFromExcel("InternalFunctionalityChecks");
    }

    @Then("^User navigates to claim file view and verify image, document and RPT in claim file view page$")
    public void userNavigatesToClaimFileViewAndVerifyImageDocumentAndRPTInClaimFileViewPage() {
        for(int i=0; i<excelData.size(); i++){
            String username= (String) excelData.get(i).get("UserName");
            String password= (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            HomePage homePage = new HomePage();
            homePage.verifyHomePage();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Claim File View");
            medicalBillReviewSolutionsPage.searchWithMemberNumber((String) excelData.get(i).get("ClaimNumber"));
            medicalBillReviewSolutionsPage.navigateToRequiredTab("Doc List");
            medicalBillReviewSolutionsPage.clickOnFirstImageDocument();
            medicalBillReviewSolutionsPage.waitForSeconds(Waits.VERYSHORTWAIT);
            medicalBillReviewSolutionsPage.switchToFirstWindow();
            //medicalBillReviewSolutionsPage.verifyImageIsOpened();
            medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
            medicalBillReviewSolutionsPage.switchToWindow("MedFlow - Adjuster Desktop ()");
            medicalBillReviewSolutionsPage.clickOnFirstEORDocument();
            medicalBillReviewSolutionsPage.waitForSeconds(Waits.VERYSHORTWAIT);
            medicalBillReviewSolutionsPage.switchToFirstWindow();
            medicalBillReviewSolutionsPage.verifyDocIsOpened();
            medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
            medicalBillReviewSolutionsPage.clickRPTTypeMore();
            medicalBillReviewSolutionsPage.verifyMorePopUp();
            medicalBillReviewSolutionsPage.clickOnPDF();
            medicalBillReviewSolutionsPage.switchToFirstWindow();
            medicalBillReviewSolutionsPage.verifyDocIsOpened();
            medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
        }
    }
}

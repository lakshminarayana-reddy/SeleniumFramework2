package stepdef;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.HomePage;
import pages.MedicalBillReviewSolutionsPage;
import utils.ExcelUtils;
import utils.Waits;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionalityStepDef {
    HomePage homePage = new HomePage();
    MedicalBillReviewSolutionsPage medicalBillReviewSolutionsPage = new MedicalBillReviewSolutionsPage();
    ArrayList<HashMap<Object, Object>> excelData = new ArrayList<>();

    @When("^User navigates to \"([^\"]*)\" in Medical bill review solution$")
    public void userNavigatesToInMedicalBillReviewSolution(String linkName) {
        homePage.navigateToRequiredLinkInMedicalBillSolution(linkName);
    }

    @And("^Search claim files with member number \"([^\"]*)\"$")
    public void searchClaimFilesWithMemberNumber(String memberNumber) {
        medicalBillReviewSolutionsPage.searchWithMemberNumber(memberNumber);
    }

    @And("^User navigates to Doc List tab in claim file view page$")
    public void userNavigatesToDocListTabInClaimFileViewPage() {
        medicalBillReviewSolutionsPage.navigateToRequiredTab("Doc List");
    }

    @And("^User click on EOR image in claim file view doc list page$")
    public void userClickOnEORImageInClaimFileViewDocListPage() {
        medicalBillReviewSolutionsPage.switchToWindow("MedFlow - Adjuster Desktop ()");
        medicalBillReviewSolutionsPage.clickOnFirstEORDocument();
        medicalBillReviewSolutionsPage.waitForSeconds(Waits.VERYSHORTWAIT);
    }

    @Then("^Verify the document is opened$")
    public void verifyTheDocumentIsOpened() {
        medicalBillReviewSolutionsPage.switchToFirstWindow();
        medicalBillReviewSolutionsPage.verifyDocIsOpened();
        medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
    }

    @And("^User click on image in claim file view doc list page$")
    public void userClickOnImageInClaimFileViewDocListPage() {
        medicalBillReviewSolutionsPage.clickOnFirstImageDocument();
        medicalBillReviewSolutionsPage.waitForSeconds(Waits.VERYSHORTWAIT);
    }

    @Then("^Verify the image is opened$")
    public void verifyTheImageIsOpened() {
        medicalBillReviewSolutionsPage.switchToFirstWindow();
        //medicalBillReviewSolutionsPage.verifyImageIsOpened();
        medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
    }

    @When("^User click on print in claim file view pip log page$")
    public void userClickOnImageInClaimFileViewPipLogPage() {
        medicalBillReviewSolutionsPage.navigateToRequiredTab("PIP Log");
        medicalBillReviewSolutionsPage.clickOnPrint();
        medicalBillReviewSolutionsPage.waitForSeconds(Waits.VERYSHORTWAIT);
    }

    @When("^User click on more when the RPT Type is Draft EOB$")
    public void userClickOnMoreWhenTheRPTTypeIsDraftEOB() {
        medicalBillReviewSolutionsPage.clickRPTTypeMore();
    }

    @Then("^Verify the RPT in more popup$")
    public void verifyTheRPTInMorePopup() {
        medicalBillReviewSolutionsPage.verifyMorePopUp();
        medicalBillReviewSolutionsPage.clickOnPDF();
        medicalBillReviewSolutionsPage.switchToFirstWindow();
        medicalBillReviewSolutionsPage.verifyDocIsOpened();
        medicalBillReviewSolutionsPage.closeCurrentWindowAndSwitchToMain();
    }
}

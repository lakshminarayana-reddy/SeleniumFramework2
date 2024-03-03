package stepdef;

import cucumber.api.java.en.When;
import pages.CommonPageActions;
import pages.HomePage;
import pages.InternalMedicalBillReviewSolutionsPage;
import pages.WorkFlowManagerPage;

import java.util.ArrayList;
import java.util.HashMap;

public class MTRECalculationsStepDef {


    CommonPageActions commonPageActions = new CommonPageActions();
    HomePage homePage = new HomePage();

    @When("^User search doc and run MTRE then move doc to auto route bin$")
    public void userSearchDocAndRunMTREThenMoveDocToAutoRouteBin() {
        ArrayList<HashMap<Object, Object>> excelData = CommonStepDef.excelData;
        for(int i=0; i<excelData.size(); i++){
            String docId = (String)excelData.get(i).get("Doc_Id");
            //CommonPageActions.runMTREAPIForTheDoc(docId, (String)excelData.get(i).get("DB_Instance"), (String)excelData.get(i).get("Env_Name"));
            String username= (String) excelData.get(i).get("UserName");
            String password= (String) excelData.get(i).get("Password");
            String client = (String) excelData.get(i).get("Location");
            commonPageActions.userNavigatesToApp((String) excelData.get(i).get("Env_URL"));
            commonPageActions.login(username, password, client);
            homePage.switchDefaultContent();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Workflow Manager");
            WorkFlowManagerPage workFlowManagerPage = new WorkFlowManagerPage();
            workFlowManagerPage.verifyWorkFlowManagerPage();
            workFlowManagerPage.searchPatientWithDocId(docId);
            workFlowManagerPage.checkAndMoveToRequiredBin("Nurse Review", (String)excelData.get(i).get("ProcessorName"));
            workFlowManagerPage.verifyDocIsMoved(docId);
            homePage.switchDefaultContent();
            homePage.navigateToAdvanceReviewModule();
            InternalMedicalBillReviewSolutionsPage internalMedicalBillReviewSolutionsPage = new InternalMedicalBillReviewSolutionsPage();
            internalMedicalBillReviewSolutionsPage.switchToSingleFrame();
            internalMedicalBillReviewSolutionsPage.verifyAdvanceReviewModulePage();
//            internalMedicalBillReviewSolutionsPage.clickOnDocId(docId);
//            internalMedicalBillReviewSolutionsPage.clickOnReWork_DialogBox();
            internalMedicalBillReviewSolutionsPage.clickOnAdvanceReviewModule_left();
            internalMedicalBillReviewSolutionsPage.switchAdvanceReviewModuleTab();
            internalMedicalBillReviewSolutionsPage.switchToParentFrame();
            internalMedicalBillReviewSolutionsPage.clickApplyDefaultFlagActions_ARM();
            internalMedicalBillReviewSolutionsPage.selectFlagAction_ARM("Apply Reim Rules");
            internalMedicalBillReviewSolutionsPage.clickOnApply_ARM();
            internalMedicalBillReviewSolutionsPage.clickOnSaveAndRecalc_ARM();
            internalMedicalBillReviewSolutionsPage.switchToWindow("MedFlow - Adjuster Desktop ()");
            internalMedicalBillReviewSolutionsPage.moveToComplete();
            internalMedicalBillReviewSolutionsPage.clickSaveBtn();
            homePage.switchDefaultContent();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Workflow Manager");
            workFlowManagerPage.verifyWorkFlowManagerPage();
            workFlowManagerPage.searchPatientWithDocId(docId);
            workFlowManagerPage.checkAndMoveToRequiredBin((String)excelData.get(i).get("BinName"), "Inbox");
            workFlowManagerPage.verifyDocIsMoved(docId);
        }
    }
}

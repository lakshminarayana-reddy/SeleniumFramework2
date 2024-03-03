package stepdef;

import cucumber.api.java.en.Then;
import pages.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CASSStepDef {

    @Then("^Verify the address popup for billing provider in data capture$")
    public void verifyAddressPopupForBillingProviderInDataCapture(){
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
            homePage.switchDefaultContent();
            homePage.navigateToRequiredLinkInMedicalBillSolution("Workflow Manager");
            WorkFlowManagerPage workFlowManagerPage = new WorkFlowManagerPage();
            workFlowManagerPage.verifyWorkFlowManagerPage();
            workFlowManagerPage.searchPatientWithDocId(docId);
            workFlowManagerPage.checkAndMoveToRequiredBin("Data Capture", (String)excelData.get(i).get("ProcessorName"));
            workFlowManagerPage.verifyDocIsMoved(docId);
            homePage.switchDefaultContent();
            homePage.navigateToRequiredLink_InternalMedicalBillReviewSolution("Data Capture");
            DataCapturePage dataCapturePage = new DataCapturePage();
            dataCapturePage.pressTabKey(13);
            dataCapturePage.clickSameAsServiceProviderCheckBox();
            dataCapturePage.verifyAddressPopup();
        }
    }
}

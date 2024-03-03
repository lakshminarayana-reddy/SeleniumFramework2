package stepdef;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pages.HomePage;
import pages.InternalMedicalBillReviewSolutionsPage;
import pages.WorkFlowManagerPage;
import utils.CommonActions;
import utils.ConfigManager;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class NBDPPOStepDef {
    private static String selectDBRPProd;
    private static String selectDBWorkFlow;
    private static String execDBName;
    Statement selectStmt=null;
    CommonActions commonActions = new CommonActions();
    int count = 0;
    String newDocID = null;
    String dbName;
    HomePage homePage = new HomePage();
    WorkFlowManagerPage workFlowManagerPage = new WorkFlowManagerPage();
    InternalMedicalBillReviewSolutionsPage internalMedicalBillReviewSolutionsPage = new InternalMedicalBillReviewSolutionsPage();

    /**
     * Purpose - Configure DB properties
     *
     * @throws SQLException
     */
    public void DBProperties(String environment) throws SQLException {
        ConfigManager configManager = new ConfigManager();
        String connectionType = System.getProperty("connectionType");
        String selectUrl = null;
        String selectDBUser = null;
        String selectDBPassword = null;
        if(environment.equalsIgnoreCase("prod")){
            selectUrl = configManager.getProperty("prodUrl");
            selectDBUser = configManager.getProperty("prodDBUserName");
            selectDBPassword = configManager.getProperty("prodDBPassword");
        } else if (environment.equalsIgnoreCase("QA")){
            selectUrl = configManager.getProperty("qaUrl");
            selectDBUser = configManager.getProperty("qaDBUserName");
            selectDBPassword = configManager.getProperty("qaDBPassword");
        }
        String selectDBName = System.getProperty("SelectDBName");
        selectDBRPProd = System.getProperty("SelectDBRPProd");
        selectDBWorkFlow = System.getProperty("SelectDBWorkFlow");
        execDBName=System.getProperty("ExecDB");
        String selectConnectionUrl = "" + connectionType + "://" + selectUrl + ":1433;databaseName=" + selectDBName + ";user=" + selectDBUser + ";password=" + selectDBPassword + "";
        Connection selectConnection = DriverManager.getConnection(selectConnectionUrl);
        selectStmt = selectConnection.createStatement();
        dbName = execDBName;
    }

    @Given("^Start ppo validations for the Doc \"([^\"]*)\" in environment \"([^\"]*)\"$")
    public void startPpoValidationsForTheDoc(String docId, String environment) throws Throwable {
        DBProperties(environment);
        newDocID = docId;
        System.out.println(docId+" validation is started");
    }

    @And("^Verify processor name \"([^\"]*)\" and bin desc \"([^\"]*)\" in audit all table$")
    public void verifyProcessorNameAndBinDescInAuditAllTable(String processorName, String binDesc) throws SQLException, IOException {
        ResultSet resultSet = selectStmt.executeQuery("select * from " + selectDBRPProd + "..auditall where docid = '" + newDocID + "' \n" +
                "and ProcessorName='" + processorName + "' and BinDesc='" + binDesc + "'");
        System.out.println("select * from " + selectDBRPProd + "..auditall where docid = '" + newDocID + "' \n" +
                "and ProcessorName='" + processorName + "' and BinDesc='" + binDesc + "'");
        if (resultSet.next()) {
            System.out.println("The processor name " + processorName + " and bin desc " + binDesc + " for doc " + newDocID + "  is present in audit all table");
        } else {
            Assert.fail("The processor name " + processorName + " and bin desc " + binDesc + " for doc " + newDocID + " is not present in audit all table for doc " + newDocID);
        }
    }

    @And("^Verify ppo discount calculation in bill lines table$")
    public void verifyPpoDiscountValueInBillLinesTable() throws SQLException {
        DecimalFormat df = new DecimalFormat("0.00");
        ResultSet resultSet = selectStmt.executeQuery("select old_reimb,reimbursement,ppo_discount,old_rc,reason_code,DISCOUNT_TYPE,CHARGE,*\n" +
                " from "+selectDBRPProd+"..bill_lines where doc_id='" + newDocID + "'");
        ArrayList<ArrayList<String>> tableData = commonActions.getTableData(resultSet);
        System.out.println(tableData);
        String reasonCode = null;
        count = commonActions.getRowCount();
        if (dbName.equals("NW")) {
            reasonCode = "PPO1";
        } else if (dbName.equals("US")) {
            reasonCode = "PPO";
        }
        if (count == 0) {
            Assert.fail("The bill lines table is empty");
        }
        for (int i = 0; i < count; i++) {
            float newReimbursement = Float.parseFloat(tableData.get(i).get(1));
            float charge = Float.parseFloat(tableData.get(i).get(6));
            float oldReimbursement = Float.parseFloat(tableData.get(i).get(0));
            String discountType = tableData.get(i).get(5);
            if (tableData.get(i).get(5).startsWith("UC") && tableData.get(i).get(4).equals(reasonCode)) {
                if (newReimbursement >= charge) {
                    Assert.assertEquals("PPO discount for doc " + newDocID + " is not 0 in bill lines table", tableData.get(i).get(2), "0");
                    System.out.println("PPO discount for doc " + newDocID + " is 0 in bill lines table");
                } else if (newReimbursement < charge || newReimbursement >= oldReimbursement) {
                    float ppoDisc = charge - newReimbursement;
                    Assert.assertEquals("PPO discount for doc " + newDocID + " is not matched in bill lines table", df.format(Float.parseFloat(tableData.get(i).get(2))), df.format(ppoDisc));
                    System.out.println("PPO discount for doc " + newDocID + " is matched in bill lines table");
                }
            } else if (!tableData.get(i).get(5).startsWith("UC") && tableData.get(i).get(4).equals(reasonCode)) {
                float ppoDisc = oldReimbursement - newReimbursement;
                Assert.assertEquals("PPO discount for doc " + newDocID + " is not matched in bill lines table", df.format(Float.parseFloat(tableData.get(i).get(2))), df.format(ppoDisc));
                System.out.println("PPO discount for doc " + newDocID + " is matched in bill lines table");
            }
        }
    }

    @And("^Verify if reimbursement is 0 then there is 0 PPO_discount and old_rc is equal to reason_code$")
    public void verifyIfReimbursementIsThenThereIsPPO_discountAndOld_rcIsEqualToReason_code() throws SQLException {
        DecimalFormat df = new DecimalFormat("0.00");
        ResultSet resultSet = selectStmt.executeQuery("select old_reimb,reimbursement,ppo_discount,old_rc,reason_code,DISCOUNT_TYPE,CHARGE,*\n" +
                " from "+selectDBRPProd+"..bill_lines where doc_id='" + newDocID + "'");
        ArrayList<ArrayList<String>> tableData = commonActions.getTableData(resultSet);
        count = commonActions.getRowCount();
        if (count == 0) {
            Assert.fail("The bill lines table is empty");
        }
        for (int j = 0; j < count; j++) {
            float newReimbursement = Float.parseFloat(tableData.get(j).get(1));
            if (df.format(newReimbursement).equals("0")) {
                Assert.assertEquals("The PPO Discount is not equal to 0 for line " + j + " in doc " + newDocID + " bill lines table", "0", tableData.get(j).get(2));
                System.out.println("The PPO Discount is equal to 0 for line " + j + " in doc " + newDocID + " bill lines table");
                Assert.assertEquals("The old reason code is not matched with reason code for doc " + newDocID + " bill lines table", tableData.get(j).get(3), tableData.get(j).get(4));
                System.out.println("The old reason code is matched with reason code for line " + j + " in doc " + newDocID + " bill lines table");
            }
        }
    }

    @And("^Validate PPO_NAME is \"([^\"]*)\" in BILL_HEADER TAble$")
    public void validatePPO_NAMEIsNBDInBILL_HEADERTAble(String ppoName) throws SQLException, InterruptedException, IOException {
        Thread.sleep(5000);
        ResultSet resultSet = selectStmt.executeQuery("Select PPO_NAME, * from " + selectDBRPProd + "..BILL_HEADER where doc_id='" + newDocID + "'");
        resultSet.next();
        Assert.assertEquals("PPO Name is not as expected for doc " + newDocID + " in bill header table", ppoName, resultSet.getString("PPO_NAME"));
        System.out.println("PPO Name for " + newDocID + " is "+ppoName+" in bill header table");
    }

    @And("^Validate there is comment as \"([^\"]*)\" in Audit current$")
    public void validateThereIsCommentInAuditCurrent(String comment) throws SQLException {
        ResultSet resultSet = selectStmt.executeQuery("Select * from " + selectDBRPProd + "..auditall where docid='" + newDocID + "' and Comment='"+comment+"'");
        if (resultSet.next()) {
            System.out.println("Comment for " + newDocID + " is present in audit current table");
        } else {
            Assert.fail("Comment for " + newDocID + " is not present in audit current table");
        }
        System.out.println("Successfully validated the doc " + newDocID);
    }

    @When("^Navigate to work flow manager page in medical bill review solutions$")
    public void navigateToWorkFlowManagerPageInMedicalBillReviewSolutions() {
        homePage = new HomePage();
        homePage.switchDefaultContent();
        homePage.navigateToRequiredLinkInMedicalBillSolution("Workflow Manager");
    }

    @Then("^Verify work flow manager page in medical bill review solutions$")
    public void verifyWorkFlowManagerPageInMedicalBillReviewSolutions() {
        workFlowManagerPage = new WorkFlowManagerPage();
        workFlowManagerPage.verifyWorkFlowManagerPage();
    }

    @When("^Search doc and move to bin \"([^\"]*)\" and processor \"([^\"]*)\"$")
    public void searchDocAndMoveToBinAndProcessor(String binName, String processorName) {
        workFlowManagerPage.searchPatientWithDocId(newDocID);
        workFlowManagerPage.checkAndMoveToRequiredBin(binName, processorName);
    }

    @Then("^Verify the doc is moved to required bin and processor$")
    public void verifyTheDocIsMovedToRequiredBinAndProcessor() {
        workFlowManagerPage.verifyDocIsMoved(newDocID);
    }

    @And("^If processor name \"([^\"]*)\" and bin dec \"([^\"]*)\" is present move to \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyProcessorNameIsNotPresentInAuditAllTable(String processorName, String binDesc, String processorName1, String binDesc1) throws Throwable {
        Thread.sleep(60000);
        WorkFlowManagerPage workFlowManagerPage = new WorkFlowManagerPage();
        workFlowManagerPage.refresh();
        navigateToWorkFlowManagerPageInMedicalBillReviewSolutions();
        verifyWorkFlowManagerPageInMedicalBillReviewSolutions();
        workFlowManagerPage.searchPatientWithDocId(newDocID);
        if(workFlowManagerPage.verifyLocation(processorName, binDesc)){
            System.out.println("The processor name "+processorName+" is present in audit all table");
            workFlowManagerPage.checkAndMoveToRequiredBin(processorName1,binDesc1);
            verifyTheDocIsMovedToRequiredBinAndProcessor();
        }else {
            System.out.println("The processor name "+processorName+" is not present in audit all table");
        }
    }

    @When("^Navigate to advance review module and apply reimbursement rule$")
    public void reasonCodeInBillLineTableIsDup() throws SQLException {
        this.navigateToAdvanceReviewModule();
        this.verifyAdvanceReviewModulePage();
        clickOnDocId();
        clickOnReworkInDialogBox();
        this.clickOnLeftAdvanceReviewModule();
        this.clickOnApplyDefaultFlagActionInARM();
        this.selectFlagActionAndClickApplyButton("Apply Reim Rules");
        this.clickOnSaveAndReCalcButtonInARM();
        moveTheStatusTo();
    }

    @When("^Navigate to Internal-Internal Medical Bill Review Solution-Advance Review Module$")
    public void navigateToAdvanceReviewModule(){
        homePage.switchDefaultContent();
        homePage.navigateToAdvanceReviewModule();
    }

    @Then("^Verify advance review module page$")
    public void verifyAdvanceReviewModulePage(){
        internalMedicalBillReviewSolutionsPage.switchToSingleFrame();
        internalMedicalBillReviewSolutionsPage.verifyAdvanceReviewModulePage();
    }

    @And("^Click on left side Advance Review Module$")
    public void clickOnLeftAdvanceReviewModule(){
        internalMedicalBillReviewSolutionsPage.clickOnAdvanceReviewModule_left();
        internalMedicalBillReviewSolutionsPage.switchAdvanceReviewModuleTab();
    }

    @And("^Click on Apply default flag action in Advance review module$")
    public void clickOnApplyDefaultFlagActionInARM(){
        internalMedicalBillReviewSolutionsPage.clickApplyDefaultFlagActions_ARM();
    }

    @And("^Select Flag action \"([^\"]*)\" and click apply button$")
    public void selectFlagActionAndClickApplyButton(String flagAction){
        internalMedicalBillReviewSolutionsPage.selectFlagAction_ARM(flagAction);
        internalMedicalBillReviewSolutionsPage.clickOnApply_ARM();
    }

    @And("^Click on Save and Re-calc button in Advance review module$")
    public void clickOnSaveAndReCalcButtonInARM(){
        internalMedicalBillReviewSolutionsPage.clickOnSaveAndRecalc_ARM();
    }

    @When("^Click on newly created Doc ID$")
    public void clickOnDocId(){
        internalMedicalBillReviewSolutionsPage = new InternalMedicalBillReviewSolutionsPage();
        internalMedicalBillReviewSolutionsPage.clickOnDocId(newDocID);
    }

    @And("^Click on Rework in dialog box$")
    public void clickOnReworkInDialogBox(){
        internalMedicalBillReviewSolutionsPage.clickOnReWork_DialogBox();
    }

    @And("^Move the status to Complete$")
    public void moveTheStatusTo() {
        internalMedicalBillReviewSolutionsPage.switchToWindow("MedFlow - Adjuster Desktop ()");
        internalMedicalBillReviewSolutionsPage.moveToComplete();
        internalMedicalBillReviewSolutionsPage.clickSaveBtn();
    }
}

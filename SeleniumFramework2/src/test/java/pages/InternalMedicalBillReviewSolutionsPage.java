package pages;

import locators.InternalMedicalBillReviewSolutionsPageLocators;
import org.junit.Assert;
import org.openqa.selenium.By;
import utils.Dynamic;
import utils.SafeActions;

public class InternalMedicalBillReviewSolutionsPage extends SafeActions implements InternalMedicalBillReviewSolutionsPageLocators {
    /**
     * Purpose - To verify advance review module page
     */
    public void verifyAdvanceReviewModulePage(){
        boolean advanceReviewModulePage = isElementPresent(ADVANCEREVIEWMODULE_TEXT, SHORTWAIT);
        Assert.assertTrue("Advance review module page is not opened",advanceReviewModulePage);
    }

    /**
     * Purpose - To switch to the single frame
     */
    public void switchToSingleFrame(){
        waitForSeconds(2);
        switchFrame(SINGLE_FRAME);
    }

    /**
     * Purpose - To click on Doc ID
     * @param docId
     */
    public void clickOnDocId(String docId){
        if (!isElementPresent(Dynamic.getNewLocator(DOCID_LINK, docId), VERYSHORTWAIT)) {
            selectByVisibleText(PAGENUMBER_DROPDOWN, "All","Page number dropdown", SHORTWAIT);
        }
        if(isElementPresent(Dynamic.getNewLocator(DOCID_LINK, docId), VERYSHORTWAIT)){
            javaScriptClick(Dynamic.getNewLocator(DOCID_LINK, docId), SHORTWAIT, "Doc ID "+docId);
        }
    }

    /**
     * Purpose - To click on rework in dialog box
     */
    public void clickOnReWork_DialogBox(){
        switchToParentFrame();
        switchFrame("dialog-body");
        click(REWORK_LINK, SHORTWAIT,"Rework link in dialog box");
    }

    /**
     * Purpose - To click on left side Advance review module
     */
    public void clickOnAdvanceReviewModule_left(){
        switchToParentFrame();
        switchToSingleFrame();
        click(ADVANCEREVIEWMODULE_LINK, SHORTWAIT, "Advance review module left");
        switchToParentFrame();
    }

    /**
     * Purpose - To Switch to Advance review module window
     */
    public void switchAdvanceReviewModuleTab(){
        waitForSeconds(2);
        switchToWindow("Advanced Review Module");
    }

    /**
     * Purpose - To click on Apply default flag actions
     */
    public void clickApplyDefaultFlagActions_ARM(){
        click(APPLYDEFAULTFLAGACTIONS_LINK,SHORTWAIT, "Apply default flag actions");
    }

    /**
     * Purpose - To select flag action in ARM
     * @param flagName
     */
    public void selectFlagAction_ARM(String flagName){
        switchFrame("dialog-body");
        selectByVisibleText(FLAGACTION_DROPDOWN,flagName, "flag action dropdown in dialog box",SHORTWAIT);
    }

    /**
     * Purpose - To click on Apply button in ARM dialog box
     */
    public void clickOnApply_ARM(){
        isElementPresent(APPLY_BTN, SHORTWAIT);
        click(APPLY_BTN,SHORTWAIT, "Apply button in dialog box");
    }

    /**
     * Purpose - To click on save and re-calc in ARM
     */
    public void clickOnSaveAndRecalc_ARM(){
        switchToParentFrame();
        click(SAVEANDRECALC_BTN, SHORTWAIT, "Save and Recalc button");
        waitForPageToLoad();
        waitForSeconds(VERYSHORTWAIT);
    }

    /**
     * Purpose - To verify Indexing splitting labeling page
     */
    public void verifyIndexingSplittingLabelingPage(){
        switchToSingleFrame();
        boolean pageStatus = isElementPresent(NEWYORKBILL_DROPDOWN, SHORTWAIT);
        Assert.assertTrue("Indexing splitting labeling page is not loaded", pageStatus);
    }

    /**
     * Purpose - To submit details in indexing, splitting, labeling page
     */
    public void submitDetails_IndexingSplittingLabeling(){
        selectByVisibleText(NEWYORKBILL_DROPDOWN,"No","NewYork bill dropdown in indexing splitting labeling page",SHORTWAIT);
        click(Dynamic.getNewLocator(TABS_LINKS,"Florida"),SHORTWAIT, "Florida tab");
        selectByVisibleText(FLORIDABILL_DROPDOWN, "No", "Florida Bill dropdown", SHORTWAIT);
        click(SAVE_BTN, SHORTWAIT, "Save button");
    }

    /**
     * Purpose - To verify the success message in indexing, splitting, labeling page
     */
    public void verifySuccessMessage_IndexingSplittingLabeling(){
        boolean successMsg = isElementPresent(SUCCESS_MSG, SHORTWAIT);
        Assert.assertTrue("Success message is not displayed in indexing splitting labeling page", successMsg);
    }

    /**
     * Purpose - To move to complete button
     */
    public void moveToComplete(){
        switchFrame("SingleFrame");
        click(MOVETO_DROPDOWN,SHORTWAIT,"Move To dropdown");
        isElementPresent(COMPLETE, VERYSHORTWAIT);
        click(COMPLETE, SHORTWAIT, "Complete in dropdown");
        waitForSeconds(2);
        click(SUBMIT_BTN, SHORTWAIT, "Submit button");
    }

    /**
     * Purpose - To click on save button
     */
    public void clickSaveBtn(){
        switchFrame("dialog-body");
        if(isElementPresent(ADDRESS_FOR_FLAGS_CHECKBOX, VERYSHORTWAIT)){
            javaScriptClick(ADDRESS_FOR_FLAGS_CHECKBOX, SHORTWAIT, "Address for flags check box in Bill lines preview page");
        }
        isElementPresent(SAVE_BTN_DIALOG, SHORTWAIT);
        click(SAVE_BTN_DIALOG, SHORTWAIT,"Save button in dialog box");
    }

    public void updateStateInServiceProvider(String tin, String state, String zip){
        isElementPresent(SERVICE_PROVIDER_INFO_LINK, SHORTWAIT);
        click(SERVICE_PROVIDER_INFO_LINK, SHORTWAIT,"Service provider info link in ARM page");
        waitForSeconds(VERYVERYSHORTWAIT);
        switchFrameWithIndex(0);
        tin = tin.substring(0,2)+"-"+tin.substring(2,9);
        clearAndType(TIN_INPUT, tin, "Tin value", SHORTWAIT);
        waitForSeconds(VERYSHORTWAIT);
        By selectLoc = Dynamic.getNewLocator(SELECT_TIN, zip);
        selectLoc = Dynamic.getNewLocator(selectLoc, state);
        click(selectLoc, SHORTWAIT, "Tin with selected state and zip");
        click(SAVE_BUTTON, SHORTWAIT, "Save button");
    }
}

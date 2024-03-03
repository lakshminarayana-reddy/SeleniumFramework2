package pages;

import locators.WorkFlowManagerPageLocators;
import org.junit.Assert;
import utils.Dynamic;
import utils.SafeActions;

public class WorkFlowManagerPage extends SafeActions implements WorkFlowManagerPageLocators {
    /**
     *Purpose - Verify work flo
     */
    public void verifyWorkFlowManagerPage(){
        switchFrame("SingleFrame");
        boolean workFlowManagerPage = isElementPresent(SEARCH_LINK, SHORTWAIT);
        Assert.assertTrue("Work flow manager page is not opened",workFlowManagerPage);
    }

    /**
     * Purpose - To search patient with doc id
     * @param docId
     */
    public void searchPatientWithDocId(String docId){
        click(SEARCH_LINK, SHORTWAIT, "Search link in work flow manager page");
        switchFrame("RAD_SPLITTER_PANE_EXT_CONTENT_rdRight");
        clearAndType(DOC_ID_TEXT_FIELD, docId, "Doc Id search box in work flow manager page", SHORTWAIT);
        click(SEARCH_BTN, SHORTWAIT, "Search button after entering doc id in work flow manager page");
    }

    /**
     * Purpose - To check and move to required bin
     * @param binType
     * @param processorName
     */
    public void checkAndMoveToRequiredBin(String binType, String processorName){
        waitForSeconds(VERYVERYSHORTWAIT);
        switchFrameWithIndex(0);
        click(DOC_CHECKBOX, SHORTWAIT, "Check box in work flow manager page");
        click(SELECT_BIN, SHORTWAIT, "Select bin dropdown in work flow manager page");
        isElementPresent(Dynamic.getNewLocator(BIN_TEXT, binType), SHORTWAIT);
        javaScriptClick(Dynamic.getNewLocator(BIN_TEXT, binType), SHORTWAIT, "Bin text in select bin dropdown");
        waitForSeconds(VERYSHORTWAIT);
        click(SELECT_PROCESSOR, SHORTWAIT, "Select processor dropdown in work flow manager page");
        isElementPresent(Dynamic.getNewLocator(PROCESSOR_TEXT, processorName), SHORTWAIT);
        javaScriptClick(Dynamic.getNewLocator(PROCESSOR_TEXT, processorName), SHORTWAIT, "Processor text in select processor dropdown");
        waitForSeconds(VERYVERYSHORTWAIT);
        click(MOVE_BTN, SHORTWAIT, "Move button in work flow manager page");
        switchFrameWithIndex(0);
        if (isElementPresent(ACTIVE_RADIO_BTN, VERYSHORTWAIT)){
            click(ACTIVE_RADIO_BTN, SHORTWAIT, "Active radio button in work flow manager page");
            click(OK_BTN, SHORTWAIT, "OK button in Bin Statuses popup");
            switchToParentFrame();
            switchFrameWithIndex(0);
        }
        click(YES_BTN, SHORTWAIT, "Yes button in Annotation comments popup");
    }

    /**
     * Purpose - Verify Doc is moved
     * @param docId
     */
    public void verifyDocIsMoved(String docId){
        switchToParentFrame();
        boolean docMoved = isElementPresent(Dynamic.getNewLocator(SUCCESS_MSG, docId), SHORTWAIT);
        Assert.assertTrue(docId+" is not moved in work flow manager page",docMoved);
    }

    /**
     * Purpose - To verify the location
     * @param processorName
     * @return
     */
    public boolean verifyLocation(String processorName, String binDesc){
        switchFrameWithIndex(0);
        boolean locationStatus=false;
        boolean processorStatus = isElementPresent(Dynamic.getNewLocator(LOCATION,processorName), VERYSHORTWAIT);
        boolean binDescStatus = isElementPresent(Dynamic.getNewLocator(LOCATION,binDesc), VERYSHORTWAIT);
        if (processorStatus){
            locationStatus = true;
        } else if(binDescStatus){
            locationStatus = true;
        }
        switchToParentFrame();
        return locationStatus;
    }
}

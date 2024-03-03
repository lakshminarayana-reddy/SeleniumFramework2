package pages;

import locators.InboxPageLocators;
import org.junit.Assert;
import utils.Dynamic;
import utils.SafeActions;

public class InboxPage extends SafeActions implements InboxPageLocators {

    /**
     * Purpose - To verify the inbox page
     */
    public void verifyInboxPage(){
        switchFrame("frame1");
        switchFrame("srframe");
        boolean inboxPage = isElementPresent(DOC_HEADER, SHORTWAIT);
        Assert.assertTrue("Header DocId verification failed as Inbox page is not loaded",inboxPage);
    }

    /**
     * Purpose - To click on Edit EOR link for required doc
     * @param docId
     */
    public void clickEditEOR(String docId){
        javaScriptClick(Dynamic.getNewLocator(EDITEOR_LINK, docId), SHORTWAIT, "Edit EOR link for doc "+docId);
    }

    /**
     * Purpose - To verify Explanation of reimbursement frame
     */
    public void verifyExplanationOfReimbursementFrame(){
        switchToParentFrame();
        switchToParentFrame();
        switchFrame("dialog-body");
        boolean eorPage = isElementPresent(EOR_HEADER, SHORTWAIT);
        Assert.assertTrue("Explanation of reimbursement dialog box is not opened",eorPage);
    }

    /**
     * Purpose - To verify the EOR image in inbox page
     * @param docId
     */
    public void verifyEORImage(String docId){
        boolean eorStatus=false;
        int count = getLocatorCount(NEXT_PAGE_LINK, "Next page link in inbox page");
        for(int i=0; i<count/2; i++){
            eorStatus = isElementPresent(Dynamic.getNewLocator(DOC_EOR, docId), SHORTWAIT);
            if(eorStatus){
                break;
            } else {
                click(NEXT_PAGE_LINK, SHORTWAIT, "Next page link in inbox page");
            }
        }
        if(!eorStatus){
            Assert.fail("EOR image is not displayed for doc "+docId+" in inbox page");
        }
    }
}

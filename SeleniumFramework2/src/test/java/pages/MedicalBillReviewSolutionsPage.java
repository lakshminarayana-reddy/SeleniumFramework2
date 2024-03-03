package pages;

import locators.MedicalBillReviewSolutionLocators;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import utils.Dynamic;
import utils.SafeActions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalBillReviewSolutionsPage extends SafeActions implements MedicalBillReviewSolutionLocators {

    public void searchWithMemberNumber(String memberNumber){
        switchFrame("SingleFrame");
        clearAndType(MEMBER_NUMBER_INPUT,memberNumber,"Member number in claim file view page", SHORTWAIT);
        click(SEARCH_BTN,SHORTWAIT, "Search button in claim file view page");
    }

    public void searchWithDocId(String docId){
        switchFrame("SingleFrame");
        clearAndType(DOC_ID_INPUT,docId,"Doc Id in claim file view page", SHORTWAIT);
        click(SEARCH_BTN,SHORTWAIT, "Search button in claim file view page");
    }

    public void navigateToRequiredTab(String tabName){
        click(Dynamic.getNewLocator(DYNAMIC_LINK, tabName), SHORTWAIT, tabName+" in Claim file view page");
    }

    public void clickOnFirstEORDocument(){
        if(!isElementPresent(FRAME2, VERYVERYSHORTWAIT)) {
            switchFrame("SingleFrame");
        }
        switchFrame("RAD_SPLITTER_PANE_EXT_CONTENT_rdpPages");
        for(int i=1; i<=10; i++){
            if(isElementPresent(EOR_DOCUMENT, VERYSHORTWAIT)){
                javaScriptClick(EOR_DOCUMENT, SHORTWAIT, "EOR document in claim file view page");
                break;
            } else {
                javaScriptClick(Dynamic.getNewLocator(NEXT_LINK, String.valueOf(i+1)),SHORTWAIT, "Next link");
            }
        }
    }

    public void clickOnFirstImageDocument(){
        switchFrame("RAD_SPLITTER_PANE_EXT_CONTENT_rdpPages");
        for(int i=1; i<=10; i++){
            if(isElementPresent(IMAGE, VERYSHORTWAIT)){
                javaScriptClick(IMAGE, SHORTWAIT, "Image in claim file view page");
                break;
            } else {
                javaScriptClick(Dynamic.getNewLocator(NEXT_LINK, String.valueOf(i+1)),SHORTWAIT, "Next link");
            }
        }
    }

    public void verifyDocIsOpened(){
        String pageSource = getPageSource();
        if(pageSource.contains("application/pdf")){
            System.out.println("PDF is opened in the new tab after clicking on EOR");
        } else {
            System.out.println("PDF is not opened after clicking on EOR link");
            Assert.assertTrue(false);
        }
    }

    public void verifyImageIsOpened(String docId){
        verifyFileDownloaded(docId);
    }

    public void clickRPTTypeMore(){
        if(!isElementPresent(FRAME2, VERYVERYSHORTWAIT)) {
            switchFrame("SingleFrame");
        }
        switchFrame("RAD_SPLITTER_PANE_EXT_CONTENT_rdpPages");
        for(int i=1; i<=10; i++){
            if(isElementPresent(MORE_LINK, VERYSHORTWAIT)){
                javaScriptClick(MORE_LINK, SHORTWAIT, "More link in claim file view page");
                break;
            } else {
                javaScriptClick(Dynamic.getNewLocator(NEXT_LINK, String.valueOf(i+1)),SHORTWAIT, "Next link");
            }
        }
    }

    public void verifyMorePopUp(){
        switchFrame("FMoreRpts");
        boolean status = isElementPresent(REPORT_TYPE, SHORTWAIT);
        Assert.assertTrue("Report type is not displayed in popup", status);
    }

    public void clickOnPDF(){
        javaScriptClick(PDF, SHORTWAIT, "PDF document link in more popup");
        waitForSeconds(VERYSHORTWAIT);
    }

    public void clickOnPrint(){
        switchFrame("RAD_SPLITTER_PANE_EXT_CONTENT_rdpPages");
        javaScriptClick(PRINT, SHORTWAIT, "Print icon in pip log page");
    }

    public void verifyDraftEOBCreatedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("M/d/YYYY");
        Date date = new Date();
        if(isElementPresent(LAST_PAGE, VERYSHORTWAIT)){
            click(LAST_PAGE, SHORTWAIT,"Last page in report more");
        }
        boolean dateStatus = isElementPresent(Dynamic.getNewLocator(DRAFT_EOB_CREATED_DATE,formatter.format(date)),SHORTWAIT);
        Assert.assertTrue( "Created date is not matching. Expected date to be "+formatter.format(date),dateStatus);
    }

    public void verifyDraftEOBFor3M(){
        SimpleDateFormat formatter = new SimpleDateFormat("M/d/YYYY");
        Date date = new Date();
        if(isElementPresent(LAST_PAGE, VERYSHORTWAIT)){
            click(LAST_PAGE, SHORTWAIT,"Last page in report more");
        }
        boolean reportTypeStatus = isElementPresent(Dynamic.getNewLocator(DRAFT_EOB_3M,formatter.format(date)),SHORTWAIT);
        Assert.assertTrue( "Report type is not matching. Expected date to be 3M",reportTypeStatus);
    }

    public void uploadImage(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY");
        Date date = new Date();
        switchFrame("RAD_SPLITTER_PANE_EXT_CONTENT_rdpPages");
        click(UPLOAD_IMAGE, SHORTWAIT, "Attachment link");
        switchToParentFrame();
        switchToWindow("AD Upload Utility");
        isElementPresent(FILE_INPUT, MEDIUMWAIT);
        driver.findElement(FILE_INPUT).sendKeys(System.getProperty("user.dir")+"/src/test/java/testdata/Image.tif");
        waitForSeconds(1);
        click(UPLOAD_BUTTON, SHORTWAIT, "Upload button");
        waitForSeconds(1);
        selectByVisibleText(SELECT_LABEL, "Bill without Notes","Select label dropdown", SHORTWAIT);
        waitForSeconds(1);
        clearAndType(CLIENT_RECEIVED_DATE, formatter.format(date),"Client received date", SHORTWAIT);
        waitForSeconds(1);
        javaScriptClick(SUBMIT_BUTTON, SHORTWAIT, "Submit button");

    }

    public void verifyImageUploadedSuccessfully(){
        boolean status = isElementPresent(IMAGE_UPLOADED, SHORTWAIT);
        Assert.assertTrue("Image is not uploaded", status);
    }
}

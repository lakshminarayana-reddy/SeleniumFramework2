package pages;

import locators.HomePageLocators;
import org.junit.Assert;
import utils.Dynamic;
import utils.SafeActions;

public class HomePage extends SafeActions implements HomePageLocators {
    /**
     * Purpose - Verify home page
     */
    public void verifyHomePage(){
        boolean homePage = isElementPresent(HEADER_TXT, MEDIUMWAIT);
        Assert.assertTrue("Home page is not opened",homePage);
    }

    /**
     * Purpose - Navigate to advance review module
     */
    public void navigateToAdvanceReviewModule(){
        //mouseHover(Dynamic.getNewLocator(DYNAMIC_LINK, "Internal"),"Internal link",SHORTWAIT);
        //mouseHover(Dynamic.getNewLocator(DYNAMIC_LINK, "Internal Medical Bill Review Solutions"),"Internal Medical Bill Review Solutions link",SHORTWAIT);
        javaScriptClick(Dynamic.getNewLocator(DYNAMIC_LINK,"Advanced Review Module"), SHORTWAIT, "Advanced Review Module link");
    }

    /**
     * Purpose - Navigate to Inbox in Medical bill solution
     */
    public void navigateToRequiredLinkInMedicalBillSolution(String linkName){
        mouseHover(Dynamic.getNewLocator(DYNAMIC_LINK, "Medical Bill Review Solutions"),"Medical Bill Review Solutions link",SHORTWAIT);
        click(Dynamic.getNewLocator(DYNAMIC_LINK, linkName),SHORTWAIT,linkName+" link");
    }

    /**
     * Purpose - To Navigate to required link in internal -> internal medical bill review solution
     * @param linkName
     */
    public void navigateToRequiredLink_InternalMedicalBillReviewSolution(String linkName){
        mouseHover(Dynamic.getNewLocator(DYNAMIC_LINK, "Internal"),"Internal link",SHORTWAIT);
        mouseHover(Dynamic.getNewLocator(DYNAMIC_LINK, "Internal Medical Bill Review Solutions"),"Internal Medical Bill Review Solutions link",SHORTWAIT);
        click(Dynamic.getNewLocator(DYNAMIC_LINK,linkName), SHORTWAIT, linkName+" in internal->internal medical bill review solution");
    }


}

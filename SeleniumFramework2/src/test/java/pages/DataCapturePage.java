package pages;

import locators.DataCapturePageLocators;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import utils.SafeActions;

import java.security.Key;

public class DataCapturePage extends SafeActions implements DataCapturePageLocators {

    public void pressTabKey(int numOfTimes){
        for(int i=0; i<numOfTimes; i++) {
            waitForSeconds(1);
            new Actions(driver).sendKeys(Keys.TAB).perform();
            System.out.println("TAB key Keyboard action performed");
        }
    }

    public void clickSameAsServiceProviderCheckBox(){
        switchFrame("SingleFrame");
        click(SAME_AS_SERVICE_PROVIDER_CHECKBOX, SHORTWAIT, "Same as service provider checkbox");
    }

    public void verifyAddressPopup(){
        switchFrameWithIndex(0);
        boolean addressStatus = isElementPresent(CASS_ADDRESS_POPUP, SHORTWAIT);
        Assert.assertTrue("Address popup in Data capture is not found", addressStatus);
    }
}

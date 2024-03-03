package locators;

import org.openqa.selenium.By;

public interface CommonLocators {
    By USER_NAME = By.id("txtUserID");
    By PASSWORD = By.id("txtPassword");
    By LOGIN_BTN = By.id("btnOK");
    By LB_LOCATION_DROPDOWN = By.id("lbLocation");
}

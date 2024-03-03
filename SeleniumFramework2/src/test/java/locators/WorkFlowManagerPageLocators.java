package locators;

import org.openqa.selenium.By;

public interface WorkFlowManagerPageLocators {
    By SEARCH_LINK = By.xpath("//a[@title='Search Docs.']");
    By DOC_ID_TEXT_FIELD = By.id("txtDocID");
    By SEARCH_BTN = By.id("btnSearch");
    By DOC_CHECKBOX = By.xpath("//input[@type='checkbox']");
    By SELECT_BIN = By.id("ddlBin_Input");
    By BIN_TEXT = By.xpath("//li[text()='%s']");
    By SELECT_PROCESSOR = By.id("ddlProcessor_Input");
    By PROCESSOR_TEXT = By.xpath("//li[text()='%s']");
    By MOVE_BTN = By.id("btnMove");
    By YES_BTN = By.id("btnYes");
    By ACTIVE_RADIO_BTN = By.xpath("//label[text()='Active']");
    By SUCCESS_MSG = By.xpath("//span[@class='labelSuccess'][contains(.,'%s')]");
    By OK_BTN = By.id("btnOK");
    By LOCATION = By.xpath("//td[contains(@title,'%s')]");
}
